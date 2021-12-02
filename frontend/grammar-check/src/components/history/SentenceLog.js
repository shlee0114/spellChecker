import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { LogTextArea } from "./LogTextArea";

const serverIp = "http://localhost:8089/api/";
const Area = styled.article`
width: 35%;
  height: 100%;
  background:#111;
`;


export const SentenceLog = ({opened}) => {

  const [logList, setLogList] = useState("")

  useEffect(() => {
    if (opened) {
      return;
    }
  }, [opened]);

  return (
    <LogTextArea title="문장 수정 기록" logList={logList}/>
  );
};
