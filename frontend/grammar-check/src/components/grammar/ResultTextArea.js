import React, { useState, useEffect } from "react";
import styled from "styled-components";
import axios from "axios";
import { serverIp } from "../../static/setting";

const Area = styled.article`
  width: 50%;
  height: 100%;
  display: flex;
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
  align-self: center;
`;

export const ResultTextArea = ({
  text,
  eventChecker,
  endEvent,
  setResultList
}) => {
  const [fixedText, setFixedText] = useState("");

  useEffect(() => {
    if (!eventChecker) return;

    axios
      .get(`${serverIp}check?grammar=${text}`)
      .then((result) => {
        const list = result.data.response;
        const resultist = [];
        var fix = "";

        list.map(function (info) {
          fix += `${info.errorText} -> ${info.fixedText}\n\n`;
          resultist.push({
            errorText: info.errorText,
            fixedText: info.fixedText,
          });
        });
        setResultList(resultist)
        setFixedText(fix);
      })
      .catch((res) => {
        console.log(res);
      });
    endEvent(false);
  }, [eventChecker]);

  return (
    <Area>
        <TextArea readOnly value={fixedText}/>
    </Area>
  );
};
