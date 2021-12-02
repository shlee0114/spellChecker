import React from "react";
import styled from "styled-components";
import oc from "open-color";

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
  color: ${oc.grape[5]};
  font-family: "godo maum";
  font-size: 2.6rem;
`;
const ContentArea = styled.div`
  height: 75%;
  background: #eee;
`;

const TextArea = styled.textarea`
  width: 100%;
  height: 80%;
  font-size: 1rem;
  border: 1px solid rgba(95, 92, 92, 0.47);
  outline: 1px solid rgba(95, 92, 92, 0.47);
  resize: none;
  font-family: "noto sans KR";
`;

export const LogTextArea = ({title, logList}) => {
  return (
    <Area>
      <TitleArea>{title}</TitleArea>
      <ContentArea>
        <TextArea readOnly value={logList}/>
      </ContentArea>
    </Area>
  );
};
