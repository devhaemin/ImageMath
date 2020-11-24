import React, {Component} from 'react';
import {_getAccessToken} from "../cookie";
import {Link} from "react-router-dom";

export class Lecture extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lecture:[],
        }

    }

    componentDidMount() {
        this.setState({
            token : _getAccessToken()
        })
        this._getLecture()

    }

    _getLecture = function (){
        fetch('http://api-doc.imagemath.kr:3000/lecture')
            .then(response => response.json())
            .then(response => {
                this.setState({
                    lecture: response,
                })
            })
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
                                    : <div className={'green_font'} className={'link'} onClick={()=>alert('로그인 해주세요.')}>포스트 작성</div>
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