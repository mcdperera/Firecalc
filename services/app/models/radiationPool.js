const mongoose = require('mongoose');

// radiation pool Schema
const radiationPoolSchema = mongoose.Schema({
    Diameter: {
        type: Number,
        required: true
    },
    Distance: {
        type: Number,
        required: true
    },

    Length: {
        type: Number,
        required: true
    },
    Width: {
        type: Number,
        required: true
    },

    DiameterMeasure: {
        type: Number,
        required: true
    },

    DistanceMeasure: {
        type: Number,
        required: false
    },

    LengthMeasure: {
        type: Number,
        required: true
    },

    WidthMeasure: {
        type: Number,
        required: true
    },

    EquivalentDiameter: {
        type: String
    },

    EquivalentDlameter: {
        type: String
    },

    Heatflux: {
        type: String
    },

    HeatfluxBtu: {
        type: String
    }
});

const RadiationPool = module.exports = mongoose.model('RadiationPool', radiationPoolSchema);

// calculate radiationPool
module.exports.calculate = (radiationPool, callback) => {

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


