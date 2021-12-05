import React from "react";
import styled from "styled-components";
const Area = styled.article`
width: 100%;
height: 100%;
display flex;
flex-direction: column;
`;

const TextArea = styled.textarea`
  width: 100%;
  height: 100%;
  font-size: 1rem;
  border: none;
  outline: none;
  resize: none;
  font-family: "noto sans KR";
  background: #0000;
  -ms-overflow-style: none;
  &::-webkit-scrollbar{
    display:none;
  }
`;

export const LogTextArea = ({logList, display}) => {
  return (
    <Area style={{display:display}}>
        <TextArea readOnly value={logList} />
    </Area>
  );
};
