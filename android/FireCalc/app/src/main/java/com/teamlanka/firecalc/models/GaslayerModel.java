package com.teamlanka.firecalc.models;

/**
 * Created by Charmal on 1/16/2018.
 */
public class GaslayerModel {

    public float q;
    public float ambientTemp;

    public String InteriorLiningThermalConductivity;

    public float hk;
    public float Ao;
    public float At;
    public float Ho;

    public float tempK;
    public float tempC;
    public float tempF;
    public float tempR;

    public String getQ() {
        return String.valueOf(q) + Constants.kw;
    }

    public String getAmbientTemp() {
        return String.valueOf(ambientTemp) + Constants.tempK;
    }

    public String getInteriorConductivity() {
        return InteriorLiningThermalConductivity + Constants.kWmK;
    }

    public String getHk() {
        return String.valueOf(hk) + Constants.kWm2K;
    }

    public String getAo() {
        return String.valueOf(Ao) + Constants.mm;
    }

    public String getAt() {
        return String.valueOf(At) + Constants.mm;
    }

    public String getHo() {
        return String.valueOf(Ho) + Constants.m;
    }

    public String gettempK() {
        return String.valueOf(tempK) + Constants.tempK;
    }

    public String gettempC() {
        return String.valueOf(tempC) + Constants.tempC;
    }

    public String gettempF() {
        return String.valueOf(tempF) + Constants.tempF;
    }

    public String gettempR() {
        return String.valueOf(tempR) + Constants.tempR;
    }

    public static final String Compartment_Width = "CompartmentWidth";
    public static final String Compartment_Length = "CompartmentLength";
    public static final String Compartment_Height = "CompartmentHeight";
    public static final String Vent_Width = "VentWidth";
    public static final String Vent_Height = "VentHeight";
    public static final String Interior_Lining_Thickness = "InteriorLiningThickness";
    public static final String Interior_Lining_Material = "InteriorLiningMaterial";
    public static final String Q = "Q";
    public static final String AmbientTemp = "AmbientTemp";

    public static final String Compartment_Width_Measure = "CompartmentWidthMeasure";
    public static final String Compartment_Length_Measure = "CompartmentLengthMeasure";
    public static final String Compartment_Height_Measure = "CompartmentHeightMeasure";
    public static final String Vent_Width_Measure = "VentWidthMeasure";
    public static final String Vent_Height_Measure = "VentHeightMeasure";
    public static final String Interior_Lining_Thickness_Measure = "InteriorLiningThicknessMeasure";
    public static final String Q_Measure = "QMeasure";
    public static final String AmbientTemp_Measure = "AmbientTempMeasure";

}
