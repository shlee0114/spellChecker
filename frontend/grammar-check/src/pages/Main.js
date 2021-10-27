import React, { useState, useEffect } from 'react'
import '../main.css'
import url from "../apolloClient"
import {grammar} from '../graphql/index'
import axios from 'axios'

require('dotenv').config()

export default function Main (){

    const [ fixedText, setFixedText ] = useState('');
    const [ wordHistoryText, setWordHistory ] = useState('');
    const [ sentenceHistoryText, setSentenceHistory ] = useState('');
    const [ textCount, setCount ] = useState(0);
    const [ sendText, setText ] = useState('');
    const [ resultist, setResult ] = useState([]);
    const [ip, setIP] = useState('');
    const serverIp = 'http://zifori.me:8089/api/'

    var isCtrl = false
    var serverSendTimer = null

    const getData = async () => {
        const res = await axios.get('https://geolocation-db.com/json/')
        console.log(res.data);
        setIP(res.data.IPv4)
    }

    useEffect( () => {
        getData()
    }, [])

    const inputTextKeyUp = (e) => {
        if(e.which === 17) isCtrl=false;  
    }

    const inputTextKeyDown = (e) => {
        const inputArea = document.getElementById('input')

        if(e.which === 17) isCtrl=true;  
        if(e.which === 192 && isCtrl === true) {  
            if(resultist.length !== 0){
                const beforeText = inputArea.value

                resultist.map(result => {
                    inputArea.value = inputArea.value.replaceAll(result.errorText, result.fixedText)
                    url.mutate({
                        mutation:grammar.LOG_INSERT(result.errorText, result.fixedText, ip)
                    }).catch(res => {
                        console.log(res)
                    })
                })

                const afterText = inputArea.value

                axios.post(`${serverIp}log`, 
                {errorText: beforeText, fixedText: afterText, fixedCount: resultist.length, ip: ip}
                ).catch(res => {
                    console.log(res)
                })
            }else{
                inputArea.value = inputArea.value.replace(new RegExp(sendText+ '$'), fixedText)
                
                url.mutate({
                    mutation:grammar.LOG_INSERT(sendText, fixedText, ip)
                }).catch(res => {
                    console.log(res)
                })
            }
            return false;  
        }  

        if(serverSendTimer != null){
            clearTimeout(serverSendTimer)
        }

        if(e.which === 32){
            sendServer(false)
        }else{
            serverSendTimer = setTimeout( () => {sendServer(false)}, 500 )
        }

        setTimeout(() => {
            setCount(inputArea.value.length)
        }, 0)
        
    }

    const sendServer = (totalYn) => {
        const inputArea = document.getElementById('input')
        const sendText = inputArea.value.replaceAll('\n', ' ').split(" ").pop()


        setFixedText('')
        setText(sendText)

        if(sendText.trim() === ''){
            return true
        }

        setResult([])
        if(totalYn){
            axios.get(`${serverIp}check?grammar=${inputArea.value}`)
            .then(result => {
                const list = result.data.response
                const resultist = []
                var fix = ''

                list.map(info => (
                    fix += `${info.errorText} -> ${info.fixedText}\n`,
                    resultist.push({
                        errorText : info.errorText,
                        fixedText : info.fixedText
                    })
                ))

                setFixedText(fix)
                setResult(resultist)
            }).catch(res => {
                console.log(res)
            })
        }else{
            url.query({
                query : grammar.GRAMMAR_CHECK(sendText)
            }).then(result => {
                if(!(result.errors??false)){
                    if((result.data.check.fixedText??"") !== ""){
                        setFixedText(result.data.check.fixedText)
                    }else{
                        setFixedText(sendText)
                    }
                }
            }).catch(res => {
                console.log(res)
            })
        }
    }

    const searchWordHistory = () => {
        url.query({
            query : grammar.SEARCH_LOG()
        }).then(result => {

            const list = result.data.log
            var log = ''

            list.map(info => (
                log += `${info.error} -> ${info.fixed}\ntime : ${info.fixedTime}\n\n`
            ))
            setWordHistory(log)
        }).catch(res => {
            console.log(res)
        })
    }

    const searchSentenceHistory = () => {
        axios.get(`${serverIp}log`)
        .then(result => {
            const list = result.data.response
            var log = ''

            list.map(info => (
                log += `${info.error} -> ${info.fixed}\ncount : ${info.count}\ntime : ${info.fixedTime}\n\n`
            ))
            setSentenceHistory(log)
        }).catch(res => {
            console.log(res)
        })
    }

    return (
        <article>
            <section className="checkGrammar">
                <label className="totalTextCount">{textCount}/500</label>
                <label className="infoLabel">ctrl + `키를 누르시면 맞춤법 수정이 됩니다.</label>
                <button className="checkAll" onClick={() => sendServer(true)}>전체 검사</button>
                <div className="textarea">
                    <textarea className="inputArea" id="input" cols="30" rows="5"
                    onKeyDown={(e)=>{inputTextKeyDown(e)}}
                    onKeyUp={(e) => {inputTextKeyUp(e)}}>
                    </textarea>
                    <textarea className="resultArea" readOnly value={fixedText}>
                    </textarea>
                </div>
            </section>
            <section className="history">
                <button className="searchWordHistory" onClick={() => searchWordHistory()}>낱말 수정 기록</button>
                <button className="searchSentenceHistory" onClick={() => searchSentenceHistory()}>문장 수정 기록</button>
                <div className="historyTextarea">
                    <textarea className="wordHistory" readOnly value={wordHistoryText}>
                    </textarea>
                    <textarea className="sentenceHistory" readOnly value={sentenceHistoryText}>
                    </textarea>
                </div>
            </section>
        </article>
    )
}