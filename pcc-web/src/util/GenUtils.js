class GenUtils {

    formatDate(dtTime){
        let options = {
            year: 'numeric', month: 'numeric', day: 'numeric'
        };

        let dt = '';

        if(dtTime !== null && dtTime !== '') {
            dt = new Intl.DateTimeFormat('en-AU', options).format(new Date(dtTime));
        }

        return dt;
    }
}

export default new GenUtils();