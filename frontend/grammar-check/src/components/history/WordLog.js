import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import oc from 'open-color'
import { CloseButton } from "../common/CloseButton";
import { Button } from "../common/Button";
import axios from "axios";
import { gsap } from "gsap";

const serverIp = "http://localhost:8089/api/";
const Area = styled.article`
  width: 35%;
  height: 100%;
  display flex;
  flex-direction: column;
`;

const TitleArea = styled.div`
    height: 25%;
    display: flex;
    align-items: center;
    align-self: center;
    color : ${oc.grape[5]};
    font-family: 'godo maum';
    font-size : 2.6rem;
`
const ContentArea = styled.div`
    height: 75%;
    background:#eee;
`

const TextArea = styled.textarea`
  width: 100%;
  height: 80%;
  font-size: 1rem;
  border: 1px solid rgba(95, 92, 92, 0.47);
  outline: 1px solid rgba(95, 92, 92, 0.47);
  resize: none;
  font-family: "noto sans KR";
`;


export const WordLog = ({opened}) => {

  useEffect(() => {
    if (opened) {
      return;
    }
  }, [opened]);

  return (
    <Area>
    <TitleArea>
        낱말 수정 기록
    </TitleArea>
    <ContentArea>
        <TextArea readOnly value=""></TextArea>
    </ContentArea>
    </Area>
  );
};
