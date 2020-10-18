import React, { Component, createRef } from 'react';

import Purchases from './Purchases';
import PurchaseForm from './PurchaseForm';
import Sales from './Sales';
import SaleForm from './SaleForm';
import Message from './Message';

class Products extends Component {

    constructor(props) {
        super(props)
        this.state = {
            msgObj: {
                msg: '',
                isError: true
            },
            purchasRef: createRef(),
            salesRef: createRef()
        }
        this.updateMessage = this.updateMessage.bind(this);
        this.reloadPurchases = this.reloadPurchases.bind(this);
        this.reloadSales = this.reloadSales.bind(this);
    }

    reloadPurchases(){
        this.state.purchasRef.current.getPurchaseList();
    }

    reloadSales(){
        this.state.salesRef.current.getSaleList();
    }

    updateMessage(msg, isError = false){
        this.setState({msgObj: {msg:msg, isError:isError}});
    }

    render() {
        return (
            <div>
                <Message msgObj={this.state.msgObj} />
                <div className="grid-container-col">
                    <div className="grid-item-col">
                        <div className="grid-container-row">
                            <div className="grid-item-row">
                                <Purchases ref={this.state.purchasRef} updateMessage={this.updateMessage} />
                            </div>
                            <div className="grid-item-row">
                                <PurchaseForm updateMessage={this.updateMessage} reloadPurchases={this.reloadPurchases} />
                            </div>
                        </div>
                    </div>
                    <div className="grid-item-col">
                        <div className="grid-container-row">
                            <div className="grid-item-row">
                                <Sales ref={this.state.salesRef} updateMessage={this.updateMessage} />
                            </div>
                            <div className="grid-item-row">
                                <SaleForm updateMessage={this.updateMessage} reloadSales={this.reloadSales} />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Products;