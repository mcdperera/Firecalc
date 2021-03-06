const mongoose = require('mongoose');

// hrr Schema
const hrrSchema = mongoose.Schema({
    Fuel: {
        type: Number,
        required: true
    },
    A: {
        type: Number,
        required: true
    },

    AMeasure: {
        type: Number,
        required: true
    },
    Radius: {
        type: Number,
        required: true
    },

    RadiusMeasure: {
        type: Number,
        required: true
    },

    m: {
        type: String,
        required: false
    },

    dHc: {
        type: String,
        required: false
    },

    QButSec: {
        type: String,
        required: false
    },

    Q: {
        type: String,
        required: false
    },

    AreaM2: {
        type: String
    },

    AreaF2: {
        type: String
    }
});

const Hrr = module.exports = mongoose.model('Hrr', hrrSchema);

// calculate hrr
module.exports.calculate = (hrr, callback) => {

    var fuel = 1;

    var heatEffect = getmaxBurningFlux(fuel);

    console.log(heatEffect);

    hrr.m = heatEffect + " g/m^2-sec";

    var dHC = getHeatEffect(fuel);
    hrr.dHc = dHC +" kJ/g";

    var A =  measureAmountInMeters2(0,hrr.A);

    console.log(A); 

    var Q = heatEffect * dHC * A;

    hrr.Q = Q + " kW/m^2";
    hrr.QButSec  = Q * 1.055055852 + " butSec";


    var area = 2 * Math.PI * hrr.Radius; 

    hrr.AreaM2 = parseFloat(area).toFixed(3) + " m^2";

    hrr.AreaF2 = parseFloat(area *  10.7639).toFixed(3)  + " feet^2";

}

function measureAmountInMeters2(measureType, measureValue) {

    if (measureType == 1) { //meters 
        measureValue = measureValue * 0.062427818;
    } 

    return (parseFloat(measureValue)).toFixed(3);
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
