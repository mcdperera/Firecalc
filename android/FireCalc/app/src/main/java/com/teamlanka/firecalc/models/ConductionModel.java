package com.teamlanka.firecalc.models;

/**
 * Created by Charmal on 1/16/2018.
 */
public class ConductionModel {

    public float Lm;

    public float kWmk;
    public float kkWmk;

    public float qBut;
    public float qkW;


    public float  kpc;

    public float tSec;
    public float  tHrs;
    public float tMin;


    public String getLm() {
        return String.valueOf(Lm) + Constants.m;
    }

    public String getKwmk() {
        return String.valueOf(kWmk) + Constants.WmK;
    }

    public String getKkwmk() {
        return String.valueOf(kkWmk) + Constants.kWmK;
    }

    public String getQkW() {
        return String.valueOf(qkW) + Constants.WmK;
    }

    public String getQBut() {
        return String.valueOf(qBut) + Constants.kWmK;
    }

    public String getKpc() {
        return String.valueOf(kpc);
    }

    public String getTsec() {
        return String.valueOf(tSec) + Constants.sec;
    }
    public String getTmin() {
        return String.valueOf(tHrs) + Constants.hrs;
    }
    public String getTHrs() {
        return String.valueOf(tMin) + Constants.min;
    }

    public static final String L = "L";
    public static final String LMeasure = "LMeasure";

    public static final String Material = "Material";

    public static final String T1 = "T1";
    public static final String T1Measure = "T1Measure";

    public static final String T2 = "T2";
    public static final String T2Measure = "T2Measure";



}
