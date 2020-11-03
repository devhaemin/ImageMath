import React, { Component } from 'react';

class write extends Component {
  constructor(props) {
    super(props)
    this.state = {
        file : null,
        title : '',
        contents :'',
        fileName : '',
    }
  }

  _handleFormSubmit(e) {
      e.preventDefault()
      this.addContents()
      .then((Response) => {
          console.log(Response.data);
      })
  }

  _handleFileChange(e){
      this.setState({
          file:e.target.files[0],
          fileName: e.target.value
      })
  }

  _handleValueChange(e) {
      let nextState = {};
      nextState[e.target.name] = e.target.value;
      this.setState(nextState);
  }

  
  _addContents(){
    const formData = new FormData();
    formData.append('video', this.state.file)
    formData.append('title', this.state.title)
    formData.append('contents', this.state.contents)

    fetch("http://apidoc.imagemath.kr:3000/{post}" , {
        method : 'POST',
        body : formData
    })
    .then(response => response.json())
    .catch(error => console.error('Error:', error))
    .then(response => console.log('Success:', JSON.stringify(response)));
}

  render() {

    return (
        <div className='Write'>
          <div>
              <input type='text' id='title_txt' name='title' placeholder='제목'/>
          </div>
          <div>
              <textarea id='content_txt' name='contents' placeholder='내용을 입력하세요.'></textarea>
          </div>
          <div>
              <input type='file' name='file' />
          </div>
        </div>
    );
  }
}

export default write;

