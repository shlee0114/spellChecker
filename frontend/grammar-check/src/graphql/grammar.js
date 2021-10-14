import { gql } from '@apollo/client'

const grammarCheck = (text) => {
    return gql`
    {query: check(text : "${text}") { fixedText } }`
}

const querys = {
    GRAMMAR_CHECK : grammarCheck
}

export default querys