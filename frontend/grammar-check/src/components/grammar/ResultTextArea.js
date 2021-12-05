import React, { useState, useEffect } from "react";
import styled from "styled-components";
import axios from "axios";
import { serverIp } from "../../static/setting";
import { Button } from "../common/Button";

const Area = styled.article`
  width: 50%;
  height: 100%;
  display: flex;
  flex-direction: column;
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

export const ResultTextArea = ({
  text,
  setText,
  eventChecker,
  endEvent,
  ip
}) => {
  const [fixedText, setFixedText] = useState("");
  const [clearText, setClearText] = useState("0");
  const [fixedCount, setFixedCount] = useState(0);
  const [resultText, setResultText] = useState("");

  useEffect(() => {
    if (!eventChecker) return;

    axios
      .get(`${serverIp}check?grammar=${text}`)
      .then((result) => {
        const list = result.data.response;
        var fix = text;
        var resultTempText = text;
        var count = 0

        list.map(function (info) {
          fix = fix.replaceAll(info.errorText, `<bold>${info.fixedText}</bold>`);
          resultTempText = resultTempText.replaceAll(info.errorText, info.fixedText);
          count++
        });
        if(count === 0) {
          fix = ""
          resultTempText = ""
        }
        setFixedCount(count)
        setResultText(resultTempText)
        insertFixedText(fix)
      })
      .catch((res) => {
        console.log(res);
      });
    endEvent(false);
  }, [eventChecker]);

  const fixAllText = (_) => {
    if (fixedCount === 0) return;

    axios
      .post(`${serverIp}log`, {
        errorText: text,
        fixedText: resultText,
        fixedCount: fixedCount,
        ip: ip,
      })
      .catch((res) => {
        console.log(res);
      });

    setText(resultText);
    insertFixedText("")
  }

  const insertFixedText = (text) => {
    setFixedText(text);
    setClearText(text.length === 0 ? "0" : "1")
  }

  return (
    <Area>
    <HeaderArea>
      <Button
        style={{
          opacity: clearText,
          float: "right",
          padding: "3%"
        }}
        onClick={fixAllText}
      >
        일괄 수정
      </Button>
    </HeaderArea>
        <TextArea readOnly value={fixedText}/>
    </Area>
  );
};
