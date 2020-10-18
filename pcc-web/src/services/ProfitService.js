import axios from 'axios';
import { config } from '../config/config';

class ProfitService {

    getProfit(pC){
        //array to cs string
        let prodCodes = pC.prodCodes.join();
        let url = `/profits?catId=${pC.catId}&prodCodes=${prodCodes}&priceFrom=${pC.priceFrom}&priceTo=${pC.priceTo}&dateFrom=${pC.dateFrom}&dateTo=${pC.dateTo}`;
        return axios.get(config.apiURL + url);
    }

}

export default new ProfitService();