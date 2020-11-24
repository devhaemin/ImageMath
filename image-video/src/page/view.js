import React, {Component} from 'react';
import './main.css';

class view extends Component {
    constructor(props) {
        super(props)
        this.state = {}
    }

    _getCookie = function (name) {
        var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
        return value ? value[2] : null;
    }

    componentDidMount() {
        this._getData()
    }

    _getData = function () {
        const key = this.props.match.params.data;
        fetch('http://api-doc.imagemath.kr:3000/file/video/attachedVideo?videoSeq=' + key, {
            method: 'GET',
            headers: {
                "x-access-token": this._getCookie("accessToken")
            }
        })
            .then(response => response.json())
            .then(response => {
                this.setState({data: response});
            })
    }

    _removeView = function () {
        const key = this.props.match.params.data;

        if (window.confirm('해당 게시물을 삭제하시겠습니까?\n삭제된 데이터는 복구할 수 없습니다.')) {
            fetch(`http://api-doc.imagemath.kr:3000/video/${encodeURIComponent(key)}`, {
                method: 'DELETE',
                headers: {
                    "x-access-token": this._getCookie("accessToken")
                }
            }).then(r => {
                console.log(r);
            })
        }
    }

    render() {

        const {data} = this.state;


        return (
            <div className='Write'>
                {data
                    ? <div>
                        <div className='write_option_div'>
                            <input type='button' value='삭제' onClick={() => this._removeView()}/>
                        </div>


                        <div className='top_title'>
                            <input type='text' id='title_text' name='title' defaultValue={data.title} readOnly/>
                            {data.title}
                        </div>

                        <div>
                            <textarea id='content_txt' name='contents' defaultValue={data.contents} readOnly></textarea>
                        </div>
                    </div>
                    : null}
            </div>
        );
    }
}

export default view;

