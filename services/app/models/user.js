const mongoose = require('mongoose');

var userSchema = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    isAdmin: {
        type: Boolean,
        required: true
    },
    password: {
        type: String,
        required: true
    }
    ,
    authenticated: {
        type: Boolean,
        required: false
    }

});

var User = mongoose.model('User', userSchema);

User.authenticate = function (username, password, callback) {

    User.findOne({ email: username , password :password  }, callback);
}

// Functions for interacting with user
User.getUsers = function (callback, limit) {
    User.find({}, callback).limit(limit);
}

User.getUser = function (username , password, callback) {
    User.find({ username: username }, callback);
}

User.addUser = function (user, callback) {
    User.create(user, callback);
}

User.deleteUser = function (id, callback) {
    User.deleteOne({ _id: id }, callback);
}

module.exports = User;