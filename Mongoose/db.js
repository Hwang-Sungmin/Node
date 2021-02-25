const mongoose = require('mongoose')
module.exports = () => {
    function connect() {
        mongoose.connect('localhost:27017', function(err){
            if(err){
                console.log('mongodb connection error', err);
            }
            console.log('mongodb connected');
        });
    }
    connect();
    mongoose.connection.on('disconnected', connect);
    require('./user.js')
}