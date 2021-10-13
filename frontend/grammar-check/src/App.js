import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import {Main} from './pages'

function App() {

    return (
        <body>
          <BrowserRouter>
            <Route exact path="/" component={Main}/>
          </BrowserRouter>
        </body>
    );
}

export default App;