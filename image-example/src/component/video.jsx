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
            .then(response => response.json())
            .then(response => {
                this.setState({
                    videos: response
                })
                if(this.state.videos.length===0) {
                    alert('영상이 없습니다')
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
                            <div className={'student_list'} key={video.videoSeq}>
                                <div className={'student_seq'}> <Link className={'link'} to={video_view_url} style={{ textDecoration: 'none' }}>{video.videoSeq}</Link></div>
                                <div>
                                    <Link className={'link'} to={video_view_url} style={{ textDecoration: 'none' }}>{video.title}</Link>
                                </div>
                                <div>
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