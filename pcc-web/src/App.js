import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Navbar from './components/Navbar';
import Products from './components/Products';
import Profits from './components/Profits';

function App() {
  return (
    <Router basename={`${process.env.PUBLIC_URL}`}>
      <div className="container">
        <Navbar />
        <div>
          <Route path="/" exact component={Products} />
          <Route path="/products" exact component={Products} />
          <Route path="/profits" exact component={Profits} />
        </div>
      </div>
    </Router>
  );
}

export default App;
