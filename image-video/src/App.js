import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';


import { Home, Test } from './inc';

class App extends Component {
  constructor(props){
    super(props)
    this.state = {

    }
  }
  render() {
    return (
      <div className='App'>
        <BrowserRouter>
          <Route path="/" component={Home} exact/>
          <Route path="/test" component={Test}/>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;