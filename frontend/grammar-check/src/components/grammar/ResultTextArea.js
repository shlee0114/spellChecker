import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { CloseButton } from "../common/CloseButton";
import axios from "axios";
import { gsap } from "gsap";

const Area = styled.article`
  width: 24%;
  height: 100%;
  margin-left: 1px;
`;

const CorverArea = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
  background: #efefec;
  z-index: 10;
`;
const FixedArea = styled.div`
  width: 100%;
  height: 100%;
  background: #dddddd;
`;
const TextArea = styled.textarea`
  margin: 10%;
  width: 80%;
  height: 80%;
  font-size: 1rem;
  border: 1px solid rgba(95, 92, 92, 0.47);
  outline: 1px solid rgba(95, 92, 92, 0.47);
  resize: none;
  font-family: "noto sans KR";
`;

export const ResultTextArea = ({
  text,
  eventChecker,
  endEvent,
  setResultList,
}) => {
  const serverIp = "http://localhost:8089/api/";
  const [fixedText, setFixedText] = useState("");

  const checkerRef = useRef();

  useEffect(() => {
    if (!eventChecker) return;
    
    axios
      .get(`${serverIp}check?grammar=${text}`)
      .then((result) => {
        const list = result.data.response;
        const resultist = [];
        var fix = "";

        list.map(
          (info) => (
            (fix += `${info.errorText} -> ${info.fixedText}\n`),
            resultist.push({
              errorText: info.errorText,
              fixedText: info.fixedText,
            })
          )
        );

        setFixedText(fix);
        setResultList(resultist);
        gsap.to(checkerRef.current, {
          marginLeft: "-100%",
          duration: 0.5,
        });
      })
      .catch((res) => {
        console.log(res);
      });
    endEvent(false);
  }, [eventChecker]);

  const close = (e) => {
    gsap.to(checkerRef.current, {
      marginLeft: "0",
      duration: 0.5,
    });
    setResultList([]);
  };

  return (
    <Area>
      <CorverArea />
      <FixedArea ref={checkerRef}>
        <CloseButton onClick={close}></CloseButton>
        <TextArea readOnly value={fixedText}></TextArea>
      </FixedArea>
    </Area>
  );
};
