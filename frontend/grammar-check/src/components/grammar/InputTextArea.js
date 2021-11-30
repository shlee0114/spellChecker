import React, { useState, useRef } from "react";
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
`;

export const InputTextArea = () => {
  const [textCount, setCount] = useState(0);
  const [text, setText] = useState("");
  const [fixedText, setFixedText] = useState("");

  const quickCheckerRef = useRef();

  var isCtrl = false;
  var serverSendTimer = null;

  const inputTextKeyUp = (e) => {
    if (e.which === 17) isCtrl = false;
    setCount(text.length);
  };

  const inputTextKeyDown = (e) => {
    if (e.which === 17) {
      isCtrl = true;
      return;
    }
  };

  const inputTextChange = (e) => {
    var targetText = e.target.value;
    if (targetText.length >= 500) {
      targetText = targetText.substr(0, 500);
    }
    setText(targetText);
    setCount(text.length);

    if (serverSendTimer != null) {
      clearTimeout(serverSendTimer);
    }

    if (e.which === 32) {
      sendServerQuick();
    } else {
      serverSendTimer = setTimeout(() => {
        sendServerQuick();
      }, 800);
    }
  };

  const sendServerQuick = () => {
    const inputArea = document.getElementById("input");
    const sendText = inputArea.value.replaceAll("\n", " ").split(" ").pop();

    if (sendText.trim() === "") {
      return true;
    }

    url
      .query({
        query: grammar.GRAMMAR_CHECK(sendText),
      })
      .then((result) => {
        if (
          !(result.errors ?? false) &&
          (result.data.check.fixedText ?? "") !== ""
        ) {
          setFixedText(`${sendText} -> ${result.data.check.fixedText}`);
          gsap.to(quickCheckerRef.current, {
            top: "0",
            opacity: "1",
            duration: 1,
          });
        } else {
          gsap.to(quickCheckerRef.current, {
            top: "-100%",
            opacity: "0",
            duration: 1,
          });
        }
      })
      .catch((res) => {
        console.log(res);
      });
  };

  return (
    <Area>
      <TextArea
        onKeyDown={inputTextKeyDown}
        onKeyUp={inputTextKeyUp}
        onChange={inputTextChange}
        value={text}
        id="input"
      ></TextArea>
      <TextUtilArea>
        <QuickChecker ref={quickCheckerRef}>{fixedText}</QuickChecker>
        <Spacer />
        <TextCounter>{textCount}/500</TextCounter>
        <Button>검사</Button>
      </TextUtilArea>
    </Area>
  );
};
