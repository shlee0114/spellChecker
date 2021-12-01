import React, { useState } from "react";
import styled from "styled-components";
import { InputTextArea } from "./InputTextArea";
import { ResultTextArea } from "./ResultTextArea";

const Area = styled.section`
  width: 100%;
  height: 60vh;
  margin-left: 15%;
  margin-top: 10%;
  display: flex;
`;

export const GrammarArea = () => {
  const [text, setText] = useState("");
  const [checkEvent, setCheckEvent] = useState(true);
  const [resultList, setResultList] = useState([]);
  const [resultOpened, setResultOpened] = useState(false);
  const [closeAll, setCloseAll] = useState(false);
  return (
    <Area>
      <InputTextArea
        text={text}
        setText={setText}
        startEvent={setCheckEvent}
        resultList={resultList}
        resultOpened={resultOpened}
        setCloseAll={setCloseAll}
      />
      <ResultTextArea
        text={text}
        eventChecker={checkEvent}
        endEvent={setCheckEvent}
        setResultList={setResultList}
        setResultOpened={setResultOpened}
        closeAll={closeAll}
      />
    </Area>
  );
};
