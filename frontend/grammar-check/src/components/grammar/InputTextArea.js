import React, { useState } from "react";
import styled from "styled-components";
import { Button } from "../common/Button";

const Area = styled.article`
  width: 100%;
  height: 100%;
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

const TextUtilArea = styled.div`
    bottom: 0;
    display:flex;
    float: right
`
const TextCounter = styled.label`
margin: auto 10px;
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
    <TextCounter>{textCount}/500</TextCounter>
    <Button>검사</Button>
      </TextUtilArea>
    </Area>
  );
};
