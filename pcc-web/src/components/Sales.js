import React, { Component } from 'react';

import SaleService from '../services/SaleService';
import GenUtils from '../util/GenUtils';


class Sales extends Component {

    constructor(props) {
        super(props)
        this.state = {
            saleList: []
        }
    }

    componentDidMount() {
        this.getSaleList();
    }

    getSaleList(){
        SaleService.getSaleList()
        .then(res => {
            this.props.updateMessage('');
            this.setState({ saleList: res.data });
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
                    Recent Sales
                </div>
                <table className="report-standard">
                    <thead>
                        <tr >
                            <th>Id</th>
                            <th>Product Code</th>
                            <th>Product Name</th>
                            <th>Category</th>
                            <th>Sale Price</th>
                            <th>Quantity Sold</th>
                            <th>Date of Sold</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.saleList.map(sale => (
                            <tr key={sale.salesId}>
                                <td>{sale.salesId}</td>
                                <td>{sale.prodCode}</td>
                                <td>{sale.product.prodName}</td>
                                <td>{sale.product.category.category}</td>
                                <td>{sale.salesPrice}</td>
                                <td>{sale.salesQty}</td>
                                <td>{this.formatDate(sale.salesDate)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default Sales;