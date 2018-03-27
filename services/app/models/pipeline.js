const mongoose = require('mongoose');

// flame Height Schema
const pipeLineSchema = mongoose.Schema({
    dp: {
        type: Number,
        required: true
    },
    dpMeasure: {
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

    L: {
        type: Number,
        required: true
    },

    LMeasure: {
        type: Number,
        required: true
    },

    Sg: {
        type: Number,
        required: true
    },
    Qm3hr: {
        type: String,
        required: false
    },
    Qcfmr: {
        type: String,
        required: false
    }
});

const PipeLine = module.exports = mongoose.model('PipeLine', pipeLineSchema);

// Pipeline
module.exports.calculate = (pipeLine, callback) => {
    var dp = 0;

    if (pipeLine.dpMeasure == 0) {
        dp = pipeLine.dp * 2.490889083;
    } else if (pipeLine.dpMeasure == 1) {
        dp = pipeLine.dp * 10;
    }
    else if (pipeLine.dpMeasure == 2) {
        dp = pipeLine.dp;
    }

    var d = measureAmountInMiliMeters(pipeLine.DMeasure,pipeLine.D);

    var l = measureAmountInMeters(pipeLine.LMeasure,pipeLine.L);

    var f10 = dp* Math.pow(d,4.8);
    var f11 = Math.pow(pipeLine.Sg,0.8)*l;
    var f12 = Math.pow(f10/f11,0.555); 

    var f13 =  0.00403*f12;
    pipeLine.Qm3hr = parseFloat(f13).toFixed(3) + " m^3/hr"; 

    pipeLine.Qcfmr =  parseFloat(f13 * 0.588125867).toFixed(3) + " cfm"

}

function measureAmountInMiliMeters(measureType, measureValue) {

    if (measureType == 0) { //centi meters 
        measureType = measureValue * 10;
    } else if (measureType == 1) { //feet
        measureType = measureValue * 304.8;
    } else if (measureType == 2) { // inches 
        measureType = measureValue * (25.4);
    } else if (measureType == 3) { // meters
        measureType = measureValue *1000;
    } else {
        measureType = measureValue; // milimeters
    }

    return (parseFloat(measureType)).toFixed(3);
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
