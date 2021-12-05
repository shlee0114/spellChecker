import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { Button } from "../common/Button";
import url from "../../apolloClient";
import { grammar } from "../../graphql/index";
import { gsap } from "gsap";
import axios from "axios";
import { serverIp } from "../../static/setting";

const Area = styled.article`
  width: 50%;
  height: 100%;
`;

const HeaderArea = styled.div`
  height: 13%;
`;

const TextArea = styled.textarea`
  width: 94%;
  height: 74%;
  background: #0000;
  font-size: 1.3rem;
  border: none;
  outline: none;
  resize: none;
  font-family: "noto sans KR";
  padding: 0 3%;
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
  color: #8d8d8d;
  margin: auto 10px;
  padding: 0.5rem;
  position: absolute;
  opacity: 0;
`;

const CTRL_KEY_CODE = 17;
const SINGLE_QUOTATION_KEY_CODE = 192;

const CHECKABLE_MAX_LENGTH = 500;

export const InputTextArea = ({
  text,
  setText,
  startEvent,
  resultList,
  ip,
}) => {
  const [textCount, setCount] = useState(0);
  const [fixedText, setFixedText] = useState("");
  const [clearText, setClearText] = useState(false);

  const quickCheckerRef = useRef();
  const inputAreaRef = useRef();

  var isCtrl = false;
  var serverSendTimer = null;

  useEffect(() => {
    if (resultList.length === 0) return;
    const beforeText = text;
    var afterText = text;

    resultList.map((result) => {
      afterText = afterText.replaceAll(result.errorText, result.fixedText);
      url
        .mutate({
          mutation: grammar.LOG_INSERT(result.errorText, result.fixedText, ip),
        })
        .catch((res) => {
          console.log(res);
        });
    });

    axios
      .post(`${serverIp}log`, {
        errorText: beforeText,
        fixedText: afterText,
        fixedCount: resultList.length,
        ip: ip,
      })
      .catch((res) => {
        console.log(res);
      });

    setText(afterText);
  }, [resultList]);

  const inputTextKeyUp = (e) => {
    if (e.which === CTRL_KEY_CODE) {
      isCtrl = false;
      return;
    }
    textSetting(text);
    sendServer();
  };

  const inputTextKeyDown = (e) => {
    if (e.which === CTRL_KEY_CODE) {
      isCtrl = true;
      return;
    }
    if (e.which === SINGLE_QUOTATION_KEY_CODE && isCtrl === true) {
      const textArray = fixedText.replaceAll(" ", "").split("->");
      textSetting(text.replace(new RegExp(textArray[0] + "$"), textArray[1]));

      url
        .mutate({
          mutation: grammar.LOG_INSERT(textArray[0], textArray[1], ip),
        })
        .catch((res) => {
          console.log(res);
        });
      openOrCloseQuickChecker(false);
      return false;
    }
  };

  const inputTextChange = (e) => {
    var targetText = e.target.value;
    if (targetText.length >= CHECKABLE_MAX_LENGTH) {
      targetText = targetText.substr(0, CHECKABLE_MAX_LENGTH);
    }
    textSetting(targetText);
  };

  const textSetting = (text) => {
    setText(text);
    setCount(text.length);
    setClearText(text.length === 0 ? "hidden" : "");
  };

  const sendServer = () => {
    if (serverSendTimer != null) {
      clearTimeout(serverSendTimer);
    }
    serverSendTimer = setTimeout(() => {
      sendServerQuick();
    }, 700);
  };

  const sendServerQuick = () => {
    const sendText = text.replaceAll("\n", " ").split(" ").pop();

    if (sendText.trim() === "") {
      openOrCloseQuickChecker(false);
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

        openOrCloseQuickChecker(fixedYn);

        if (!fixedYn) return;

        setFixedText(`${sendText} -> ${result.data.check.fixedText}`);
      })
      .catch((res) => {
        console.log(res);
      });
  };

  const openOrCloseQuickChecker = (openYn) => {
    const opacity = openYn ? "1" : "0";

    gsap.to(quickCheckerRef.current, {
      opacity: opacity,
      duration: 0.8,
    });
  };

  return (
    <Area>
      <HeaderArea>
        <Button
          style={{
            visibility: clearText,
            fontWeight: "500",
            color: "gray",
            fontSize: "1.5rem",
            float: "right",
            padding: "3%"
          }}
        >
          X
        </Button>
      </HeaderArea>
      <TextArea
        onKeyDown={inputTextKeyDown}
        onKeyUp={inputTextKeyUp}
        onChange={inputTextChange}
        value={text}
      />
      <TextUtilArea ref={inputAreaRef}>
        <QuickChecker ref={quickCheckerRef}>{fixedText}</QuickChecker>
        <Spacer />
        <TextCounter>{textCount}/500</TextCounter>
        <Button
          onClick={(_) => {
            startEvent(true);
            openOrCloseQuickChecker(false);
          }}
        >
          검사
        </Button>
      </TextUtilArea>
    </Area>
  );
};
