import React, { Component } from 'react';
import { Route, Link, Switch } from 'react-router-dom';
import '../App.css';

import Modal from 'react-awesome-modal';
class header extends Component {
    constructor(props){
        super(props);
        this.state = {
            visible : false
        }
    }

    _openModal = function() {
        this.setState({
            visible : true
        });
    }

  render() {

    return (
        <div class='header_grid'>
            <div> </div>
            <div className='acenter'>
                <Route path='/'/>
                <Link className='link_tit' to='/'> <h3>Image Math </h3> </Link>
            </div>

            <div className='acenter'> 
                <h5> 관리자 로그인 </h5>
            </div>
        </div>
    );
  }
}

export default header;

