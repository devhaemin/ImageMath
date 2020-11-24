import React, {Component} from 'react';
import {_getAccessToken} from "../cookie";

export class Write extends Component {
    constructor(props) {
        super(props);
        this.state ={
            file : null,
            data : '',
        }
    }
    _onChange = function(files){
        this.setState({
            file : files[0]
        })
    }

    _submitBoard = function () {
        const title = document.getElementsByName('title')[0].value.trim();
        const contents = document.getElementsByName('contents')[0].value.trim();

        console.log(this.state.title)

        if(title==="") {
            return alert('제목을 입력해주세요.');
        }else if(contents==="") {
            return alert('내용을 입력해주세요.');
        }else if(this.state.file==="") {
            return alert('파일을 첨부해주세요');
        }
        else{
            const formData = new FormData();

            const board_id = this.props.match.params.data;

            formData.append('title', title);
            formData.append('contents',contents);
            formData.append('video', this.state.file);

            fetch(`http://api-doc.imagemath.kr:3000/video/${board_id}`,{
                method : 'POST' ,
                headers: {
                    'x-access-token': _getAccessToken()
                },
                body: formData
            })
                .then(response => {
                    if (response.status === 200) {
                        alert('포스트가 등록되었습니다')
                        this.props.history.goBack()

                    } else {
                        alert("포스트 등록에 실패하였습니다.")
                        this.props.history.goBack()

                    }
                    return response
                })
                .then(response => {
                    console.log(response)
                })

        }
    }

    render() {
        return (
            <div className={'Write'}>
                <div>
                    <input type={'text'} id={'title_txt'} name={'title'} placeholder={'제목'}/>
                </div>

                <div>
                    <textarea id={'content_txt'} name={'contents'} placeholder={'내용을 입력하세요'}></textarea>
                </div>

                <div>
                    <input type={'file'} name={'files'} id={'content_file'} onChange={(e) => this._onChange(e.target.files)}/>
                </div>

                <div id={'post_submit'}>
                    <button onClick={()=>this._submitBoard()}>포스트 등록</button>
                </div>

            </div>
        );
    }
}

export default Write;