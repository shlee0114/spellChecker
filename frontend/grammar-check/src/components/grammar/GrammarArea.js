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

export const GrammarArea = () => {
    return (
        <Area>
        <InputTextArea/>
        <ResultTextArea/>
        </Area>
    )
} 