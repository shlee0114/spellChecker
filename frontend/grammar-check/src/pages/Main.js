import React, { useState } from 'react'
import '../main.css'
import url from "../apolloClient"
import {grammar} from '../graphql/index'
import axios from 'axios'

export default function Main (){

    const [ fixedText, setFixedText ] = useState('');
    const [ textCount, setCount ] = useState(0);
    const [ sendText, setText ] = useState('');
    const [ resultist, setResult ] = useState([]);

    var isCtrl = false
    var serverSendTimer = null

    const inputTextKeyUp = (e) => {
        if(e.which === 17) isCtrl=false;  
    }

    const inputTextKeyDown = (e) => {
        const inputArea = document.getElementById('input')

        if(e.which === 17) isCtrl=true;  
        if(e.which === 192 && isCtrl === true) {  
            if(resultist.length !== 0){
                resultist.map((result) => (
                    inputArea.value = inputArea.value.replaceAll(result.errorText, result.fixedText)
                ))
            }else{
                inputArea.value = inputArea.value.replace(new RegExp(sendText+ '$'), fixedText)
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
            axios.get(`http://localhost:8089/api/check?grammar=${inputArea.value}`)
            .then(result => {
                const list = result.data.response
                const resultist = []
                var fix = ''

                list.map((info) => (
                    fix += `${info.errorText} -> ${info.fixedText}\n`,
                    resultist.push({
                        errorText : info.errorText,
                        fixedText : info.fixedText
                    })
                ))

                setFixedText(fix)
                setResult(resultist)
            })
        }else{
            url.query({
                query : grammar.GRAMMAR_CHECK(sendText)
            }).then(result => {
                if(!(result.errors??false)){
                    if(result.data.check.fixedText != null){
                        setFixedText(result.data.check.fixedText)
                    }else{
                        setFixedText(sendText)
                    }
                }
            })
        }
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
                        {fixedText}
                    </textarea>
                </div>
            </section>
            <section className="searchHistory">
                <button className="checkWord">변경 낱말 검사</button>
                <button className="checkSentence">변경 문장 검사</button>
            </section>
        </article>
    )
}