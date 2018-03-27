const mongoose = require('mongoose');

// hrr Schema
const t2FireSchema = mongoose.Schema({
    t1: {
        type: Number,
        required: true
    },
    hrr: {
        type: Number,
        required: true
    },

    time: {
        type: Number,
        required: true
    },

    Output: {
        type: String
    }
});

const T2Fire = module.exports = mongoose.model('T2Fire', t2FireSchema);

// calculate hrr
module.exports.calculate = (t2fire, callback) => {

    var hrr = parseInt(t2fire.hrr);
    var time = parseInt(t2fire.time);

    var alpha = 1000 / Math.pow(parseInt(t2fire.t1), 2);

    console.log(alpha);

    var str = "Time" + "\t" + "HRR" + "\n" + "seconds" + "\t" + "kW" + "\n";

    for (var i = 0; i < 1400; i) {

        var val = alpha * Math.pow(i, 2);
        var result = 0;

        if (val > hrr) {
            result = hrr;
        }
        else {
            result = val;
        }

        str += i + "\t" + result + "\n";

        i = i + time;
    }

    t2fire.Output = str;
}
