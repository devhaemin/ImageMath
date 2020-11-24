import React, {Component} from 'react';
import {Route, Link, Switch} from 'react-router-dom';
import '../App.css';

import Modal from 'react-awesome-modal';

class header extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            id: "",
            password: "",
            isLogin: 'login',
        }
    }

    componentDidMount() {
        this._startLogin()
    }

    _openModal = function () {
        this.setState({
            visible: true
        });
    }

    _closeModal = function () {
        this.setState({
            visible: false
        })
    }

    _changeID = function () {
        const id_v = document.getElementsByName('id')[0].value;

        this.setState({
            id: id_v
        });
    }

    _changePW = function () {
        const pw_v = document.getElementsByName('password')[0].value;

        this.setState({
            password: pw_v
        });
    }

    _setCookie = function (name, value, exp) {
        var date = new Date();
        date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
        document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
    }

    _getCookie = function (name) {
        var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
        return value ? value[2] : null;
    }

    _deleteCookie = function (name) {
        document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
    }

    _emailLogin = function () {
        fetch("http://api-doc.imagemath.kr:3000/auth/login", {
            method: "POST",
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                email: this.state.id,
                password: this.state.password
            })
        })
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    alert("조교만 로그인할 수 있습니다.");
                }
            })
            .then(response => {

                if (response) {
                    if (response.accessToken && response.userType === "tutor") {
                        this._setCookie("accessToken", response.accessToken, 1);
                        console.log(response.accessToken);
                        alert("로그인 되었습니다.")
                        this._closeModal();
                        this.setState({isLogin: 'logout'})
                    } else {
                        alert("조교만 로그인할 수 있습니다.");
                    }
                }
            });
    }

    _logOut = function () {
        this._deleteCookie();
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
                    this._deleteCookie('accessToken');
                    alert('로그아웃 되었습니다');
                    this.setState({isLogin: 'login'})
                    console.log(this.response)

                } else {
                    this._openModal()
                }

            })
    }

    _startLogin = function () {
        fetch('http://api-doc.imagemath.kr:3000/auth/autologin', {
            method: 'GET',
            headers: {
                "x-access-token": this._getCookie('accessToken')
            }
        })

            .then(response => response.json())
            .then(response => {
                if (this._getCookie('accessToken') === response.accessToken) {
                    this.setState({isLogin: 'logout'})
                }
            })
    }


    render() {


        return (
            <div class='header_grid'>
                <div className='acenter'>
                    {this.state.isLogin === 'logout'
                        ? <h5><Link to='/write'>포스트 작성</Link></h5>
                        : <h5 onClick={() => {
                            alert('로그인 해주세요')
                        }}>포스트 작성</h5>
                    }

                </div>
                <div className='acenter'>
                    <Route path='/'/>
                    <Link className='link_tit' to='/'><h3>Image Math </h3></Link>
                </div>

                <div className='acenter'>
                    <h5 onClick={() => this._isLogin()}>{this.state.isLogin}</h5>
                    <Modal visible={this.state.visible}
                           width="400"
                           height="350"
                           effect="fadeInDown"
                           onClickAway={() => this._closeModal()}>
                        <div>
                            <h4 className='acenter login_tit'>관리자 로그인</h4>
                            <form>
                                <div className='login_div'>
                                    <div className='login_input_div'>
                                        <p>관리자 ID</p>
                                        <input type='text' name='id' onChange={() => this._changeID()}/>
                                    </div>
                                    <div className='login_input_div' style={{'marginTop': '40px'}}>
                                        <p>관리자 Password</p>
                                        <input type='password' name='password' onChange={() => this._changePW()}/>
                                    </div>

                                    <div className='submit_div'>
                                        <div><input type='button' value='로그인' onClick={() => this._emailLogin()}/></div>
                                        <div><input type='button' value='취소' onClick={() => this._closeModal()}/></div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </Modal>
                </div>
            </div>
        );
    }
}

export default header;

