const mongoose = require('mongoose');

// hrr Schema
const gasconcSchema = mongoose.Schema({
    airchanges: {
        type: Number,
        required: true
    },
    qg: {
        type: Number,
        required: true
    },

    qgMeasure: {
        type: Number,
        required: true
    },


    v: {
        type: Number,
        required: true
    },

    vMeasure: {
        type: Number,
        required: true
    },

    timestep: {
        type: Number,
        required: true
    },

    Output: {
        type: String
    }
});

const GasConc = module.exports = mongoose.model('GasConc', gasconcSchema);

// calculate hrr
module.exports.calculate = (gasconc, callback) => {

    var air = parseFloat(gasconc.airchanges);

    var time = parseFloat(gasconc.timestep);

    var v = parseFloat(gasconc.v);

    //v = 28.32;
    var Qa = v* air;

    var Qg = parseFloat(gasconc.qg);//1.700316304;
    
    var str = "Time" + "\t" + "Gas Conc." + "\n" + "hours" + "\t" + "%" + "\n";

    for (var i = 0; i < 9; i) {

        var result = 100 * (Qg / (Qg+Qa)) * (1 - Math.exp(-(Qg+Qa)*i/v ));

        str += parseFloat(i).toFixed(3) + "\t" + parseFloat(result).toFixed(3)  + "\n";

        i = i + time/60;
    }

    gasconc.Output = str;
}


function measureAmountInMeters(measureType, measureValue) {

	if (measureType == 0) { //meters 
		measureType =  measureValue / 100;
	} else if (measureType == 1) { //feet
		measureType = measureValue / 3.28;
	} else if (measureType == 2) { // inches 
		measureType =  measureValue *(0.0254);
	} else if (measureType == 3) { // meters
		measureType =  measureValue;
	} else {
		measureType =  measureValue / 1000; // milimeters
	}

	return  (parseFloat(measureType)).toFixed(3);
}