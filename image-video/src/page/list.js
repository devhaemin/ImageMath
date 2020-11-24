import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import header from '../inc/header';
import './main.css';
import Moment from 'react-moment';

Moment.globalFormat = 'YYYY - MM - DD HH시 mm분 ss초';

class list extends Component {
    constructor(props) {
        super(props)
        this.state = {
            data: [],
            isLogin: 'logout'
        }
    }

    _getCookie = function (name) {
        var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
        return value ? value[2] : null;
    }


    componentDidMount() {
        this._getListData()
    }

    _getListData = function () {
        fetch('http://api-doc.imagemath.kr:3000/video', {
            headers: {
                "x-access-token": this._getCookie('accessToken')
            }
        })
            .then(response => response.json())
            .then(response => {
                console.log(response);
                return response;
            })
            .then(response => {
                this.setState({data: response})

            });
    }

    _isLogin = function () {
        fetch('http://api-doc.imagemath.kr:3000/auth/autologin', {
            method: 'GET',
            headers: {
                "x-access-token": this._getCookie('accessToken')
            }
        })

            .then(response => response.json())
            .then(response => {
                if (this._getCookie("accessToken") === response.accessToken) {
                    this.setState({isLogin: 'login'})
                } else {
                    alert('로그인 해주세요');
                }

            })

    }


    render() {
        const list = this.state.data

        return (
            <div className='List'>

                <div className='list_grid list_tit'>
                    <div> 제목</div>
                    <div className='acenter'> 날짜</div>

                </div>

                {list ? list.map((el, key) => {
                        const view_url = '/view/' + el.key;

                        return (
                            <div className='list_grid list_data' key={key}>
                                {this.state.isLogin === 'logout'
                                    ? <div><Link to={view_url}> {el.title} </Link></div>
                                    : <div onClick={() => {
                                        alert('로그인 해주세요')
                                    }}>{el.title}</div>
                                }
                                <div><Moment unix>{el.uploadTime / 1000}</Moment></div>
                                {/* <div className='acenter'> {el.uploadTime.slice(0, 10)} </div> */}
                            </div>
                        )
                    })
                    : null}
            </div>
        );

    }
}

export default list;