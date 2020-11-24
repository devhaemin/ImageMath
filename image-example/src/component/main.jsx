import React, {Component} from 'react';
import './css/main.css';
import {  Route } from 'react-router-dom';
import { Write } from './write';
import Lecture from "./lecture";
import Video from "./video";
import VideoView from "./video_view";

class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {
            token : undefined
        }
    }

    render() {
        return (
            <div className='Mains'>
                <div id='Mains-left'>
                </div>

                <div>
                    <Route path='/' component={Lecture} exact/>
                    <Route path='/write/:data' component={Write}/>
                    <Route path={'/lecture'} component={Lecture}/>
                    <Route path={'/video/:lectureSeq'} component={Video}/>
                    <Route path={'/video_view/:lectureSeq/:videoSeq'} component={VideoView}/>

                </div>
                <div id='Mains-right'>
                </div>
            </div>
        );
    }
}

export default Main;