package com.teamlanka.firecalc.models;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by Charmal on 1/16/2018.
 */
public class FlashoverModel {

    public float hk;
    public float Av;
    public float At;
    public String InteriorLiningThermalConductivity;

    public float McCaffrey;
    public float McCaffreyBtu;

    public float Babrauskas;
    public float BabrauskasBtu;

    public float Thomas;
    public float ThomasBtu;

    public String getInteriorConductivity() {
        return InteriorLiningThermalConductivity + Constants.kWmK;
    }

    public String getHk() {
        return String.valueOf(hk) + Constants.kWmK;
    }

    public String getAv() {
        return String.valueOf(Av) + Constants.mm;
    }

    public String getAt() {
        return String.valueOf(At) + Constants.mm;
    }

    public String getMcCaffrey() {
        return String.valueOf(McCaffrey) + Constants.kw;
    }

    public String getMcCaffreyBtu() {
        return String.valueOf(McCaffreyBtu) + Constants.btuSec;
    }

    public String getBabrauskas() {
        return String.valueOf(Babrauskas) + Constants.kw;
    }

    public String getBabrauskasBtu() {
        return String.valueOf(BabrauskasBtu) + Constants.btuSec;
    }

    public String getThomas() {
        return String.valueOf(Thomas) + Constants.kw;
    }

    public String getThomasBtu() {
        return String.valueOf(ThomasBtu) + Constants.btuSec;
    }

    public static final String Compartment_Width = "CompartmentWidth";
    public static final String Compartment_Length = "CompartmentLength";
    public static final String Compartment_Height = "CompartmentHeight";
    public static final String Vent_Width = "VentWidth";
    public static final String Vent_Height = "VentHeight";
    public static final String Interior_Lining_Thickness = "InteriorLiningThickness";
    public static final String Interior_Lining_Material = "InteriorLiningMaterial";

    public static final String Compartment_Width_Measure = "CompartmentWidthMeasure";
    public static final String Compartment_Length_Measure = "CompartmentLengthMeasure";
    public static final String Compartment_Height_Measure = "CompartmentHeightMeasure";
    public static final String Vent_Width_Measure = "VentWidthMeasure";
    public static final String Vent_Height_Measure = "VentHeightMeasure";
    public static final String Interior_Lining_Thickness_Measure = "InteriorLiningThicknessMeasure";

}
