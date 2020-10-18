import axios from 'axios';
import { config } from '../config/config';

class PurchaseService {

    getPurchaseList(){
        return axios.get(config.apiURL + '/purchases?limit=3');
    }

    addPurchase(purch){
        return axios.post(config.apiURL + '/purchases', purch);
    }
}

export default new PurchaseService();