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

    public String McCaffrey;
    public String McCaffreyBtu;

    public String Babrauskas;
    public String BabrauskasBtu;

    public String Thomas;
    public String ThomasBtu;

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
        return McCaffrey;
    }

    public String getMcCaffreyBtu() {
        return McCaffreyBtu;
    }

    public String getBabrauskas() {
        return Babrauskas;
    }

    public String getBabrauskasBtu() {
        return BabrauskasBtu;
    }

    public String getThomas() {
        return Thomas;
    }

    public String getThomasBtu() {
        return ThomasBtu;
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
