import React,  {Component} from 'react';

import {_getAccessToken} from "../cookie";

class VideoView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: '',
            video: '',
            students : [],
            accessStudentSeq : []
        }
        this._deleteVideo = this._deleteVideo.bind(this);
        this._getVideo = this._getVideo.bind(this);
        this._isChecked = this._isChecked.bind(this);
    }

    componentDidMount() {
        this.setState({
            token : _getAccessToken()
        })
        this._getVideo();
        this._getStudentList();
        this._accessStudent();
    }

    _deleteVideo = function (){
        const videoSeq = this.props.match.params.videoSeq;

        fetch(`http://api-doc.imagemath.kr:3000/video/${videoSeq}`,{
            method : 'DELETE',
            headers: {
                'x-access-token': _getAccessToken()
            }
        })
            .then(response => {
                if(response.status===200){
                    alert('삭제되었습니다')
                    this.props.history.goBack()
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
                this.setState({
                    video : response[0].fileUrl
                })
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
                this.setState({
                    students: response
                })
                console.log(response)
            })
    }

    _isChecked = function (e){
        if(e.target.checked) {
            this.state.ischecked = true
        } else{
            this.state.ischecked = false
        }


        const videoSeq = this.props.match.params.videoSeq;
        const userSeq = e.target.value;
        const hasAccess = this.state.ischecked

        fetch(`http://api-doc.imagemath.kr:3000/video/${videoSeq}/${userSeq}`,{
            method :'PATCH',
            headers: {
                'x-access-token': _getAccessToken()
            },
            body: JSON.stringify({
                hasAccess : hasAccess
            }),
        })
            .then(response=> response.json())
            .then(response => {
                console.log(response)
            })
    }

    _accessStudent = function (){
        const videoSeq = this.props.match.params.videoSeq;

        fetch(`http://api-doc.imagemath.kr:3000/video/${videoSeq}/user`,{
            headers: {
                'x-access-token': _getAccessToken()
            },
        })
            .then(response => response.json())
            .then(response => {
                const list = response;
                list.map((accessStudent) => {
                    this.state.accessStudentSeq.push(accessStudent.userSeq)
                })
                console.log(this.state.accessStudentSeq)
            })
    }

    _isAccess=function (userSeq){
        this.state.accessStudentSeq.map((accessStudent)=>{
            if(accessStudent===userSeq) return true
        })
    }


    render() {
        const list = this.state.students

        return (
            <div>
                <div>
                    <video width={'100%'} height={'100%'} controls>
                        <source src={this.state.video} type="video/mp4"/>
                    </video>
                </div>
                <div className={'video_view_btn'}>
                    <a className={'download_btn'} href={this.state.video}>다운로드</a>
                    <button className={'download_btn'} onClick={this._deleteVideo}>삭제</button>
                </div>

                <div className={'video_view_title'}>
                    <div></div>
                    <div className={'list_title'}>학생 목록</div>
                    <div className={'list_access'}>권한</div>
                </div>


                {list ? list.map((student) => {
                        return(
                            <div className={'video_list'} key={student.userSeq}>
                                <div>{student.userSeq}</div>
                                <div>
                                    {student.name}
                                </div>
                                {this._isAccess(student.userSeq)
                                    ? <div className={'std_checkbox'}><input type={'checkbox'} className={'checkbox'} value={student.userSeq} onChange={this._isChecked} checked/></div>
                                    : <div><input type={'checkbox'} className={'checkbox'} value={student.userSeq} onChange={this._isChecked} /></div>
                                }
                            </div>
                        )
                    })
                    :null}
            </div>
            
        );
    }
}

export default VideoView;