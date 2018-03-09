const mongoose = require('mongoose');

// gaslayer Schema
const gaslayerSchema = mongoose.Schema({
	CompartmentWidth: {
		type: Number,
		required: true
	},
	CompartmentWidthMeasure: {
		type: Number,
		required: true
	},

	CompartmentLength: {
		type: Number,
		required: true
	},
	CompartmentLengthMeasure: {
		type: Number,
		required: true
	},

	CompartmentHeight: {
		type: Number
	},
	CompartmentHeightMeasure: {
		type: Number
	},

	VentWidth: {
		type: Number,
		required: true
	},

	VentWidthMeasure: {
		type: Number,
		required: true
	},

	VentHeight: {
		type: Number,
		required: true
	},

	VentHeightMeasure: {
		type: Number,
		required: true
	},


	InteriorLiningThickness: {
		type: Number,
		required: true
	},

	InteriorLiningThicknessMeasure: {
		type: Number,
		required: true
	},

	InteriorLiningMaterial: {
		type: Number,
		required: true
	},

	Q: {
		type: Number,
		required: true
	},

	q: {
		type: Number,
		required: true
	},

	QMeasure: {
		type: Number,
		required: true
	},

	ambientTemp: {
		type: Number,
		required: true
	},

	AmbientTemp: {
		type: Number,
		required: true
	},

	AmbientTempMeasure: {
		type: Number,
		required: true
	},

	InteriorLiningThermalConductivity: {
		type: Number,
		required: false
	},

	hk: {
		type: Number,
		required: false
	},
	Ao: {
		type: Number,
		required: false
	},
	At: {
		type: Number,
		required: false
	},

	Ho: {
		type: Number
	},

	tempK: {
		type: Number
	},

	tempC: {
		type: Number
	},
	tempF: {
		type: Number
	},

	tempR: {
		type: Number
	}
	
});

const Gaslayer = module.exports = mongoose.model('Gaslayer', gaslayerSchema);

// calculate gaslayer
module.exports.calculate = (gaslayer, callback) => {

	var CompartmentWidth = measureAmountInMeters(gaslayer.CompartmentWidthMeasure, gaslayer.CompartmentWidth);
	var CompartmentLength = measureAmountInMeters(gaslayer.CompartmentLengthMeasure, gaslayer.CompartmentLength);
	var CompartmentHeight = measureAmountInMeters(gaslayer.CompartmentHeightMeasure, gaslayer.CompartmentHeight);
	var VentWidth = measureAmountInMeters(gaslayer.VentWidthMeasure, gaslayer.VentWidth);
	var VentHeight = measureAmountInMeters(gaslayer.VentHeightMeasure, gaslayer.VentHeight);
	var InteriorLiningThickness = measureAmountInMeters(gaslayer.InteriorLiningThicknessMeasure, gaslayer.InteriorLiningThickness);
	var Q = measureAmountInEnergy(gaslayer.QMeasure, gaslayer.Q);
	var AmbientTemp = measureAmountInTemperature(gaslayer.AmbientTempMeasure, gaslayer.AmbientTemp);

	console.log("CompartmentWidth" + CompartmentWidth);
	console.log("CompartmentLength" + CompartmentLength);
	console.log("CompartmentHeight" + CompartmentHeight);
	console.log("VentWidth" + VentWidth);
	console.log("VentHeight" + VentHeight);
	console.log("InteriorLiningThickness" + InteriorLiningThickness);
	console.log("Q" + Q);
	console.log("AmbientTemp" + AmbientTemp);

	gaslayer.q = Q;
	gaslayer.ambientTemp = AmbientTemp;

	gaslayer.InteriorLiningThermalConductivity = getThermalConductivity(gaslayer.InteriorLiningMaterial);
	gaslayer.hk = parseFloat(gaslayer.InteriorLiningThermalConductivity / InteriorLiningThickness).toFixed(3);
	gaslayer.Ao = parseFloat(VentWidth* VentHeight).toFixed(3);
	gaslayer.At =  (parseFloat(2 * (CompartmentWidth * CompartmentLength) + 2 * (CompartmentHeight * CompartmentWidth) + 2 * (CompartmentHeight * CompartmentLength) - gaslayer.Ao)).toFixed(3);
	gaslayer.Ho = parseFloat(VentHeight).toFixed(9);

	var denomi1 = (gaslayer.Ao  * gaslayer.hk * gaslayer.At);
	console.log("denomi1 " + denomi1);

	var demo2 =  Math.sqrt(gaslayer.Ho);
	console.log("demo2 " + demo2);

	var denomi = (denomi1 * demo2);
	console.log("denomi " + denomi);

	var q2 = Q ^ 2;
	console.log("q2" + q2);

	var tempK = ((( (q2) / denomi ) ^ (1/3) ) * 6.85 - 0)+ (AmbientTemp -0 );

	console.log("tempK" + tempK);
	gaslayer.tempK = tempK.toFixed(3);;
	gaslayer.tempC = measureAmountInTemperature(0,tempK);
	gaslayer.tempF =measureAmountInTemperature(1,tempK);
	gaslayer.tempR = measureAmountInTemperature(3,tempK);
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

function measureAmountInEnergy(measureType, measureValue) {

	if (measureType == 0) { //kW 
		measureType =  measureValue;
	} else if (measureType == 1) { //Btu/sec
		measureType = measureValue *1.055055852;
	}

	return  (parseFloat(measureType)).toFixed(1);
}

function measureAmountInTemperature(measureType, measureValue) {

	//=IF(D11="[F]",((C11-32)*(5/9)+273.15),IF(D11="[C]",C11+273.15,IF(D11="[K]",C11,(C11-459.67-32)*5/9+273.15)))
	if (measureType == 0) { //C 
		measureType =  measureValue + 273.15 ;
	} else if (measureType == 1) { //F
		measureType = (measureValue - 32)*(5/9) + 273.15 ;
	} else if (measureType == 2) { // K 
		measureType =  measureValue;
	} else if (measureType == 3) { // R
		measureType =  (measureValue - 459.67 -32)*5/9 +273.15;
	} 

	return  (parseFloat(measureType)).toFixed(1);
}


function getThermalConductivity(material) {

	var conductivity = 0;

	if (material == 0) {
		conductivity = 0.00026;
	} else if (material == 1) {
		conductivity = 0.00014;
	} else if (material == 2) {
		conductivity = 0.206;
	} else if (material == 3) {
		conductivity = 0.0008;
	} else if (material == 4) {
		conductivity = 0.00073;
	} else if (material == 5) {
		conductivity = 0.00013;
	} else if (material == 6) {
		conductivity = 0.00015;
	} else if (material == 7) {
		conductivity = 0.0016;
	} else if (material == 8) {
		conductivity = 0.000034;
	} else if (material == 9) {
		conductivity = 0.00053;
	} else if (material == 10) {
		conductivity = 0.000037;
	} else if (material == 11) {
		conductivity = 0.00076;
	} else if (material == 12) {
		conductivity = 0.00017;
	}
	else if (material == 13) {
		conductivity = 0.00016;
	}
	else if (material == 14) {
		conductivity = 0.00012;
	}
	else {
		conductivity = 0.054;
	}

	return conductivity;

}

