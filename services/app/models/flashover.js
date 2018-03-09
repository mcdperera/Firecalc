const mongoose = require('mongoose');

// flashover Schema
const flashOverSchema = mongoose.Schema({
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

	InteriorLiningThermalConductivity: {
		type: Number,
		required: false
	},

	hk: {
		type: Number,
		required: false
	},
	Av: {
		type: Number,
		required: false
	},
	At: {
		type: Number,
		required: false
	},

	McCaffrey: {
		type: Number
	},
	McCaffreyBtu: {
		type: Number
	},

	Babrauskas: {
		type: Number
	},
	BabrauskasBtu: {
		type: Number
	},

	Thomas: {
		type: Number
	},
	ThomasBtu: {
		type: Number
	}
});

const Flashover = module.exports = mongoose.model('Flashover', flashOverSchema);

// calculate flashover
module.exports.calculate = (flashover, callback) => {

	var CompartmentWidth = measureAmountInMeters(flashover.CompartmentWidthMeasure, flashover.CompartmentWidth);
	var CompartmentLength = measureAmountInMeters(flashover.CompartmentLengthMeasure, flashover.CompartmentLength);
	var CompartmentHeight = measureAmountInMeters(flashover.CompartmentHeightMeasure, flashover.CompartmentHeight);
	var VentWidth = measureAmountInMeters(flashover.VentWidthMeasure, flashover.VentWidth);
	var VentHeight = measureAmountInMeters(flashover.VentHeightMeasure, flashover.VentHeight);
	var InteriorLiningThickness = measureAmountInMeters(flashover.InteriorLiningThicknessMeasure, flashover.InteriorLiningThickness);

	flashover.Av = parseFloat(VentWidth* VentHeight).toFixed(3);
	flashover.At =  (parseFloat(2 * (CompartmentWidth * CompartmentLength) + 2 * (CompartmentHeight * CompartmentWidth) + 2 * (CompartmentHeight * CompartmentLength) - flashover.Av)).toFixed(3);
	flashover.InteriorLiningThermalConductivity = getThermalConductivity(flashover.InteriorLiningMaterial);
	flashover.hk = parseFloat(flashover.InteriorLiningThermalConductivity / InteriorLiningThickness).toFixed(3);

	flashover.McCaffrey = Math.ceil((610 * Math.sqrt(flashover.hk * flashover.At * flashover.Av * Math.sqrt(VentHeight))));
	flashover.McCaffreyBtu = Math.ceil((610 * Math.sqrt(flashover.hk * flashover.At * flashover.Av * Math.sqrt(VentHeight))) * 1.055055852);

	flashover.Babrauskas = Math.ceil((750 * flashover.Av * Math.sqrt(VentHeight)));
	flashover.BabrauskasBtu = Math.ceil((750 * flashover.Av * Math.sqrt(VentHeight)) * 1.055055852);

	flashover.Thomas = Math.ceil((7.8 * flashover.At + 378 * flashover.Av * Math.sqrt(VentHeight)));
	flashover.ThomasBtu = Math.ceil((7.8 * flashover.At + 378 * flashover.Av * Math.sqrt(VentHeight)) * 1.055055852);

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

