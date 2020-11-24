import React, {Component} from 'react';
import {_getAccessToken} from "../cookie";
import {Link} from "react-router-dom";

class Video extends Component {
    constructor(props) {
        super(props);
        this.state = {
            videos : [],
            lectureSeq:''
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
                } else {
                    alert("조교만 로그인 할 수 있습니다. 다시 로그인 해주세요")
                }
            })
            .then(response => {
                this.setState({
                    videos: response
                })
                if (response.length===0) {
                    alert('비디오 목록이 없습니다.');
                    this.props.history.goBack()
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