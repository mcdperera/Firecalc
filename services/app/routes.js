var Todo = require('./models/todo');

// Models 
var FlashoverModel = require("./models/flashover")
var GasLayerModel = require("./models/gaslayer")
var ConductionModel = require("./models/conduction")
var UserModel = require("./models/user")
var FeedbackModel = require("./models/feedback")

function getTodos(res) {
    Todo.find(function (err, todos) {

        // if there is an error retrieving, send the error. nothing after res.send(err) will execute
        if (err) {
            res.send(err);
        }

        res.json(todos); // return all todos in JSON format
    });
};

// Read the category Json file to fill all the fire calc type categories.
var fs = require("fs"),
    json;

function readJsonFileSync(filepath, encoding) {

    if (typeof (encoding) == 'undefined') {
        encoding = 'utf8';
    }

    var file = fs.readFileSync(filepath, encoding);
    return JSON.parse(file);
}

function getConfig(file) {

    var filepath = __dirname + '/' + file;
    return readJsonFileSync(filepath);
}

// The json file contains all the categories.
json = getConfig('./json/category.json')


module.exports = function (app) {

    // getting all the categories
    app.get('/api/category', function (req, res) {
        res.setHeader("Content-Type", "application/json");
        res.send(json)
    });

    // Get the images by requesting the image
    app.get('/api/images', function (req, res) {
        res.sendfile('./public/images/' + req.query.image);
    });

    // flash over calculation
    app.post('/api/flashover', (req, res) => {
        var flashOver = req.body;
        FlashoverModel.calculate(flashOver, (err, flashOver) => {
            if (err) {
                throw err;
            }
        });

        res.json(flashOver);

    });


    // Gas Layer calculation
    app.post('/api/gaslayer', (req, res) => {
        var gaslayer = req.body;
        GasLayerModel.calculate(gaslayer, (err, gaslayer) => {
            if (err) {
                throw err;
            }
        });

        res.json(gaslayer);

    });



    //  Conduction calculation
    app.post('/api/conduction', (req, res) => {
        var conduction = req.body;
        console.log(conduction);
        ConductionModel.calculate(conduction, (err, conduction) => {

            if (err) {
                throw err;
            }
        });

        res.json(conduction);

    });

    // user sign in
    app.post('/api/signin', (req, res, next) => {
        var newUser = req.body;
        console.log(newUser);
        UserModel.authenticate(newUser.email, newUser.password, function (err, newUser) {
            if (err) {
                throw err;
            }
            console.log(newUser);
            res.json(newUser);
        });
        console.log(newUser);
    });

    // user signup 
    app.post('/api/signup', (req, res, next) => {
        var newUser = req.body;
        console.log(newUser);
        UserModel.addUser(newUser, function (err, newUser) {
            if (err) {
                throw err;
            }
            res.json(newUser);
        });
        console.log(newUser);
    });

    // user feedback
    app.post('/api/feedback', (req, res, next) => {
        var newFeedback = req.body;
        console.log(newFeedback);
        FeedbackModel.addFeedback(newFeedback, function (err, newFeedback) {
            if (err) {
                throw err;
            }
            res.json(newFeedback);
        });
        console.log(newFeedback);
    });


    // api ---------------------------------------------------------------------
    // get all todos
    app.get('/api/todos', function (req, res) {
        // use mongoose to get all todos in the database
        getTodos(res);
    });

    // create todo and send back all todos after creation
    app.post('/api/todos', function (req, res) {

        // create a todo, information comes from AJAX request from Angular
        Todo.create({
            text: req.body.text,
            done: false
        }, function (err, todo) {
            if (err)
                res.send(err);

            // get and return all the todos after you create another
            getTodos(res);
        });

    });

    // delete a todo
    app.delete('/api/todos/:todo_id', function (req, res) {
        Todo.remove({
            _id: req.params.todo_id
        }, function (err, todo) {
            if (err)
                res.send(err);

            getTodos(res);
        });
    });

    //application -------------------------------------------------------------
    app.get('*', function (req, res) {
        res.sendFile(__dirname + '/public/index.html'); // load the single view file (angular will handle the page changes on the front-end)
    });
};


