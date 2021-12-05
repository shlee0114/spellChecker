import React, { useState, useEffect } from "react";
import { LogTextArea } from "./LogTextArea";
import axios from "axios";
import { serverIp } from "../../static/setting";

export const SentenceLog = ({ opened }) => {
  const [logList, setLogList] = useState("");
  const [display, setDisplay] = useState("");

  useEffect(() => {
    setDisplay(opened ? "" : "none")
    if (opened) {
      axios
        .get(`${serverIp}log`)
        .then((result) => {
          const list = result.data.response;
          var log = "";

          list.map(
            (info) =>
              (log += `${info.error} -> ${info.fixed}\ncount : ${info.count}\ntime : ${info.fixedTime}\n\n`)
          );
          setLogList(log);
        })
        .catch((res) => {
          console.log(res);
        });
    }
  }, [opened]);

  return <LogTextArea logList={logList} display={display}/>;
};
