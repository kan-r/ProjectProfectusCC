import React, { Component } from 'react';
import SaleService from '../services/SaleService';
import ProductService from '../services/ProductService';


class SaleForm extends Component {

    constructor(props) {
        super(props)
        this.state = {
            sale: {
                prodCode: '',
                prodName: '',
                category: '',
                salesPrice: '',
                salesQty: '',
                salesDate: '',
            }
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleBlur = this.handleBlur.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleClear = this.handleClear.bind(this);
    }

    handleChange(e) {
        
        let sale = {
            ...this.state.sale,
            [e.target.name] : e.target.value
        };

        this.setState({sale:sale});
    }

    handleBlur(e){
        
        ProductService.getProduct(this.state.sale.prodCode)
        .then(res => {
            this.props.updateMessage('');

            let product = res.data;
            if(!product){
                return;
            }

            let sale = {
                ...this.state.sale,
                prodName : product.prodName,
                category : product.category.category
            };
    
            this.setState({sale:sale});
        })
        .catch(err => {
            this.props.updateMessage(err, true);
        });
    }

    handleSubmit(e){
        e.preventDefault();

        SaleService.addSale(this.state.sale)
        .then(res => {
            this.props.updateMessage('');
            this.props.reloadSales();
        })
        .catch(err => {
            this.props.updateMessage(err, true);
        });
    }

    handleClear(e){
        e.preventDefault();

        let sale = {
            prodCode: '',
            prodName: '',
            category: '',
            salesPrice: '',
            salesQty: '',
            salesDate: '',
        };

        this.setState({sale:sale});

        this.props.updateMessage('');
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <table className="form-standard">
                    <thead>
                        <tr>
                            <th>Products Sold</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Product Code</td>
                            <td>
                                <input name="prodCode" value={this.state.sale.prodCode} onChange={this.handleChange} onBlur={this.handleBlur} />
                            </td>
                        </tr>
                        <tr>
                            <td>Product Name</td>
                            <td>
                                <input name="prodName" value={this.state.sale.prodName} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Category</td>
                            <td>
                                <input name="category" value={this.state.sale.category} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Sale Price</td>
                            <td>
                                <input type="number" min="0.01" step="0.01" name="salesPrice" value={this.state.sale.salesPrice} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Quantity Sold</td>
                            <td>
                                <input type="number" min="1" step="1" name="salesQty" value={this.state.sale.salesQty} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Date of Sale</td>
                            <td>
                                <input type="date" name="salesDate" value={this.state.sale.salesDate} onChange={this.handleChange} />
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

export default SaleForm;
