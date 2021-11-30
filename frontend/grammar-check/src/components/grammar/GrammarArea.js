import React from 'react'
import styled from 'styled-components'
import {InputTextArea} from './InputTextArea'
import {ResultTextArea} from './ResultTextArea'

const Area = styled.section`
    width: 70%;
    height: 60vh;
    margin-left: 15%;
    margin-top: 10%;
    background: #F7F7F7;
    box-sizing: border-box;
    border: 1px solid rgba(95, 92, 92, 0.47);
`

export const GrammarArea = () => {
    return (
        <Area>
        <InputTextArea/>
        <ResultTextArea/>
        </Area>
    )
} 