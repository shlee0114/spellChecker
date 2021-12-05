import React, { useState, useEffect } from "react";
import { LogTextArea } from "./LogTextArea";
import url from "../../apolloClient";
import { grammar } from "../../graphql";

export const WordLog = ({ opened }) => {
  const [logList, setLogList] = useState("");
  const [display, setDisplay] = useState("");

  useEffect(() => {
    setDisplay(opened ? "" : "none")
    if (opened) {
      url
        .query({
          query: grammar.SEARCH_LOG(),
        })
        .then((result) => {
          const list = result.data.log;
          var log = "";

          list.map(
            (info) =>
              (log += `${info.error} -> ${info.fixed}\ntime : ${info.fixedTime}\n\n`)
          );
          setLogList(log);
        })
        .catch((res) => {
          console.log(res);
        });
    }
  },[opened]);

  return <LogTextArea logList={logList} display={display}/>;
};
