import React, { Component } from 'react';
import './App.css';
import { Route, Link, Switch } from 'react-router-dom';

import { Head } from './inc'

class App extends Component {
  constructor(props) {
    super(props)
    this.state = {
    }
  }

  render() {
    return(
    <div>
        <Head />
    </div>
    )
  }
}

export default App;

