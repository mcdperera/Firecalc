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
import com.teamlanka.firecalc.models.GaslayerModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GaslayerActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerCompartmentWidth, spinnerCompartmentLength,
            spinnerCompartmentHeight, spinnerventWidth,
            spinnerventHeight, spinnerinteriorThickness,
            spinnerinteriorMaterial, spinnerT,
            spinnerQ;

    EditText compartmentWidth, compartmentLength,
            compartmentHeight, ventWidth, ventHeight,
            interiorThickness, editTextT,
            editTextQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gaslayer);

        initView();
    }

    private void initView() {

        spinnerCompartmentWidth = (Spinner) findViewById(R.id.spinnerCompartmentWidth);
        spinnerCompartmentWidth.setSelection(1);

        spinnerCompartmentLength = (Spinner) findViewById(R.id.spinnerCompartmentLength);
        spinnerCompartmentLength.setSelection(1);

        spinnerCompartmentHeight = (Spinner) findViewById(R.id.spinnerCompartmentHeight);
        spinnerCompartmentHeight.setSelection(1);

        spinnerventWidth = (Spinner) findViewById(R.id.spinnerventWidth);
        spinnerventWidth.setSelection(1);

        spinnerventHeight = (Spinner) findViewById(R.id.spinnerventHeight);
        spinnerventHeight.setSelection(1);

        spinnerinteriorThickness = (Spinner) findViewById(R.id.spinnerinteriorThickness);
        spinnerinteriorThickness.setSelection(2);

        spinnerinteriorMaterial = (Spinner) findViewById(R.id.spinnerinteriorMaterial);
        spinnerinteriorMaterial.setSelection(12);

        spinnerT = (Spinner) findViewById(R.id.spinnerT);
        spinnerT.setSelection(1);

        spinnerQ = (Spinner) findViewById(R.id.spinnerQ);
        spinnerQ.setSelection(1);

        Button callGasLayer = (Button) findViewById(R.id.callGasLayer);
        callGasLayer.setOnClickListener(this);

        Button buttonReference = (Button) findViewById(R.id.buttonReference);
        buttonReference.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        compartmentWidth = (EditText) findViewById(R.id.compartmentWidth);
        compartmentLength = (EditText) findViewById(R.id.compartmentLength);
        compartmentHeight = (EditText) findViewById(R.id.compartmentHeight);
        ventWidth = (EditText) findViewById(R.id.ventWidth);
        ventHeight = (EditText) findViewById(R.id.ventHeight);
        interiorThickness = (EditText) findViewById(R.id.interiorThickness);

        editTextQ = (EditText) findViewById(R.id.editTextQ);
        editTextQ.setText("50");

        editTextT = (EditText) findViewById(R.id.editTextT);
        editTextT.setText("72");
    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent( product_item_activity, GaslayerActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callGasLayer:
                try {
                    viewFlashoverDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.buttonReference:

                callReference();

                break;
            case R.id.buttonPatrice:

                callPatriceValues();

                break;
            case R.id.buttonClear:

                callClear();

                break;

        }
    }

    private void viewFlashoverDialog() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        // Compartment Width
        jsonObject.accumulate(GaslayerModel.Compartment_Width, ((EditText) findViewById(R.id.compartmentWidth)).getText().toString());
        jsonObject.accumulate(GaslayerModel.Compartment_Width_Measure, spinnerCompartmentWidth.getSelectedItemPosition());

        // Compartment Length
        jsonObject.accumulate(GaslayerModel.Compartment_Length, ((EditText) findViewById(R.id.compartmentLength)).getText().toString());
        jsonObject.accumulate(GaslayerModel.Compartment_Length_Measure, spinnerCompartmentLength.getSelectedItemPosition());

        // Compartment Height
        jsonObject.accumulate(GaslayerModel.Compartment_Height, ((EditText) findViewById(R.id.compartmentHeight)).getText().toString());
        jsonObject.accumulate(GaslayerModel.Compartment_Height_Measure, spinnerCompartmentHeight.getSelectedItemPosition());

        // vent width
        jsonObject.accumulate(GaslayerModel.Vent_Width, ((EditText) findViewById(R.id.ventWidth)).getText().toString());
        jsonObject.accumulate(GaslayerModel.Vent_Width_Measure, spinnerventWidth.getSelectedItemPosition());

        // vent heigth
        jsonObject.accumulate(GaslayerModel.Vent_Height, ((EditText) findViewById(R.id.ventHeight)).getText().toString());
        jsonObject.accumulate(GaslayerModel.Vent_Height_Measure, spinnerventHeight.getSelectedItemPosition());

        //Interior Lining Thickness
        jsonObject.accumulate(GaslayerModel.Interior_Lining_Thickness, ((EditText) findViewById(R.id.interiorThickness)).getText().toString());
        jsonObject.accumulate(GaslayerModel.Interior_Lining_Thickness_Measure, spinnerinteriorThickness.getSelectedItemPosition());

        // Interior Lining  Material
        jsonObject.accumulate(GaslayerModel.Interior_Lining_Material, spinnerinteriorMaterial.getSelectedItemPosition());

        jsonObject.accumulate(GaslayerModel.Q, ((EditText) findViewById(R.id.editTextQ)).getText().toString());
        jsonObject.accumulate(GaslayerModel.Q_Measure, spinnerQ.getSelectedItemPosition());

        jsonObject.accumulate(GaslayerModel.AmbientTemp,((EditText) findViewById(R.id.editTextT)).getText().toString());
        jsonObject.accumulate(GaslayerModel.AmbientTemp_Measure, spinnerT.getSelectedItemPosition());

        final GaslayerModel response =
                jsonHttpClient.Post(ServiceUrl.GASLAYER, jsonObject, GaslayerModel.class);

        final Dialog dialog = new Dialog(GaslayerActivity.this);

        dialog.setContentView(R.layout.gaslayer_dialog_layout);

        dialog.setTitle("Temperature of Upper Gas Layer");


        ((TextView) dialog.findViewById(R.id.edittextQ)).setText(response.getQ());
        ((TextView) dialog.findViewById(R.id.edittextAmbientTemp)).setText(response.getAmbientTemp());

        ((TextView) dialog.findViewById(R.id.edittextInterial)).setText(response.getInteriorConductivity());

        ((TextView) dialog.findViewById(R.id.edittextHk)).setText(response.getHk());
        ((TextView) dialog.findViewById(R.id.edittextAo)).setText(response.getAo());
        ((TextView) dialog.findViewById(R.id.edittextAt)).setText(response.getAt());

        ((TextView) dialog.findViewById(R.id.editTextHo)).setText(response.getHo());

        ((TextView) dialog.findViewById(R.id.editTextTempC)).setText(response.gettempC());
        ((TextView) dialog.findViewById(R.id.editTextTempF)).setText(response.gettempF());
        ((TextView) dialog.findViewById(R.id.editTextTempK)).setText(response.gettempK());
        ((TextView) dialog.findViewById(R.id.editTextTempR)).setText(response.gettempR());

        Button button = (Button) dialog.findViewById(R.id.send_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callReference() {
        Uri uri = Uri.parse("http://ogneborec.su/files/uploads/files/0460561_8A68C_sfpe_handbook_of_fire_protection_engineering.pdf"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void callPatriceValues() {
        compartmentWidth.setText("8");
        compartmentLength.setText("12");
        compartmentHeight.setText("7");

        ventWidth.setText("12");
        ventHeight.setText("6");

        interiorThickness.setText("0.5");
        spinnerinteriorMaterial.setSelection(12);
    }

    private void callClear() {
        compartmentWidth.setText("");
        compartmentLength.setText("");
        compartmentHeight.setText("");
        ventWidth.setText("");
        ventHeight.setText("");
        interiorThickness.setText("");
        spinnerinteriorMaterial.setSelection(12);
    }

    public boolean validate() {
        boolean valid = true;

        EditText compartmentWidth = (EditText) findViewById(R.id.compartmentWidth);

        if (!compartmentWidth.toString().isEmpty()) {
            compartmentWidth.setError(null);
        } else {
            compartmentWidth.setError("Enter Compartment Width");
            valid = false;
        }

        EditText compartmentLength = (EditText) findViewById(R.id.compartmentLength);

        if (!compartmentLength.toString().isEmpty()) {
            compartmentLength.setError(null);
        } else {
            compartmentLength.setError("Enter Compartment Length");
            valid = false;
        }

        EditText compartmentHeight = (EditText) findViewById(R.id.compartmentHeight);

        if (!compartmentHeight.toString().isEmpty()) {
            compartmentHeight.setError(null);
        } else {
            compartmentHeight.setError("Enter Compartment height");
            valid = false;
        }

        EditText ventWidth = (EditText) findViewById(R.id.ventWidth);

        if (!ventWidth.toString().isEmpty()) {
            ventWidth.setError(null);
        } else {
            ventWidth.setError("Enter Vent Width");
            valid = false;
        }


        EditText ventHeight = (EditText) findViewById(R.id.ventHeight);

        if (!ventHeight.toString().isEmpty()) {
            ventHeight.setError(null);
        } else {
            ventHeight.setError("Enter Vent Height");
            valid = false;
        }

        EditText interiorThickness = (EditText) findViewById(R.id.interiorThickness);

        if (!interiorThickness.toString().isEmpty()) {
            interiorThickness.setError(null);
        } else {
            interiorThickness.setError("Enter Interior Thickness");
            valid = false;
        }

        return valid;
    }

    public void onLoginFailed() {
        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

    }


}
