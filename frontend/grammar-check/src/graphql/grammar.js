import { gql } from '@apollo/client'

const grammarCheck = (text) => {
    return gql`
    query check{
        check(text:"${text}") { 
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

const searchLog = () => {
    return gql`
        query log{
            log{
                error,
                fixed,
                count,
                fixedTime
            }
        }
    `
}

const queries = {
    GRAMMAR_CHECK : grammarCheck,
    LOG_INSERT : logInsert,
    SEARCH_LOG : searchLog
}

export default queries