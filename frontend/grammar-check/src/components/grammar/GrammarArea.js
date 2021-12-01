import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { InputTextArea } from "./InputTextArea";
import { ResultTextArea } from "./ResultTextArea";
import axios from 'axios'

const Area = styled.section`
  width: 100%;
  height: 60vh;
  margin-left: 15%;
  margin-top: 7.5%;
  display: flex;
`;

export const GrammarArea = () => {
  const [text, setText] = useState("");
  const [checkEvent, setCheckEvent] = useState(true);
  const [resultList, setResultList] = useState([]);
  const [resultOpened, setResultOpened] = useState(false);
  const [closeAll, setCloseAll] = useState(false);
  const [ip, setIP] = useState('');

  const getData = async () => {
    const res = await axios.get('https://geolocation-db.com/json/')
    setIP(res.data.IPv4)
  }

  useEffect( () => {
      getData()
  }, [])

  return (
    <Area>
      <InputTextArea
        text={text}
        setText={setText}
        startEvent={setCheckEvent}
        resultList={resultList}
        resultOpened={resultOpened}
        setCloseAll={setCloseAll}
        ip={ip}
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
