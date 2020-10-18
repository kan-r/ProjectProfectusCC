import axios from 'axios';
import { config } from '../config/config';

class CategoryService {

    getCategoryList(){
        return axios.get(config.apiURL + '/categories');
    }

}

export default new CategoryService();