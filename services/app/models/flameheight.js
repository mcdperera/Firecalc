const mongoose = require('mongoose');

// flame Height Schema
const flameHeightSchema = mongoose.Schema({
    Q: {
        type: Number,
        required: true
    },
    QMeasure: {
        type: Number,
        required: true
    },

    D: {
        type: Number,
        required: true
    },
    DMeasure: {
        type: Number,
        required: true
    },

    LFeet: {
        type: Number
    },

    LInches: {
        type: String,
        required: false
    },

    LMeters: {
        type: String,
        required: false
    }
});

const FlameHeight = module.exports = mongoose.model('FlameHeight', flameHeightSchema);

// calculate flame Height
module.exports.calculate = (flameHeight, callback) => {
    var Q = 0;

    if (flameHeight.QMeasure == 1) {
        Q = flameHeight.Q;

    } else {
        Q = flameHeight.Q * 1.055055852
    }

    var d = measureAmountInMeters(flameHeight.DMeasure, flameHeight.D);

    var final = (0.23 * Math.pow(Q, (2 / 5)) - 1.02 * d) / 0.304878049;

    flameHeight.LInches =parseFloat( final * 12 ).toFixed(3)+ " inches"

    flameHeight.LMeters = parseFloat( final * 0.3048).toFixed(3) + " meters"

}

function measureAmountInMeters(measureType, measureValue) {

    if (measureType == 0) { //meters 
        measureType = measureValue / 100;
    } else if (measureType == 1) { //feet
        measureType = measureValue / 3.28;
    } else if (measureType == 2) { // inches 
        measureType = measureValue * (0.0254);
    } else if (measureType == 3) { // meters
        measureType = measureValue;
    } else {
        measureType = measureValue / 1000; // milimeters
    }

    return (parseFloat(measureType)).toFixed(3);
}


function getHeatEffect(fuel) {

    if (fuel = 0) {
        return 16.1;
    } else if (fuel = 1) {
        return 43.7;
    }
    else if (fuel = 2) {
        return 44.6;
    }
    else if (fuel = 3) {
        return 19.8;
    }
    else if (fuel = 4) {
        return 24.9;
    }
    else if (fuel = 5) {
        return 43.3;
    }
    else if (fuel = 6) {
        return 43.3;
    }
    else if (fuel = 7) {
        return 39.8;
    }
    else if (fuel = 8) {
        return 16.4;
    }
    else if (fuel = 9) {
        return 14;
    }
}

function getmaxBurningFlux(fuel) {

    if (fuel = 0) {
        return 14;
    } else if (fuel = 1) {
        return 55;
    }
    else if (fuel = 2) {
        return 70;
    }
    else if (fuel = 3) {
        return 22;
    }
    else if (fuel = 4) {
        return 28;
    }
    else if (fuel = 5) {
        return 26;
    }
    else if (fuel = 6) {
        return 24;
    }
    else if (fuel = 7) {
        return 38;
    }
    else if (fuel = 8) {
        return 16;
    }
    else if (fuel = 9) {
        return 11;
    }
}
