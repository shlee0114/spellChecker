import React, { useState } from "react";
import styled from "styled-components";

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

export const InputTextArea = ({setCount}) => {
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
    </Area>
  );
};
