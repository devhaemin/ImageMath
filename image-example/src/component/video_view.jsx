import React,  {Component} from 'react';

import {_getAccessToken} from "../cookie";

class VideoView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: '',
            video: '',
            students : [],
            checkedStudentSeq : []
        }
        this._deleteVideo = this._deleteVideo.bind(this);
        this._getVideo = this._getVideo.bind(this);
    }

    componentDidMount() {
        this.setState({
            token : _getAccessToken()
        })
        this._getVideo()
        this._getStudentList();
    }

    _deleteVideo = function (){
        const videoSeq = this.props.match.params.videoSeq;

        fetch(`http://api-doc.imagemath.kr:3000/video/${videoSeq}`,{
            method : 'DELETE',
            headers: {
                'x-access-token': _getAccessToken()
            }
        })
            .then(response => response.json())
            .then(response => {
                console.log(response)
                if(response.status===200){
                    return response
                    alert('삭제가 완료되었습니다')
                }
            })
    }

    _getVideo = function () {
        const videoSeq = this.props.match.params.videoSeq;

        fetch(`http://api-doc.imagemath.kr:3000/file/video/attachedVideo?videoSeq=${videoSeq}`, {
            headers:{
                'x-access-token': _getAccessToken()
            }
        })
            .then(response => response.json())

            .then(response => {
                console.log(response)
                this.setState({
                    video : response[0].fileUrl
                })
                console.log(this.state.video)
            })
    }

    _getStudentList = function (){
        const lectureSeq = this.props.match.params.lectureSeq;

        fetch(`http://api-doc.imagemath.kr:3000/lecture/student/${lectureSeq}` ,{
            method :'GET',
            headers: {
                'x-access-token': _getAccessToken()
            },
        })
            .then(response => response.json())
            .then(response => {
                console.log(response)
                this.setState({
                    students: response
                })
            })
    }

    _isChecked = function (){
        
    }


    render() {
        const list = this.state.students

        return (
            <div>
                <div>
                    <video width={'350px'} height={'300px'} controls>
                        <source src={this.state.video} type="video/mp4"/>
                    </video>
                </div>
                <a href={this.state.video}>다운로드</a>
                <button onClick={this._deleteVideo}>삭제</button>
                {list ? list.map((student) => {
                        return(
                            <div className={'student_list'} key={student.userSeq}>
                                <div className={'student_seq'}>{student.userSeq}</div>
                                <div>
                                    {student.name}
                                </div>
                                <div><input className={'checkbox'} type={'checkbox'} /></div>
                            </div>
                        )
                    })
                    :null}
            </div>
            
        );
    }
}

export default VideoView;