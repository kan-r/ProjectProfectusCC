import React, { Component } from 'react';
import PurchaseService from '../services/PurchaseService';
import ProductService from '../services/ProductService';


class PurchaseForm extends Component {

    constructor(props) {
        super(props)
        this.state = {
            purchase: {
                prodCode: '',
                prodName: '',
                category: '',
                purchPrice: '',
                purchQty: '',
                purchDate: '',
            }
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleBlur = this.handleBlur.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleClear = this.handleClear.bind(this);
    }

    handleChange(e) {
        
        let purchase = {
            ...this.state.purchase,
            [e.target.name] : e.target.value
        };

        this.setState({purchase:purchase});
    }

    handleBlur(e){
        
        ProductService.getProduct(this.state.purchase.prodCode)
        .then(res => {
            this.props.updateMessage('');

            let product = res.data;
            if(!product){
                return;
            }

            let purchase = {
                ...this.state.purchase,
                prodName : product.prodName,
                category : product.category.category,
                purchPrice : product.purchPrice
            };
    
            this.setState({purchase:purchase});
        })
        .catch(err => {
            this.props.updateMessage(err, true);
        });
    }

    handleSubmit(e){
        e.preventDefault();

        PurchaseService.addPurchase(this.state.purchase)
        .then(res => {
            this.props.updateMessage('');
            this.props.reloadPurchases();
        })
        .catch(err => {
            this.props.updateMessage(err, true);
        });
    }

    handleClear(e){
        e.preventDefault();

        let purchase = {
            prodCode: '',
            prodName: '',
            category: '',
            purchPrice: '',
            purchQty: '',
            purchDate: '',
        };

        this.setState({purchase:purchase});

        this.props.updateMessage('');
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <table className="form-standard">
                    <thead>
                        <tr>
                            <th>Products Purchased</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Product Code</td>
                            <td>
                                <input name="prodCode" value={this.state.purchase.prodCode} onChange={this.handleChange} onBlur={this.handleBlur} />
                            </td>
                        </tr>
                        <tr>
                            <td>Product Name</td>
                            <td>
                                <input name="prodName" value={this.state.purchase.prodName} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Category</td>
                            <td>
                                <input name="category" value={this.state.purchase.category} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Purchase Price</td>
                            <td>
                                <input type="number" min="0.01" step="0.01" name="purchPrice" value={this.state.purchase.purchPrice} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Quantity Purchased</td>
                            <td>
                                <input type="number" min="1" step="1" name="purchQty" value={this.state.purchase.purchQty} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Date of Purchase</td>
                            <td>
                                <input type="date" name="purchDate" value={this.state.purchase.purchDate} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <a onClick={this.handleClear} className="button">Clear</a>
                                <input type="submit" value="Save" className="button" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        );
    }
}

export default PurchaseForm;
