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

const logInsert = (errorText, fixedText, ip) => {
    return gql`
    mutation log{
        log(input: {errorText:"${errorText}", fixedText:"${fixedText}", fixedCount:0, ip:"${ip}"})
    }
    `
}

export default{
    GRAMMAR_CHECK : grammarCheck,
    LOG_INSERT : logInsert,
}