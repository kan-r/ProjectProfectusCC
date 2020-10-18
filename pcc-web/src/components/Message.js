import React, { Component } from 'react';

class Message extends Component {
    
    renderMsg(){
        let msg = this.props.msgObj.msg;
       
        if(msg === null || msg === ''){
            return '';
        }

        if(typeof(msg) === 'object'){
            if(msg.response !== undefined){
                msg = msg.response.data;
                if(typeof(msg) === 'object'){
                    if(msg.error !== undefined){
                        msg = msg.error;
                        if(msg === 'invalid_token'){
                            msg = 'Session expired';
                            this.logout();  
                        }else if(msg === 'invalid_grant'){
                            msg = 'Bad credentials';    
                        }
                    }
                }
            }else {
                msg = msg.message;
            }
        }

        if(msg === null || msg === ''){
            return '';
        }

        if(this.props.msgObj.isError){
            return(<div className="message message-info">{msg}</div>);
        }else{
            return(<div className="message message-error">{msg}</div>);
        }
    }

    render() {
        return (
            <div>
                {this.renderMsg()}
            </div>
        );
    }
}

export default Message;