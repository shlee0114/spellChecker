import { gql } from '@apollo/client'

const grammarCheck = (text) => {
    return gql`
    {query: check(text : "${text}") { errorText fixedText } }`
}

export default{
    GRAMMAR_CHECK : grammarCheck,
}