import React, {Component} from 'react';
import '../App.css';
import { LoginButton } from './loginButton';
import imgLogo from './img/이미지 수학 작은 로고.png';
import { Link } from 'react-router-dom';
import {_getAccessToken} from "../cookie";


class header extends Component {
    constructor(props) {
        super(props);
        this.state = {
            token : undefined
        }
    }

    componentDidMount() {
        this.setState({
            token : _getAccessToken()
        })
    }

    render() {
        return (
            <div className={'header_grid'}>
                <div></div>
                <div className={'logo_img'}>
                    <Link to={'/'}><img src={imgLogo} alt={'logo'}/></Link>
                </div>
                <div className={'header_grid'}>
                    <LoginButton/>
                </div>
            </div>
        );
    }
}

export default header;