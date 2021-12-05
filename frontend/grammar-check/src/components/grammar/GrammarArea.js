import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { shadow } from "../../lib/StyleUtils";
import { InputTextArea } from "./InputTextArea";
import { ResultTextArea } from "./ResultTextArea";
import axios from "axios";
import nayngFootprint from "../../static/images/nayng-footprint.png"

const Area = styled.section`
  padding: 1rem 6rem;
  height: 70vh;
  display: flex;
`;

const TextArea = styled.article`
  width: 100%;
  ${shadow(0)}
  display: flex;
`;

const CenterLine = styled.div`
  border: 1px solid #c7c7c7;
  height: 90%;
  align-self: center;
`;

const FootPrint = styled.img`
  position: absolute;
  right: -4%;
  bottom: -15%;
`

export const GrammarArea = () => {
  const [text, setText] = useState("");
  const [checkEvent, setCheckEvent] = useState(true);
  const [ip, setIP] = useState("");

  const getData = async () => {
    const res = await axios.get("https://geolocation-db.com/json/");
    setIP(res.data.IPv4);
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <Area>
      <TextArea>
        <InputTextArea
          text={text}
          setText={setText}
          startEvent={setCheckEvent}
          ip={ip}
        />
        <CenterLine />
        <ResultTextArea
          text={text}
          setText={setText}
          eventChecker={checkEvent}
          endEvent={setCheckEvent}
          ip={ip}
        />
      </TextArea>
      <FootPrint src={nayngFootprint}/>
    </Area>
  );
};
