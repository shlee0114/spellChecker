import React from 'react'
import styled from 'styled-components'
import oc from 'open-color'
import { shadow } from '../../lib/StyleUtils'

const Wrapper = styled.div `
font-weight : 600;
color : ${oc.cyan[6]};
border : 1px solid ${oc.cyan[6]};
padding : 0.5rem;
padding-bottom : 0.4rem;
cursor : pointer;
border-radius : 2px;
text-decoration : none;
transition : .2s all;
margin-right: 10px;

&:hover{
    background : ${oc.cyan[6]};
    color : white;
    ${shadow(0)}
}

&:active{
    transform : translateY(3px);
}
`

export const Button = ({children, onClick}) => (
    <Wrapper onClick={onClick}>
        {children}
    </Wrapper>
)