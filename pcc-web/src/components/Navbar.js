import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class Navbar extends Component {
    
    render() {
        return (
            <div className="topnav">
                <ul>
                    <li><Link to="/products">Products</Link></li>
                    <li><Link to="/profits">Profits</Link></li>
                    <li>
                        <a className="disabled"><i>Profectus CC</i></a>
                    </li>
                </ul>
            </div>
        );
    }
}

export default Navbar;