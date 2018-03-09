const mongoose = require('mongoose');

// conduction Schema
const conductionSchema = mongoose.Schema({

	Material: {
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

	Lm: {
		type: Number,
		required: true
	},

	T2: {
		type: Number,
		required: true
	},

	T2Measure: {
		type: Number,
		required: true
	},


	T1: {
		type: Number,
		required: true
	},

	T1Measure: {
		type: Number,
		required: true
	},

	kWmk: {
		type: Number,
		required: true
	},

	kkWmk: {
		type: Number,
		required: true
	},

	qBut: {
		type: Number,
		required: true
	},
	qkW: {
		type: Number,
		required: true
	},

	kpc: {
		type: Number,
		required: true
	},

	tSec: {
		type: Number,
		required: true
	},

	tHrs: {
		type: Number,
		required: true
	},

	tMin: {
		type: Number,
		required: true
	},
});

const Conduction = module.exports = mongoose.model('Conduction', conductionSchema);

// calculate Conduction
module.exports.calculate = (conduction, callback) => {

	var L = measureAmountInMeters(conduction.L, conduction.LMeasure);

	conduction.Lm = L;
	var T2 = measureAmountInTemperature(conduction.T2, conduction.T2Measure);
	var T1 = measureAmountInTemperature(conduction.T1, conduction.T1Measure);

	var kWmk = getThermalConductivity(conduction.Material);

	var kkWmk = kWmk / 1000;

	conduction.kkWmk = kWmk;
	conduction.kWmk = kkWmk;

	var q = kkWmk * (T2 - T1) / T1

	conduction.qBut = q * 1 / 11.3565267;
	conduction.qkW = q

	var kpc = getThermalInertia(conduction.Material);
	conduction.kpc = kpc
	var t = (L ^ 2) / (16 * kpc);

	conduction.tSec = t * 1 / 3600;
	conduction.tMin = q / 60;
	conduction.tHrs = q;

	// var CompartmentLength = measureAmountInMeters(gaslayer.CompartmentLengthMeasure, gaslayer.CompartmentLength);
	// var CompartmentHeight = measureAmountInMeters(gaslayer.CompartmentHeightMeasure, gaslayer.CompartmentHeight);
	// var VentWidth = measureAmountInMeters(gaslayer.VentWidthMeasure, gaslayer.VentWidth);
	// var VentHeight = measureAmountInMeters(gaslayer.VentHeightMeasure, gaslayer.VentHeight);
	// var InteriorLiningThickness = measureAmountInMeters(gaslayer.InteriorLiningThicknessMeasure, gaslayer.InteriorLiningThickness);
	// var Q = measureAmountInEnergy(gaslayer.QMeasure, gaslayer.Q);


	// console.log("CompartmentWidth" + CompartmentWidth);
	// console.log("CompartmentLength" + CompartmentLength);
	// console.log("CompartmentHeight" + CompartmentHeight);
	// console.log("VentWidth" + VentWidth);
	// console.log("VentHeight" + VentHeight);
	// console.log("InteriorLiningThickness" + InteriorLiningThickness);
	// console.log("Q" + Q);
	// console.log("AmbientTemp" + AmbientTemp);



	// gaslayer.q = Q;
	// gaslayer.ambientTemp = AmbientTemp;

	// gaslayer.InteriorLiningThermalConductivity = getThermalConductivity(gaslayer.InteriorLiningMaterial);
	// gaslayer.hk = parseFloat(gaslayer.InteriorLiningThermalConductivity / InteriorLiningThickness).toFixed(3);
	// gaslayer.Ao = parseFloat(VentWidth* VentHeight).toFixed(3);
	// gaslayer.At =  (parseFloat(2 * (CompartmentWidth * CompartmentLength) + 2 * (CompartmentHeight * CompartmentWidth) + 2 * (CompartmentHeight * CompartmentLength) - gaslayer.Ao)).toFixed(3);
	// gaslayer.Ho = parseFloat(VentHeight).toFixed(9);

	// var denomi1 = (gaslayer.Ao  * gaslayer.hk * gaslayer.At);
	// console.log("denomi1 " + denomi1);

	// var demo2 =  Math.sqrt(gaslayer.Ho);
	// console.log("demo2 " + demo2);

	// var denomi = (denomi1 * demo2);
	// console.log("denomi " + denomi);

	// var q2 = Q ^ 2;
	// console.log("q2" + q2);

	// var tempK = ((( (q2) / denomi ) ^ (1/3) ) * 6.85 - 0)+ (AmbientTemp -0 );

	// console.log("tempK" + tempK);
	// gaslayer.tempK = tempK.toFixed(3);;
	// gaslayer.tempC = measureAmountInTemperature(0,tempK);
	// gaslayer.tempF =measureAmountInTemperature(1,tempK);
	// gaslayer.tempR = measureAmountInTemperature(3,tempK);
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

function measureAmountInEnergy(measureType, measureValue) {

	if (measureType == 0) { //kW 
		measureType = measureValue;
	} else if (measureType == 1) { //Btu/sec
		measureType = measureValue * 1.055055852;
	}

	return (parseFloat(measureType)).toFixed(1);
}

function measureAmountInTemperature(measureType, measureValue) {

	//=IF(D11="[F]",((C11-32)*(5/9)+273.15),IF(D11="[C]",C11+273.15,IF(D11="[K]",C11,(C11-459.67-32)*5/9+273.15)))
	if (measureType == 0) { //C 
		measureType = measureValue;
	} else if (measureType == 1) { //F
		measureType = (measureValue - 32) * (5 / 9);
	} else if (measureType == 2) { // K 
		measureType = measureValue - 273.15;
	} else if (measureType == 3) { // R
		measureType = (measureValue - 459.67 - 32) * 5 / 9;
	}

	return (parseFloat(measureType)).toFixed(1);
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


function getThermalInertia(material) {

	var thermalInertia = 0;

	if (material == 0) {
		thermalInertia = 0.000022727272727;
	} else if (material == 1) {
		thermalInertia = 0.000000247586036;
	} else if (material == 2) {
		thermalInertia = 0.000000513392857;
	} else if (material == 3) {
		thermalInertia = 0.000000691699605;
	} else if (material == 4) {
		thermalInertia = 0.000000478468900;
	} else if (material == 5) {
		thermalInertia = 0.000113917343695;
	} else if (material == 6) {
		thermalInertia = 0.000000085664737;
	} else if (material == 7) {
		thermalInertia = 0.000000335097002;
	} else if (material == 8) {
		thermalInertia = 0.000000396825397;
	} else if (material == 9) {
		thermalInertia = 0.000000089285714;
	} else if (material == 10) {
		thermalInertia = 0.000000112439342;
	} else if (material == 11) {
		thermalInertia = 0.000001214285714;
	} else if (material == 12) {
		thermalInertia = 0.000012683467184;
	} else if (material == 13) {
		thermalInertia = 0.000000076754386;
	}

	return thermalInertia;

}