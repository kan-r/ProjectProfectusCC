import React, { Component } from 'react';

import PurchaseService from '../services/PurchaseService';
import GenUtils from '../util/GenUtils';


class Purchases extends Component {

    constructor(props) {
        super(props)
        this.state = {
            purchaseList: []
        }
    }

    componentDidMount() {
        this.getPurchaseList();
    }

    getPurchaseList(){
        PurchaseService.getPurchaseList()
        .then(res => {
            this.props.updateMessage('');
            this.setState({ purchaseList: res.data });
        })
        .catch(err => {
            this.props.updateMessage(err, true);
        });;
    }

    formatDate(dtTime){
        return GenUtils.formatDate(dtTime);
    }

    render() {
        return (
            <div>
                <div className="title">
                    Recent Purchases
                </div>
                <table className="report-standard">
                    <thead>
                        <tr >
                            <th>Id</th>
                            <th>Product Code</th>
                            <th>Product Name</th>
                            <th>Category</th>
                            <th>Purchase Price</th>
                            <th>Quantity Purchased</th>
                            <th>Date of Purchase</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.purchaseList.map(purch => (
                            <tr key={purch.purchId}>
                                <td>{purch.purchId}</td>
                                <td>{purch.prodCode}</td>
                                <td>{purch.product.prodName}</td>
                                <td>{purch.product.category.category}</td>
                                <td>{purch.purchPrice}</td>
                                <td>{purch.purchQty}</td>
                                <td>{this.formatDate(purch.purchDate)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default Purchases;