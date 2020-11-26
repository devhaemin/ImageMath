import React,  {Component} from 'react';
import ReactPlayer from 'react-player'
import {_getAccessToken} from "../cookie";

class VideoView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: '',
            video: '',
            students : [],
            ischecked : ''
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
        const videoSeq = this.props.match.params.videoSeq;

        fetch(`http://api-doc.imagemath.kr:3000/video/${videoSeq}/${lectureSeq}/user` ,{
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
                console.log(this.state.students)
            })
    }

    _isChecked = function (e){
        if(e.target.checked) {
            this.state.ischecked = 1
        } else{
            this.state.ischecked = 0
        }

        const videoSeq = this.props.match.params.videoSeq;
        const userSeq = e.target.value;
        const hasAccess = this.state.ischecked

        fetch(`http://api-doc.imagemath.kr:3000/video/${videoSeq}/${userSeq}`,{
            method :'PATCH',
            headers: {
                'x-access-token': _getAccessToken(),
                'Content-type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify({
                hasAccess : hasAccess
            }),
        })
            .then(response=> {
                console.log(response);
                return response.json()
            })
            .then(response => {
                console.log(response)
            })
    }

    render() {
        const list = this.state.students

        return (
            <div>
                <div>
                    <ReactPlayer url={this.state.video} width={'100%'} height={'500px'} controls/>
                </div>
                <div className={'video_view_btn'}>
                    <a className={'download_btn'} href={this.state.video}>다운로드</a>
                    <button className={'download_btn'} onClick={this._deleteVideo}>삭제</button>
                </div>

                <div className={'video_view_title'}>
                    <div className={'list_std'}>학생 번호</div>
                    <div className={'list_title'}>학생 목록</div>
                    <div className={'list_access'}>권한</div>
                </div>


                {list ? list.map((student) => {
                        return(
                            <div className={'video_list'} key={student.userSeq}>
                                <div className={'green_font'}>{student.userSeq}</div>
                                <div>
                                    {student.name}
                                </div>
                                {student.hasAccess==1
                                    ?   <div className={'std_checkbox'}><input type={'checkbox'} className={'checkbox'} value={student.userSeq} onChange={this._isChecked} defaultChecked={'checked'} /></div>
                                    :   <div className={'std_checkbox'}><input type={'checkbox'} className={'checkbox'} value={student.userSeq} onChange={this._isChecked} /></div>
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