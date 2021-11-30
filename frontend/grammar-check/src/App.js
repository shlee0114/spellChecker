import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import {Main} from './pages'
import {Header} from './components/header/Header'

function App() {

    return (
          <BrowserRouter>
            <Header/>
            <Route exact path="/" component={Main}/>
          </BrowserRouter>
    );
}

export default App;