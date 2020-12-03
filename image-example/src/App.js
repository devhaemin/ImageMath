import React, {Component} from 'react';
import { Header } from './component';
import Main from "./component/main";
import {progressBarFetch, setOriginalFetch} from "react-fetch-progressbar";

class App extends Component {
    componentWillMount() {
        // Let react-fetch-progressbar know what the original fetch is.
        setOriginalFetch(window.fetch);

        /*
          Now override the fetch with progressBarFetch, so the ProgressBar
          knows how many requests are currently active.
        */
        window.fetch = progressBarFetch;
    }

    render() {
        return (
            <div>
                <div>
                    <Header />
                </div>
                <div>
                    <Main/>
                </div>
            </div>

        );
    }
}

export default App;