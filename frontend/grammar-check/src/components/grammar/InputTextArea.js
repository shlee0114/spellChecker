import React, { useState } from "react";
import styled from "styled-components";
import oc from 'open-color'
import { Button } from "../common/Button";

const Area = styled.article`
  width: 70%;
  height: 100%;
  background: #F7F7F7;
  box-sizing: border-box;
  border: 1px solid rgba(95, 92, 92, 0.47);
`;

const TextArea = styled.textarea`
  width: 100%;
  height: 90%;
  background: #00000000;
  font-size: 1.95rem;
  border: none;
  outline: none;
  resize: none;
  font-family: 'noto sans KR';
`;

const QuickChecker = styled.label`
  margin: auto 10px;
  border : 1px solid ${oc.cyan[6]};
  padding : 0.5rem;
  position: absolute;
  top: 100%;
  opacity: 0;
`

const TextUtilArea = styled.div`
  display:flex;
  z-index: 10;
  position: relative;
`
const TextCounter = styled.label`
  margin: auto 10px;
`

const Spacer = styled.div`
flex-grow: 1;
`

export const InputTextArea = () => {
  const [ textCount, setCount ] = useState(0);
    const [ text, setText ] = useState("");
    var isCtrl = false

    const inputTextKeyUp = (e) => {
        if(e.which === 17) isCtrl=false;  
        setCount(text.length)
    }

    const inputTextKeyDown = (e) => {
        if(e.which === 17) {
            isCtrl=true
            return
        } 
    }

    const inputTextChange = (e) => {
      var targetText = e.target.value
      if(targetText.length >= 500) {
        targetText = targetText.substr(0, 500)
      }
      setText(targetText)
      setCount(text.length)
    }

  return (
    <Area>
      <TextArea
        onKeyDown={inputTextKeyDown}
        onKeyUp={inputTextKeyUp}
        onChange={inputTextChange}
        value={text}
      ></TextArea>
      <TextUtilArea>
      <QuickChecker>testtest -&gt; testest</QuickChecker>
      <Spacer/>
      <TextCounter>{textCount}/500</TextCounter>
      <Button>검사</Button>
      </TextUtilArea>
    </Area>
  );
};
