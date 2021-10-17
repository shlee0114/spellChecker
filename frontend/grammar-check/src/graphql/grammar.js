import { gql } from '@apollo/client'

const grammarCheck = (text) => {
    return gql`
    query check{
        check(text : {text:"${text}"}) { 
            fixedText 
        }
    }  
    `
}

export default{
    GRAMMAR_CHECK : grammarCheck,
}