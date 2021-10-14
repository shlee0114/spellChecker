import { gql } from '@apollo/client'

const GRAMMAR_CHECK = (text) => {
    return gql`
    {query: check(text : "${text}") { fixedText } }`
}

export default GRAMMAR_CHECK