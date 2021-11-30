import React from 'react'
import styled from 'styled-components'
import oc from 'open-color'

const Wrapper = styled.div `
font-size: 1.3rem;
position: absolute;
font-weight : 600;
color : ${oc.cyan[6]};
padding : 0.6rem;
cursor : pointer;
transition : .2s all;
margin-right: 10px;

&:hover{
    color : white;
}

&:active{
    transform : translateY(3px);
}
`

export const CloseButton = ({onClick}) => (
    <Wrapper onClick={onClick}>
        X
    </Wrapper>
)