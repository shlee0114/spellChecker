import React from 'react'
import '../main.css'

export default function Main (){

    var isCtrl = false

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
        sendText = e.target.value.split(" ").pop()

        if(e.which === 32){
            serverSendTimer()
        }else{
            serverSendTimer = setTimeout( sendServer, 500 )
        }
    }

    const sendServer = (totalYn) => {
        if(totalYn){

        }else{
            
        }
    }

    return (
        <article>
            <section className="checkGrammar">
                <label className="totalTextCount">0/500</label>
                <label className="infoLabel">ctrl + `키를 누르시면 맞춤법 수정이 됩니다.</label>
                <button className="checkAll">전체 검사</button>
                <div className="textarea">
                    <textarea className="inputArea" onKeyDown={(e)=>{inputTextKeyDown(e)}} onKeyUp={(e) => {inputTextKeyUp(e)}}>

                    </textarea>
                    <textarea className="resultArea" readOnly>

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