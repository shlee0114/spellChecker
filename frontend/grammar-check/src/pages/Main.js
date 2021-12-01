import React from 'react'
import {GrammarArea} from '../components/grammar/GrammarArea'
import { HistoryArea } from '../components/history/HistoryArea'

export const Main = () => {
    return (
        <main>
        <HistoryArea/>
            <GrammarArea/>
        </main>
    )
}