// Models 
var FlashoverModel = require("./models/flashover")
var GasLayerModel = require("./models/gaslayer")
var ConductionModel = require("./models/conduction")
var UserModel = require("./models/user")
var FeedbackModel = require("./models/feedback")
var RadiationPoolModel = require("./models/radiationPool")
var HrrModel = require("./models/hrr")
var FlameHeightModel = require("./models/flameheight")
var PipelineModel = require("./models/pipeline")
var GasAmountModel = require("./models/gasamount")
var T2FireModel = require("./models/t2fire")
var GasConc = require("./models/gasconc")

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
        console.log(flashOver);

        FlashoverModel.calculate(flashOver, (err, flashOver) => {
            if (err) {
                throw err;
            }
        });

        console.log(flashOver);
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

    //radiation pool
    app.post('/api/radiationpool', (req, res) => {
        var conduction = req.body;
        console.log(conduction);
        RadiationPoolModel.calculate(conduction, (err, conduction) => {

            if (err) {
                throw err;
            }
        });

        res.json(conduction);

    });

    // hrr calclulation
    app.post('/api/hrr', (req, res) => {
        var hrr = req.body;
        console.log(hrr);
        HrrModel.calculate(hrr, (err, conduction) => {

            if (err) {
                throw err;
            }
        });

        res.json(hrr);

    });

    // flame height calclulation
    app.post('/api/flameheight', (req, res) => {
        var flameheight = req.body;
        console.log(flameheight);
        FlameHeightModel.calculate(flameheight, (err, flameheight) => {

            if (err) {
                throw err;
            }
        });

        res.json(flameheight);

    });

    app.post('/api/pipeline', (req, res) => {
        var pipeline = req.body;
        console.log(pipeline);
        PipelineModel.calculate(pipeline, (err, pipeline) => {

            if (err) {
                throw err;
            }
        });

        res.json(pipeline);

    });

    app.post('/api/gasamount', (req, res) => {
        var gasamout = req.body;
        console.log(gasamout);
        GasAmountModel.calculate(gasamout, (err, gasamout) => {
            if (err) {
                throw err;
            }
        });

        res.json(gasamout);

    });


    // T2 fire model
    app.post('/api/t2fire', (req, res) => {
        var t2fireModel = req.body;
        console.log(t2fireModel);
        T2FireModel.calculate(t2fireModel, (err, t2fireModel) => {
            if (err) {
                throw err;
            }
        });

        res.json(t2fireModel);

    });

    // gas conc model
    app.post('/api/gasconc', (req, res) => {
        var gasconcModel = req.body;      
        GasConc.calculate(gasconcModel, (err, gasconcModel) => {
            if (err) {
                throw err;
            }
        });

        res.json(gasconcModel);

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

    // get all users
    app.post('/api/users', function (req, res) {
        UserModel.find(function (err, todos) {

            // if there is an error retrieving, send the error. nothing after res.send(err) will execute
            if (err) {
                res.send(err);
            }

            res.json(todos); // return all todos in JSON format
        });
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

    // get all users
    app.post('/api/feedbacks', function (req, res) {
        FeedbackModel.find(function (err, todos) {

            // if there is an error retrieving, send the error. nothing after res.send(err) will execute
            if (err) {
                res.send(err);
            }

            res.json(todos); // return all todos in JSON format
        });
    });

    //application -------------------------------------------------------------
    app.get('*', function (req, res) {
        res.sendFile(__dirname + '/public/index.html'); // load the single view file (angular will handle the page changes on the front-end)
    });
};


