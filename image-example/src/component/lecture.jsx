import React, {Component} from 'react';
import {_deleteAccessToken, _getAccessToken} from "../cookie";
import {Link} from "react-router-dom";

export class Lecture extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lecture:[],
            token : undefined
        }

    }

    componentDidMount() {
        this.setState({
            token : _getAccessToken()
        })
        this._getLecture()

    }

    _getLecture = function (){
        fetch('https://api-doc.imagemath.kr:3001/lecture')
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else if(response.status ==403) {
                    alert("토큰이 만료되었습니다. 다시 로그인 해주세요")
                    this._emailLogout()
                    this.props.history.push('/')
                }
            })
            .then(response => {
                this.setState({
                    lecture: response,
                })
            })
    }

    _emailLogout()  {
        _deleteAccessToken()
        this.setState({
            token : undefined
        })
        alert('로그아웃 되었습니다')
    }


    render() {
        const list = this.state.lecture

        return (
            <div>
                {list ? list.map((lec) => {
                    const video_url = '/video/' + lec.lectureSeq;
                    const write_url = '/write/' + lec.lectureSeq;
                    return(
                        <div className={'lecture_list'} key={lec.lectureSeq}>
                            <div className={'green_font'}>{lec.lectureSeq}</div>
                            <div className={'list_content'}>
                                {this.state.token
                                    ? <div><Link to={video_url} className={'link'} style={{ textDecoration: 'none' }}>{lec.name}</Link></div>
                                    : <div className={'link'} onClick={()=> alert('로그인 해주세요')}>{lec.name}</div>
                                }
                                {this.state.token
                                    ? <div className={'green_font'}><Link to={write_url} className={'link'} style={{ textDecoration: 'none' }}>포스트 작성</Link></div>
                                    : <div className={'green_font'} className={'link'} onClick={()=>alert('로그인 해주세요.')}>비디오작성</div>
                                }
                            </div>
                        </div>
                    )
                    })

                : null}
            </div>
        )
    }
}

export default Lecture;