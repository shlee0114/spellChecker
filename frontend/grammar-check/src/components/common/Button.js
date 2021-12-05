import React from 'react'
import styled from 'styled-components'

const Wrapper = styled.div `
font-size: 1.3rem;
font-weight : 600;
color : #1DB9C3;
padding : 0.6rem;
cursor : pointer;
transition : .2s all;
margin-right: 10px;

&:hover{
    color : gray;
}

&:active{
    transform : translateY(3px);
}
`

export const Button = ({children, onClick, style}) => (
    <Wrapper onClick={onClick} style={style}>
        {children}
    </Wrapper>
)