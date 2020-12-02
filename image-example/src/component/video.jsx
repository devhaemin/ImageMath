import React, {Component} from 'react';
import {_deleteAccessToken, _getAccessToken} from "../cookie";
import {Link} from "react-router-dom";

export class Video extends Component {
    constructor(props) {
        super(props);
        this.state = {
            videos : [],
            lectureSeq:'',
            token : undefined
        }
    }
    componentDidMount() {
        this.setState({
            token : _getAccessToken()
        })
        this._getVideoList();
    }


    _getVideoList = function (){
        const board_id = this.props.match.params.lectureSeq;

        fetch(`http://api-doc.imagemath.kr:3000/video/lecture/${board_id}`,{
            method:'GET',
            headers: {
                'x-access-token': _getAccessToken()
            }
        })
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else if(response.status ===403) {
                    alert("토큰이 만료되었습니다. 다시 로그인 해주세요")
                    // this._emailLogout()
                    this.props.history.goBack();
                }
            })
            .then(response => {
                this.setState({
                    videos: response
                })
                if (this.state.videos.length===0) {
                    alert('비디오 목록이 없습니다.');
                    this.props.history.goBack();
                }
            })
    }



    render() {
        const list = this.state.videos

        return (
            <div>
                {list ? list.map((video) => {
                    const video_view_url = '/video_view/'+this.props.match.params.lectureSeq+'/' + video.videoSeq
                        return(
                            <div className={'lecture_list'} key={video.videoSeq}>
                                <div className={'green_font'}>{video.videoSeq}</div>
                                <div className={'video_title'}>
                                    <Link className={'link'} to={video_view_url} style={{ textDecoration: 'none' }}>{video.title}</Link>
                                </div>
                                <div className={'video_content'}>
                                    <Link className={'link'} to={video_view_url} style={{ textDecoration: 'none' }}>{video.contents}</Link>
                                </div>

                            </div>
                        )
                    })
                    :null}
            </div>
        );
    }
}

export default Video;