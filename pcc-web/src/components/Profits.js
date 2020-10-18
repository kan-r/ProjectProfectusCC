import React, { Component } from 'react';

import ProfitForm from './ProfitForm';
import Message from './Message';
import Message2 from './Message2';


class Profits extends Component {

    constructor(props) {
        super(props)
        this.state = {
            profits: {
                type: '',
                amount: '',
                params: {}
            },
            msgObj: {
                msg: '',
                isError: true
            },
            msgObj2: {
                msg: '',
                isError: true
            }
        }
        this.updateMessage = this.updateMessage.bind(this);
        this.updateMessage2 = this.updateMessage2.bind(this);
    }

    updateMessage(msg, isError = false){
        this.setState({msgObj: {msg:msg, isError:isError}});
    }

    updateMessage2(msg, isError = false){
        this.setState({msgObj2: {msg:msg, isError:isError}});
    }

    render() {
        return (
            <div>
                <Message msgObj={this.state.msgObj} />
                <div className="grid-container-col-2">
                    <div className="grid-item-col-2">
                        <ProfitForm updateMessage={this.updateMessage} updateMessage2={this.updateMessage2} />
                    </div>
                    <div className="grid-item-col-2">
                        <Message2 msgObj={this.state.msgObj2} />
                    </div>
                </div>
            </div>
        );
    }
}

export default Profits;