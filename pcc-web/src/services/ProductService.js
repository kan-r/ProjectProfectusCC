import axios from 'axios';
import { config } from '../config/config';

class ProductService {

    getProductList(){
        return axios.get(config.apiURL + '/products');
    }

    getProduct(prodCode){
        return axios.get(config.apiURL + `/products/${prodCode}`);
    }

}

export default new ProductService();