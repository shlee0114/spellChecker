import React from 'react'
import '../main.css'

export default function Main (){

    const inputText = (e) => {

    }

    return (
        <article>
            <section className="checkGrammar">
                <label className="totalTextCount">0/500</label>
                <label className="infoLabel">ctrl + `키를 누르시면 맞춤법 수정이 됩니다.</label>
                <button className="checkAll">전체 검사</button>
                <div className="textarea">
                    <textarea className="inputArea" onChange={()=>inputText(this)}>

                    </textarea>
                    <textarea className="resultArea">

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