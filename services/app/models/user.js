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

    //User.find({ username: username , password :password  }, callback);

    User.findOne({ email: username , password :password  }, callback);

    // if (foundUser != null) {
    //     foundUser.authenticated = true;
    // }
    // else {
    //     foundUser = new User();
    //     foundUser.authenticated = fasle;
    // }

    // return callback(foundUser);

    // User.findOne({ username: username })
    //     .exec(function (err, user) {
    //         if (err) {
    //             return callback(err)
    //         } else if (!user) {
    //             var err = new Error('User not found.');
    //             err.status = 401;
    //             return callback();
    //         }
    //         if (password == user.password) {
    //             user.authenticated = true;
    //             return callback(user);
    //         } else { return callback(); }

    //     });
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