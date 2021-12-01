import React from "react";
import styled from "styled-components";
import oc from "open-color";
import { shadow } from "../../lib/StyleUtils";
import { CloseButton } from "../common/CloseButton";

const OutSideArticle = styled.article`
  top: 0;
  position: absolute;
  width: 100%;
  height: 60vh;
`;

const HistoryArticle = styled.article`
  bottom: 0;
  position: absolute;
  width: 100%;
  height: 40vh;
  display: flex;
  flex-direction: column;
  ${shadow(1)}
  z-index:11;
  background: #EFEFEC;
`;

const GradientBorder = styled.div`
  height: 3px;
  background: linear-gradient(to right, ${oc.teal[6]}, ${oc.cyan[5]});
`;

export const HistoryArea = ({}) => {
  return (
    <section>
      <OutSideArticle />
      <HistoryArticle>
        <GradientBorder />
        <CloseButton style={{ right: "0" }} />
      </HistoryArticle>
    </section>
  );
};
