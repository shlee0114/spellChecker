import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { CloseButton } from "../common/CloseButton";
import { Button } from "../common/Button";
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

const AllFixBtn = styled.div`
  width: 95px;
  float: right;
  margin-top: -8%;
  margin-right: 6%;
`;

export const ResultTextArea = ({
  text,
  eventChecker,
  endEvent,
  setResultList,
  setResultOpened,
  closeAll,
}) => {
  const serverIp = "http://localhost:8089/api/";
  const [fixedText, setFixedText] = useState("");

  const checkerRef = useRef();

  var resultist = [];

  useEffect(() => {
    if (closeAll) {
      close();
      return;
    }
  }, [closeAll]);

  useEffect(() => {
    if (!eventChecker) return;

    axios
      .get(`${serverIp}check?grammar=${text}`)
      .then((result) => {
        const list = result.data.response;
        resultist = [];
        var fix = "";

        list.map(function (info) {
          fix += `${info.errorText} -> ${info.fixedText}\n\n`;
          resultist.push({
            errorText: info.errorText,
            fixedText: info.fixedText,
          });
        });

        setFixedText(fix);
        openOrClose(true);
      })
      .catch((res) => {
        console.log(res);
      });
    endEvent(false);
  }, [eventChecker]);

  const close = (_) => {
    openOrClose(false);
    setResultList([]);
  };

  const openOrClose = (e) => {
    setResultOpened(e);
    const marginLeft = e ? "-100%" : "0";
    gsap.to(checkerRef.current, {
      marginLeft: marginLeft,
      duration: 0.5,
    });
  };

  return (
    <Area>
      <CorverArea />
      <FixedArea ref={checkerRef}>
        <CloseButton onClick={close}></CloseButton>
        <TextArea readOnly value={fixedText}></TextArea>
        <AllFixBtn>
          <Button
            onClick={(_) => {
              setResultList(resultist);
            }}
          >
            일괄수정
          </Button>
        </AllFixBtn>
      </FixedArea>
    </Area>
  );
};
