import React, {Component} from 'react';
import { Header } from './component';
import Main from "./component/main";

class App extends Component {
    
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