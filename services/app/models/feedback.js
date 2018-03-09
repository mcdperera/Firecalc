const mongoose = require('mongoose');

var feedbackSchema = mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    userid: {
        type: String,
        required: true
    }
});

var Feedback = mongoose.model('Feedback', feedbackSchema);

Feedback.addFeedback = function (feedback, callback) {
    Feedback.create(feedback, callback);
}

module.exports = Feedback;