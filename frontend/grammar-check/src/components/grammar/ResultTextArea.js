import React from 'react'
import styled from "styled-components";
import { CloseButton } from '../common/CloseButton';

const Area = styled.article`
    width: 24%;
    height: 100%;
    margin-left: 1px;
`;

const CorverArea = styled.div`
    width: 100%;
    height: 100%;
    position:absolute;
    background:#EFEFEC;
    z-index:10
`
const FixedArea = styled.div`
    width: 100%;
    height: 100%;
    background:#DDDDDD;
`
const TextArea = styled.textarea`
  margin: 10%;
  width: 80%;
  height: 80%;
  font-size: 1rem;
  border: 1px solid rgba(95, 92, 92, 0.47);
  outline: 1px solid rgba(95, 92, 92, 0.47);
  resize: none;
  font-family: 'noto sans KR';
  
`;

export const ResultTextArea = () => {
    return (
        <Area>
            <CorverArea/>
            <FixedArea>
                <CloseButton></CloseButton>
                <TextArea readOnly></TextArea>
            </FixedArea>
        </Area>
    )
} 