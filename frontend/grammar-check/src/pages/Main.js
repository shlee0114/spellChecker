import React from 'react'
import {GrammarArea} from '../components/grammar/GrammarArea'
import { LogArea } from '../components/history/LogArea'

export const Main = () => {
    return (
        <main>
        <LogArea/>
            <GrammarArea/>
        </main>
    )
}