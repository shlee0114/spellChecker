import React, { useState } from "react";
import styled from "styled-components";
import { shadow } from "../../lib/StyleUtils";
import logBtn from '../../static/images/logBtn.png'
import { SentenceLog } from "./SentenceLog";
import { WordLog } from "./WordLog";
import { Button } from "../common/Button";

const HistoryArticle = styled.article`
  right: 8.6rem;
  position: absolute;
  width: 20%;
  height: 60vh;
  display: flex;
  ${shadow(0)}
  z-index:11;
  background: #FBFBFB;
  flex-direction: column;
  transition : .4s all;
`;

const SelectedArea = styled.div`
  height:10%;
  padding: 0.3rem 2rem 0;
  display: flex;
`

const SelectedText = styled.div`
  align-self: center;
  margin: 0 1rem;
  font-size: 12px;
  font-family: 'Noto Sans KR';
  color: #888888;
`

const Spacer = styled.div`
  flex-grow: 1;
`;

const OpenButton = styled.div`
  position: absolute;
  padding: 0 6rem;
  right: 0;
`;

const OpenImg = styled.img`
height: 2.5rem;
`

const TextArea = styled.div`
  height: 100%;
  display flex;
  padding: 0 2.3rem;
`

export const LogArea = () => {

  const selectedStyle = {
    textDecoration :"underline",
    textUnderlinePosition : "under",
    color : "#1DB9C3"
  }

  const openLogStyle = {
    width: "20%",
    height: "60vh",
    opacity: "1"
  }

  const closeLogStyle = {
    width: "0",
    height: "0",
    opacity: "0"
  }

  const [wordStyle, setWordStyle] = useState(selectedStyle)
  const [sentenceStyle, setSentenceStyle] = useState([])
  const [logStyle, setLogStyle] = useState(closeLogStyle)

  const [word, setWord] = useState(true)
  const [sentence, setSentence] = useState(false)
  const [isWord, setIsWord] = useState(true)

  const [isOpened, setIsOpened] = useState(false) 

  const openOrClose = (open) => {
    if(isOpened === open) return

    open ? openLog() : closeLog()
    setIsOpened(open) 
  }

  const openLog = (_) => {
    setLogStyle(openLogStyle)
    setWord(isWord)
    setSentence(!isWord)
  }

  const closeLog = (_) => {
    setLogStyle(closeLogStyle)
    setIsWord(word)
    setWord(false)
    setSentence(false)
  }

  const clickSentence = (_) => {
    setWord(false)
    setSentence(true)
    setWordStyle([])
    setSentenceStyle(selectedStyle)
  }

  const clickWord = (_) => {
    setWord(true)
    setSentence(false)
    setWordStyle(selectedStyle)
    setSentenceStyle([])
   
  }

  return (
    <section>
      <HistoryArticle style={logStyle}>
        <SelectedArea>
          <SelectedText onClick={clickWord} style={wordStyle}>단어</SelectedText>
          <SelectedText onClick={clickSentence} style={sentenceStyle}>문장</SelectedText>
          <Spacer />
        <Button
          style={{
            fontWeight: "500",
            color: "gray",
            fontSize: "1.5rem",
            paddingRight: "0" 
          }}
          onClick={(_) => {
            openOrClose(false)
          }}
        >
          X
        </Button>
        </SelectedArea>
        <TextArea>
        <SentenceLog opened={sentence}/>
        <WordLog opened={word}/>
        </TextArea >
      </HistoryArticle>
      <OpenButton>
        <OpenImg
          onClick={(_) => {
            openOrClose(true)
          }}
          src={logBtn}
        />
      </OpenButton>
    </section>
  );
};
