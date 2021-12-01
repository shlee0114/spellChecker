import React, { useRef } from "react";
import styled from "styled-components";
import oc from "open-color";
import { shadow } from "../../lib/StyleUtils";
import { CloseButton } from "../common/CloseButton";
import { gsap } from "gsap";
import { Button } from "../common/Button";

const OutSideArticle = styled.article`
  top: 0;
  position: absolute;
  width: 100%;
  height: 0vh;
`;

const HistoryArticle = styled.article`
  bottom: -40vh;
  position: absolute;
  width: 100%;
  height: 40vh;
  display: flex;
  flex-direction: column;
  ${shadow(1)}
  z-index:11;
  background: #efefec;
`;

const GradientBorder = styled.div`
  height: 3px;
  background: linear-gradient(to right, ${oc.teal[6]}, ${oc.cyan[5]});
`;

const OpenButton = styled.div`
  position: absolute;
  bottom: 0;
  margin: 0 48.4%;
`;

export const HistoryArea = ({}) => {
  const OutSideAreaRef = useRef();
  const logAreaRef = useRef();

  const openOrClose = (e) => {
    const bottom = e ? "0" : "-40vh";
    const height = e ? "60vh" : "0";
    gsap.to(logAreaRef.current, {
      bottom: bottom,
      duration: 0.5,
    });
    gsap.to(OutSideAreaRef.current, {
      height: height,
    });
  };

  return (
    <section>
      <OutSideArticle
        ref={OutSideAreaRef}
        onClick={(_) => {
          openOrClose(false);
        }}
      />
      <HistoryArticle ref={logAreaRef}>
        <GradientBorder />
        <CloseButton
          style={{ right: "0" }}
          onClick={(_) => {
            openOrClose(false);
          }}
        />
      </HistoryArticle>
      <OpenButton>
        <Button
          onClick={(_) => {
            openOrClose(true);
          }}
        >
          기록
        </Button>
      </OpenButton>
    </section>
  );
};
