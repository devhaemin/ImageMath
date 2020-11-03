import React, { Component } from 'react';
import '../main.css';
import write from '../write';

class right_write extends Component {
  constructor(props) {
    super(props)
  }

  render() {
    return (
        <div>
          <div id='post_submit'>
            <button onClick ={() => write._addContents()}> 포스트 등록 </button>
          </div>
        </div>
    );
  }
}

export default right_write;
