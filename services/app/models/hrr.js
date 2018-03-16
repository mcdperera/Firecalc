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
        required: true
    },

    Q: {
        type: String,
        required: true
    },

    AreaM2: {
        type: String
    },

    AreaF2: {
        type: String
    }
});

const Hrr = module.exports = mongoose.model('Hrr', hrrSchema);

// calculate radiationPool
module.exports.calculate = (hrr, callback) => {


    var heatEffect = getHeatEffect(hrr.Fuel);

    hrr.m = heatEffect + " g/m^2-sec";

    var dhc = he
    var length = measureAmountInMeters(radiationPool.LengthMeasure, radiationPool.Length);
    var width = measureAmountInMeters(radiationPool.WidthMeasure, radiationPool.Width);

    var dlameter = parseFloat(Math.sqrt(4 * length * width / 3.141592654)).toFixed(3);

    radiationPool.EquivalentDiameter = dlameter + " m";
    radiationPool.EquivalentDlameter = dlameter / measureAmountInMeters(1, 1) + " m";

    var D = measureAmountInMeters(radiationPool.DiameterMeasure, radiationPool.Diameter);
    var L = measureAmountInMeters(radiationPool.DistanceMeasure, radiationPool.Distance);

    var LoverD = L / D;

    var heatFlux = 15.4 * (Math.pow(LoverD, -1.59));
    var heatFluxBut = heatFlux * 0.088055092;

    radiationPool.Heatflux = heatFlux + " kW/m^2";
    radiationPool.HeatfluxBtu = heatFluxBut + " butSec";

    radiationPool.Vaidity = checkValidity(LoverD);

}

function checkValidity(LoverD) {

    if (LoverD < 0.7) {
        return "Too close to pool edge";
    } else if (LoverD > 15) {
        return "Too far from poo edge";
    } else {
        return "OK";
    }

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
