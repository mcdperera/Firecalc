package com.teamlanka.firecalc.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.Utils.JSONHttpClient;
import com.teamlanka.firecalc.constants.ServiceUrl;
import com.teamlanka.firecalc.models.ConductionModel;
import com.teamlanka.firecalc.models.GaslayerModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ConductionActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerMaterial, spinnerL, spinnerT1, spinnerT2;

    EditText t2EditText, lEditText, t1EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conduction);

        initView();
    }

    private void initView() {

        spinnerMaterial = (Spinner) findViewById(R.id.spinnerMaterial);
        spinnerMaterial.setSelection(8);

        spinnerL = (Spinner) findViewById(R.id.spinnerL);
        spinnerL.setSelection(2);

        spinnerT1 = (Spinner) findViewById(R.id.spinnerT1);
        spinnerT1.setSelection(2);

        spinnerT2 = (Spinner) findViewById(R.id.spinnerT2);
        spinnerT2.setSelection(2);

        Button callConduction = (Button) findViewById(R.id.callConduction);
        callConduction.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        t2EditText = (EditText) findViewById(R.id.t2EditText);
        lEditText = (EditText) findViewById(R.id.lEditText);
        t1EditText = (EditText) findViewById(R.id.t1EditText);

    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent(product_item_activity, ConductionActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callConduction:
                try {
                    viewConductionDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
                    case R.id.buttonPatrice:

                callPatriceValues();

                break;
            case R.id.buttonClear:

                callClear();

                break;

        }
    }

    private void viewConductionDialog() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        // Compartment Width
        jsonObject.accumulate(ConductionModel.Material, spinnerMaterial.getSelectedItemPosition());

        // Compartment Length
        jsonObject.accumulate(ConductionModel.L, ((EditText) findViewById(R.id.lEditText)).getText().toString());
        jsonObject.accumulate(ConductionModel.LMeasure, spinnerL.getSelectedItemPosition());

        // Compartment Height
        jsonObject.accumulate(ConductionModel.T1, ((EditText) findViewById(R.id.t1EditText)).getText().toString());
        jsonObject.accumulate(ConductionModel.T1Measure, spinnerT1.getSelectedItemPosition());

        // vent width
        jsonObject.accumulate(ConductionModel.T2, ((EditText) findViewById(R.id.t2EditText)).getText().toString());
        jsonObject.accumulate(ConductionModel.T2Measure, spinnerT2.getSelectedItemPosition());

        final ConductionModel response =
                jsonHttpClient.Post(ServiceUrl.CONDUCTION, jsonObject, ConductionModel.class);

        final Dialog dialog = new Dialog(ConductionActivity.this);

        dialog.setContentView(R.layout.conduction_dialog_layout);

        dialog.setTitle("Conductive heat transfer");


        ((TextView) dialog.findViewById(R.id.edittextL)).setText(response.getLm());
        ((TextView) dialog.findViewById(R.id.edittextKwmk)).setText(response.getKwmk());
        ((TextView) dialog.findViewById(R.id.edittextKkWmk)).setText(response.getKkwmk());


        ((TextView) dialog.findViewById(R.id.edittextqKw)).setText(response.getQkW());
        ((TextView) dialog.findViewById(R.id.edittextqBtu)).setText(response.getQBut());

        ((TextView) dialog.findViewById(R.id.edittextkpc)).setText(response.getKpc());

        ((TextView) dialog.findViewById(R.id.editTextTSec)).setText(response.getTsec());
        ((TextView) dialog.findViewById(R.id.editTextTMIn)).setText(response.getTmin());
        ((TextView) dialog.findViewById(R.id.editTextTHrs)).setText(response.getTHrs());

        Button button = (Button) dialog.findViewById(R.id.send_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callPatriceValues() {
        lEditText.setText("0.6");
        t2EditText.setText("600");
        t1EditText.setText("21");

        spinnerMaterial.setSelection(8);

        spinnerL.setSelection(2);
        spinnerT1.setSelection(0);
        spinnerT2.setSelection(0);
    }

    private void callClear() {

        lEditText.setText("");
        t2EditText.setText("");
        t1EditText.setText("");

        spinnerMaterial.setSelection(8);

        spinnerL.setSelection(2);
        spinnerT1.setSelection(0);
        spinnerT2.setSelection(0);
    }

    public boolean validate() {
//        boolean valid = true;
//
//        EditText compartmentWidth = (EditText) findViewById(R.id.compartmentWidth);
//
//        if (!compartmentWidth.toString().isEmpty()) {
//            compartmentWidth.setError(null);
//        } else {
//            compartmentWidth.setError("Enter Compartment Width");
//            valid = false;
//        }
//
//        EditText compartmentLength = (EditText) findViewById(R.id.compartmentLength);
//
//        if (!compartmentLength.toString().isEmpty()) {
//            compartmentLength.setError(null);
//        } else {
//            compartmentLength.setError("Enter Compartment Length");
//            valid = false;
//        }
//
//        EditText compartmentHeight = (EditText) findViewById(R.id.compartmentHeight);
//
//        if (!compartmentHeight.toString().isEmpty()) {
//            compartmentHeight.setError(null);
//        } else {
//            compartmentHeight.setError("Enter Compartment height");
//            valid = false;
//        }
//
//        EditText ventWidth = (EditText) findViewById(R.id.ventWidth);
//
//        if (!ventWidth.toString().isEmpty()) {
//            ventWidth.setError(null);
//        } else {
//            ventWidth.setError("Enter Vent Width");
//            valid = false;
//        }
//
//
//        EditText ventHeight = (EditText) findViewById(R.id.ventHeight);
//
//        if (!ventHeight.toString().isEmpty()) {
//            ventHeight.setError(null);
//        } else {
//            ventHeight.setError("Enter Vent Height");
//            valid = false;
//        }
//
//        EditText interiorThickness = (EditText) findViewById(R.id.interiorThickness);
//
//        if (!interiorThickness.toString().isEmpty()) {
//            interiorThickness.setError(null);
//        } else {
//            interiorThickness.setError("Enter Interior Thickness");
//            valid = false;
//        }
//
//        return valid;

        return true;
    }

    public void onLoginFailed() {
        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

    }


}
