import axios from 'axios';
import { config } from '../config/config';

class SaleService {

    getSaleList(){
        return axios.get(config.apiURL + '/sales?limit=3');
    }

    addSale(sale){
        return axios.post(config.apiURL + '/sales', sale);
    }
}

export default new SaleService();