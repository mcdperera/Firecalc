const mongoose = require('mongoose');

// gas amount Schema
const gasAmountSchema = mongoose.Schema({
    type: {
        type: Number,
        required: true
    },
    Area: {
        type: Number,
        required: true
    },

    AreaMeasure: {
        type: Number,
        required: true
    },
    Height: {
        type: Number,
        required: true
    },

    HeightMeasure: {
        type: Number,
        required: true
    },

    VolumeOfGasLEL: {
        type: Number,
        required: false
    },

    VolumeOfGasStoich: {
        type: Number,
        required: false
    },

    VolumeOfGasUEL: {
        type: Number,
        required: false
    },


    WeightOfGasLEL: {
        type: Number,
        required: false
    },

    WeightOfGasStoich: {
        type: Number,
        required: false
    },

    WeightOfGasUEL: {
        type: Number,
        required: false
    },


    VolumeOfLiquidLEL: {
        type: Number,
        required: false
    },

    VolumeOfLiquidStoich: {
        type: Number,
        required: false
    },

    VolumeOfLiquidUEL: {
        type: Number,
        required: false
    },
});

const gasAmount = module.exports = mongoose.model('gasAmount', gasAmountSchema);

// Gas amount
module.exports.calculate = (gasAmount, callback) => {
    var area = getMeasureInM2(gasAmount.AreaMeasure, gasAmount.Area);
    var height = measureAmountInMeters(gasAmount.HeightMeasure, gasAmount.Height);
    var volume = height * area;

    var lel = getLel(gasAmount.type) / 100;
    var stochio = getStoichiometer(gasAmount.type) / 100;
    var uel = getUel(gasAmount.type) / 100;
    var vaporDensi = getVaporDensity(gasAmount.type);
    var liquidDensi = getLiquidDensity(gasAmount.type);

    var volumeofGasLEL = volume * lel;
    var volumeOfGasStoich = volume * stochio;
    var volumeOfGasUEL = volume * uel;

    gasAmount.VolumeOfGasLEL = parseFloat(volumeofGasLEL).toFixed(3) + " m^3";
    gasAmount.VolumeOfGasStoich = parseFloat(volumeOfGasStoich).toFixed(3) + " m^3";
    gasAmount.VolumeOfGasUEL = parseFloat(volumeOfGasUEL).toFixed(3) + " m^3";

    var weightOfGasLEL = volumeofGasLEL * vaporDensi;
    var weightOfGasStoich = volumeOfGasStoich * vaporDensi;
    var weightOfGasUEL = volumeOfGasUEL * vaporDensi;

    gasAmount.WeightOfGasLEL = parseFloat(weightOfGasLEL).toFixed(3) + " kg";
    gasAmount.WeightOfGasStoich = parseFloat(weightOfGasStoich).toFixed(3) + " kg";
    gasAmount.WeightOfGasUEL = parseFloat(weightOfGasUEL).toFixed(3) + " kg";


    gasAmount.VolumeOfLiquidLEL =  ((liquidDensi == 0) ? 'N/A' :  parseFloat(weightOfGasLEL / liquidDensi).toFixed(3) + " m^3");
    gasAmount.VolumeOfLiquidStoich = ((liquidDensi == 0) ? 'N/A' :  parseFloat(weightOfGasStoich / liquidDensi).toFixed(3) + " m^3");
    gasAmount.VolumeOfLiquidUEL =  ((liquidDensi == 0) ? 'N/A' :  parseFloat(weightOfGasUEL / liquidDensi).toFixed(3) + " m^3"); 

}

function getMeasureInM2(type, area) {
    if (type == 0) { // Methane 
        return area * 0.092950625;
    } else if (type == 1) { // Propane
        return area * 0.00064549;
    } else if (type == 2) { // user specified
        return area;
    }
}


function measureAmountInMiliMeters(measureType, measureValue) {

    if (measureType == 0) { //centi meters 
        measureType = measureValue * 10;
    } else if (measureType == 1) { //feet
        measureType = measureValue * 304.8;
    } else if (measureType == 2) { // inches 
        measureType = measureValue * (25.4);
    } else if (measureType == 3) { // meters
        measureType = measureValue * 1000;
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

function getLel(type) {
    if (type == 0) { // Methane 
        return 5;
    } else if (type == 1) { // Propane
        return 2.10
    } else if (type == 2) { // user specified
        return 2.00
    }
}

function getStoichiometer(type) {
    if (type == 0) { // Methane 
        return 9.52;
    } else if (type == 1) { // Propane
        return 6.26;
    } else if (type == 2) { // user specified
        return 4.5;
    }
}

function getUel(type) {
    if (type == 0) { // Methane 
        return 15;
    } else if (type == 1) { // Propane
        return 9.5;
    } else if (type == 2) { // user specified
        return 9;
    }
}

function getVaporDensity(type) {
    if (type == 0) { // Methane 
        return 0.6407;
    } else if (type == 1) { // Propane
        return 1.554;
    } else if (type == 2) { // user specified
        return 1.8;
    }
}

function getLiquidDensity(type) {
    if (type == 0) { // Methane 
        return "";
    } else if (type == 1) { // Propane
        return 580;
    } else if (type == 2) { // user specified
        return 540;
    }
}

