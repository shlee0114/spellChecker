import React from 'react'
import styled from 'styled-components'
import oc from 'open-color'
import { shadow } from '../../lib/StyleUtils'

// 상단 고정, 그림자
const Positioner = styled.header`
    display: flex;
    flex-direction: column;
    width: 100%;
    ${shadow(0)}
`;


// 해더의 내용
const HeaderContents = styled.div`
    width: 100%;
    height: 5vh;
    display: flex;
    flex-direction: row;
    align-items: center;

    padding-left: 6rem;
`;

// 로고
const Logo = styled.div`
    font-size: 1.6rem;
    letter-spacing: 2px;
    color: ${oc.teal[7]};
    font-family: 'godo maum';
`;

const Caution = styled.div`
    font-size: 1rem;
    color: ${oc.teal[7]};
    font-family: 'godo maum';
    margin: 5px;
`

// 하단 그래디언트 테두리
const GradientBorder = styled.div`
    height: 3px;
    background: linear-gradient(to right, ${oc.teal[6]}, ${oc.cyan[5]});
`;

export const Header = () => {
    return (
        <Positioner>
                <HeaderContents>
                    <Logo>이승현의 맞춤법 검사기</Logo>
                    <Caution>네이버 맞춤법 검사기 사용</Caution>
                </HeaderContents>
            <GradientBorder/>
        </Positioner>
    );
};