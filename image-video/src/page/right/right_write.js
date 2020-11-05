import React, { Component } from 'react';
import '../main.css';
import write from '../write';

class right_write extends Component {
  constructor(props) {
    super(props)
    this.state = {
        file : null,
        title : '',
        contents :'',
    }
  }

  _submitBoard = function() {
        const title = document.getElementsByName('title')[0].value.trim();
        const contents = document.getElementsByName('contents')[0].value.trim();
        const file = document.getElementsByName('file')[0].value.trim();

      if(title ===""){
          return alert('제목을 입력해주세요.');
      }
      else if( contents ===""){
          return alert('내용을 입력해주세요.');
      }
      else if( file ===""){
          return alert('파일을 첨부하세요.');
      }

      const data = { title : title, contents : contents, file : file};

      

    fetch("http://apidoc.imagemath.kr:3000/video" , {
        method : 'POST',
        body: JSON.stringify({
            data
        })
    })
    .then(response => response.json())
    .catch(error => console.error('Error:', error))
    .then(response => console.log('Success:', JSON.stringify(response)));
  }

  

  render() {
    return (
        <div>
          <div id='post_submit'>
            <button onClick={() => this._submitBoard()}> 포스트 등록 </button>
          </div>
        </div>
    );
  }
}

export default right_write;
