import React from 'react'
import styled from 'styled-components'
import nayng from '../../static/images/nayng.png'

const HeaderContents = styled.div`
    width: 100%;
    height: 20vh;
    display: flex;
    flex-direction: column;
    align-items: start;
    padding-left: 6rem;
`;

const Logo = styled.div`
    font-size: 48px;
    letter-spacing: 2px;
    color: #1DB9C3;
    font-family: 'godo maum';
    padding-top: 3%;
`;

const LogoImg = styled.img`
    vertical-align: sub;
`

const Caution = styled.div`
    font-size: 15px;
    color: #555555;
    font-family: 'Noto Sans KR';
    margin: 5px;
`

export const Header = () => {
    return (
                <HeaderContents>
                    <Logo>이승현의 맞춤법 검사기<LogoImg src={nayng}/></Logo>
                    <Caution>네이버 검사기 사용</Caution>
                </HeaderContents>
    );
};