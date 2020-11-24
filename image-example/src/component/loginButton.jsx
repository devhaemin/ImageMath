import React, {Component} from 'react';
import '../App.css';
import Modal from "react-awesome-modal";
import * as Cookie from '../cookie';
import {_deleteAccessToken, _getAccessToken} from "../cookie";


export class LoginButton extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            id: "",
            password: "",
        }
        this._openModal = this._openModal.bind(this);
        this._closeModal = this._closeModal.bind(this);
        this._changeID = this._changeID.bind(this);
        this._changePW = this._changePW.bind(this);
        this._emailLogin = this._emailLogin.bind(this);
        this._emailLogout = this._emailLogout.bind(this);

    }

    componentDidMount() {
        this.setState({
            token : _getAccessToken()
        })
    }

    _openModal = function () {
        this.setState({
            visible : true
        })
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
        })
    }

    _changePW = function () {
        const pw_v = document.getElementsByName('password')[0].value;

        this.setState({
            password: pw_v
        })
    }

    _emailLogin = function () {
        fetch('http://api-doc.imagemath.kr:3000/auth/login', {
            method: 'POST',
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
                    alert("조교만 로그인 할 수 있습니다.")
                }
            })
            .then(response => {
                if (response) {
                    if (response.accessToken && response.userType === 'tutor') {
                        alert('로그인 되었습니다');
                        Cookie._setAccessToken(response.accessToken)
                        this.state.token = response.accessToken
                        this._closeModal()
                        window.location.reload(false);
                    } else {
                        alert('조교만 로그인 할 수 있습니다.')
                    }
                }

            })
    }

    _emailLogout = function () {
        _deleteAccessToken()
        this.setState({
            token : undefined
        })
        alert('로그아웃 되었습니다')
        window.location.reload(false);

    }

    render() {
        return (
                <div>
                    <h3 className={'login_button'} onClick={() =>!(this.state.token) ? this._openModal() : this._emailLogout()}>{ this.state.token ? "로그아웃" : "로그인"  }</h3>
                    <Modal visible={this.state.visible} width="400px" height="300px" effect="fadeInDown"
                           onClickAway={() => this._closeModal()}>
                        <div>
                            <h4 className={'login_tit'}>튜터 로그인</h4>
                            <form>
                                <div className={'login_input'}>
                                    <p>튜터 ID</p>
                                    <input type={'text'} name={'id'} onChange={() => this._changeID()}/>
                                </div>

                                <div className={'login_input'}>
                                    <p>튜터 Password</p>
                                    <input type={'password'} name={'password'} onChange={() => this._changePW()}/>
                                </div>
                                <div className={'login_sub'}>
                                    <div><input type={'button'} className={'modal_btn'} value={'로그인'} onClick={() => this._emailLogin()}/></div>
                                    <div><input type={'button'} className={'modal_btn'} value={'닫기'} onClick={() => this._closeModal()}/></div>
                                </div>
                            </form>
                        </div>
                    </Modal>
                </div>
        );
    }
}
