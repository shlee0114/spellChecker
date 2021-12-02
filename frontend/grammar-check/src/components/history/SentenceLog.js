import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { CloseButton } from "../common/CloseButton";
import { Button } from "../common/Button";
import axios from "axios";
import { gsap } from "gsap";

const serverIp = "http://localhost:8089/api/";
const Area = styled.article`
width: 35%;
  height: 100%;
  background:#111;
`;


export const SentenceLog = ({opened}) => {

  useEffect(() => {
    if (opened) {
      return;
    }
  }, [opened]);

  return (
    <Area>
    </Area>
  );
};
