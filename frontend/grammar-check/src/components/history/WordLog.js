import React, { useState, useEffect } from "react";
import { LogTextArea } from "./LogTextArea";

export const WordLog = ({opened}) => {

  const [logList, setLogList] = useState("")

  useEffect(() => {
    if (opened) {
      return;
    }
  }, [opened]);

  return (
    <LogTextArea title="낱말 수정 기록" logList={logList}/>
  );
};
