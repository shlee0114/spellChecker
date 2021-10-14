import React, { useState } from 'react'
import '../main.css'
import {default as client} from "../apolloClient";
import {grammar} from '../graphql/index'

export default function Main (){

    const [ fixedText, setFixedText ] = useState('');
    const [ textCount, setCount ] = useState(0);

    var isCtrl = false
    var serverSendTimer = null

    const inputTextKeyUp = (e) => {
        if(e.which === 17) isCtrl=false;  
    }

    const inputTextKeyDown = (e) => {
        if(e.which === 17) isCtrl=true;  
        if(e.which === 192 && isCtrl === true) {  
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
            setCount(document.getElementById('input').value.length)
        }, 0)
        
    }

    const sendServer = (totalYn) => {

        setFixedText('')
        const sendText = document.getElementById('input').value.replaceAll('\n', ' ').split(" ").pop()
        if(sendText.trim() === ''){
            return true
        }

        if(totalYn){

        }else{
            client.url.query({
                query : grammar.GRAMMAR_CHECK(sendText)
            }).then(result => {
                if(!(result.errors??false)){
                    if(result.data.query.fixedText != null){
                        setFixedText(result.data.query.fixedText)
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
                <button className="checkAll" onClick={() => {sendServer(true)}}>전체 검사</button>
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