import React, {Component} from 'react';

class write extends Component {
    constructor(props) {
        super(props)

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
                    <input type='file' name='file'/>
                </div>
            </div>
        );
    }
}

export default write;

