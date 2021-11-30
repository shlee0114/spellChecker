import React, { useState } from 'react'
import styled from 'styled-components'
import {InputTextArea} from './InputTextArea'
import {ResultTextArea} from './ResultTextArea'
import { Button } from "../common/Button";

const Area = styled.section`
    width: 100%;
    height: 60vh;
    margin-left: 15%;
    margin-top: 10%;
    display: flex
`

const TextUtilArea = styled.div`
  position: absolute;
  display:flex;
  float: right;
  z-index: 10;
  top: 74.5%;
  left: 78%;
  height: 40px;
`
const TextCounter = styled.label`
  margin: auto 10px;
`

export const GrammarArea = () => {
    const [ textCount, setCount ] = useState(0);
    return (
        <Area>
        <InputTextArea setCount={setCount}/>
        <ResultTextArea/>
        <TextUtilArea>
        <TextCounter>{textCount}/500</TextCounter>
        <Button>검사</Button>
        </TextUtilArea>
        </Area>
    )
} 