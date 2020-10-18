import React, { Component } from 'react';

import ProfitService from '../services/ProfitService';
import CategoryService from '../services/CategoryService';
import ProductService from '../services/ProductService';


class ProfitForm extends Component {

    constructor(props) {
        super(props)
        this.state = {
            categoryList: [],
            productList: [],
            profitCrit: {
                catId: '',
                prodCodes: [],
                priceFrom: '',
                priceTo: '',
                dateFrom: '',
                dateTo: ''
            }
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeMultiSelect = this.handleChangeMultiSelect.bind(this);
        this.getProfit = this.getProfit.bind(this);
        this.clearForm = this.clearForm.bind(this);
    }

    componentDidMount() {
        this.getCategoryList();
        this.getProductList();
    }

    getCategoryList(){
        CategoryService.getCategoryList()
        .then(res => {
            this.props.updateMessage('');
            this.setState({ categoryList: res.data })
        })
        .catch(err => {
            this.props.updateMessage(err, true);
        });
    }

    getProductList(){
        ProductService.getProductList()
        .then(res => {
            this.props.updateMessage('');
            this.setState({ productList: res.data });
        })
        .catch(err => {
            this.props.updateMessage(err, true);
        });
    }

    handleChange(e) {
        
        let profitCrit = {
            ...this.state.profitCrit,
            [e.target.name] : e.target.value
        };

        this.setState({profitCrit:profitCrit});
    }

    handleChangeMultiSelect(e) {
        
        let values = Array.from(e.target.selectedOptions, option => option.value);

        let profitCrit = {
            ...this.state.profitCrit,
            [e.target.name] : values
        };

        this.setState({profitCrit:profitCrit});
    }

    getProfit(e){
        e.preventDefault();

        ProfitService.getProfit(this.state.profitCrit)
        .then(res => {
            this.props.updateMessage('');

            let profit = res.data;
            let type = profit.type;
            let amount = '$'+ profit.amount.toLocaleString('en-AU');

            let msg = `${type}: ${amount}`;
            
            
            if(type.toLowerCase() === 'profit'){
                this.props.updateMessage2(msg);
            }else{
                this.props.updateMessage2(msg, true);
            }
        })
        .catch(err => {
            this.props.updateMessage(err, true);
        });
    }

    clearForm(e){
        e.preventDefault();

        let profitCrit = {
            catId: '',
            prodCodes: [],
            priceFrom: '',
            priceTo: '',
            dateFrom: '',
            dateTo: ''
        };

        this.setState({profitCrit:profitCrit});

        this.props.updateMessage('');
        this.props.updateMessage2('');
    }

    render() {
        return (
            <form onSubmit={this.getProfit}>
                <table className="form-standard">
                    <thead>
                        <tr>
                            <th>Profit Criteria</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Date Range</td>
                            <td>
                                <input type="date" name="dateFrom" value={this.state.profitCrit.dateFrom} onChange={this.handleChange} />
                            </td>
                            <td>
                                <input type="date" name="dateTo" value={this.state.profitCrit.dateTo} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Price Range</td>
                            <td>
                                <input type="number" min="0.01" step="0.01" name="priceFrom" value={this.state.profitCrit.priceFrom} onChange={this.handleChange} />
                            </td>
                            <td>
                                <input type="number" min="0.01" step="0.01" name="priceTo" value={this.state.profitCrit.priceTo} onChange={this.handleChange} />
                            </td>
                        </tr>
                        <tr>
                            <td>Category</td>
                            <td>
                                <select name="catId" value = {this.state.profitCrit.catId} onChange={this.handleChange}>
                                    <option value=""></option>
                                    {this.state.categoryList.map((cat) => (
                                        <option key={cat.catId} value={cat.catId}>{cat.category}</option>
                                    ))}
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Product</td>
                            <td>
                                <select multiple name="prodCodes" value = {this.state.profitCrit.prodCodes} onChange={this.handleChangeMultiSelect}>
                                    <option value=""></option>
                                    {this.state.productList.map((prod) => (
                                        <option key={prod.prodCode} value={prod.prodCode}>{prod.prodCode} - {prod.prodName}</option>
                                    ))}
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <a onClick={this.clearForm} className="button">Clear</a>
                                <input type="submit" value="Go" className="button" />
                            </td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        );
    }
}

export default ProfitForm;
