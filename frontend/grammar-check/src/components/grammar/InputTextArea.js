import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import oc from "open-color";
import { Button } from "../common/Button";
import url from "../../apolloClient";
import { grammar } from "../../graphql/index";
import { gsap } from "gsap";

const Area = styled.article`
  width: 70%;
  height: 100%;
  background: #f7f7f7;
  box-sizing: border-box;
  border: 1px solid rgba(95, 92, 92, 0.47);
`;

const TextArea = styled.textarea`
  width: 100%;
  height: 90%;
  background: #00000000;
  font-size: 1.95rem;
  border: none;
  outline: none;
  resize: none;
  font-family: "noto sans KR";
  transition: 0.5s;
`;

const TextUtilArea = styled.div`
  display: flex;
  z-index: 10;
  position: relative;
`;
const TextCounter = styled.label`
  margin: auto 10px;
`;

const Spacer = styled.div`
  flex-grow: 1;
`;

const QuickChecker = styled.label`
  margin: auto 10px;
  border: 1px solid ${oc.cyan[6]};
  padding: 0.5rem;
  position: absolute;
  opacity: 0;
  top: -100%;
`;

const CTRL_KEY_CODE = 17;
const SINGLE_QUOTATION_KEY_CODE = 192;

const CHECKABLE_MAX_LENGTH = 500;

export const InputTextArea = ({
  text,
  setText,
  startEvent,
  resultList,
  resultOpened,
  setCloseAll
}) => {

  const [textAreaWidth, setWidth] = useState("100%");
  const textAreaStyle = {width:textAreaWidth}

  const [textCount, setCount] = useState(0);
  const [fixedText, setFixedText] = useState("");

  const quickCheckerRef = useRef();
  const inputAreaRef = useRef();

  var isCtrl = false;
  var serverSendTimer = null;

  
  useEffect(() => {
    const width = resultOpened ? "65%" : "100%";
    setWidth(width)
    gsap.to(inputAreaRef.current, {
      width: width,
      duration: 0.5,
    });
  },[resultOpened])

  const inputTextKeyUp = (e) => {
    if(resultOpened) {
      setCloseAll(true)
    }
    if (e.which === CTRL_KEY_CODE) {
      isCtrl = false;
      return;
    }
    setCount(text.length);
    sendServer();
  };

  const inputTextKeyDown = (e) => {
    if (e.which === CTRL_KEY_CODE) {
      isCtrl = true;
      return;
    }
  };

  const inputTextChange = (e) => {
    var targetText = e.target.value;
    if (targetText.length >= CHECKABLE_MAX_LENGTH) {
      targetText = targetText.substr(0, CHECKABLE_MAX_LENGTH);
    }
    setText(targetText);
  };

  const sendServer = () => {
    if (serverSendTimer != null) {
      clearTimeout(serverSendTimer);
    }
    serverSendTimer = setTimeout(() => {
      sendServerQuick();
    }, 800);
  };

  const sendServerQuick = () => {
    const sendText = text.replaceAll("\n", " ").split(" ").pop();

    if (sendText.trim() === "") {
      openOrCloseChecker(false);
      return true;
    }

    url
      .query({
        query: grammar.GRAMMAR_CHECK(sendText),
      })
      .then((result) => {
        const fixedYn =
          !(result.errors ?? false) &&
          (result.data.check.fixedText ?? "") !== "" &&
          sendText !== result.data.check.fixedText;

        openOrCloseChecker(fixedYn);

        if (!fixedYn) return;

        setFixedText(`${sendText} -> ${result.data.check.fixedText}`);
      })
      .catch((res) => {
        console.log(res);
      });
  };

  const openOrCloseChecker = (openYn) => {
    const top = openYn ? "0" : "-100%";
    const opacity = openYn ? "1" : "0";

    gsap.to(quickCheckerRef.current, {
      top: top,
      opacity: opacity,
      duration: 1,
    });
  };

  return (
    <Area>
      <TextArea
        onKeyDown={inputTextKeyDown}
        onKeyUp={inputTextKeyUp}
        onChange={inputTextChange}
        value={text}
        style={textAreaStyle}
      ></TextArea>
      <TextUtilArea ref={inputAreaRef}>
        <QuickChecker ref={quickCheckerRef}>{fixedText}</QuickChecker>
        <Spacer />
        <TextCounter>{textCount}/500</TextCounter>
        <Button
          onClick={(_) => {
            startEvent(true)
            setCloseAll(false)
            openOrCloseChecker(false)
          }}
        >
          검사
        </Button>
      </TextUtilArea>
    </Area>
  );
};
