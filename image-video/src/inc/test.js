import React, { Component } from 'react';


class test extends Component {
    constructor(props) {
        super(props)
    
      }    
    
    render() {
        return (
            <div>
                <h3>My name is {this.props.match.params.data} </h3>
            </div>
        );
    }
}

export default test;