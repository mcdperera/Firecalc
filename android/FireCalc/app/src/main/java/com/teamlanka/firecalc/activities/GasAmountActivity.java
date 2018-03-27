package com.teamlanka.firecalc.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.teamlanka.firecalc.models.GasAmountModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GasAmountActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerGases, spinnerArea, spinnerHeight;

    EditText areaText, heightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gasamount);

        initView();
    }

    private void initView() {

        spinnerGases = (Spinner) findViewById(R.id.spinnerGases);
        spinnerGases.setSelection(0);

        spinnerArea = (Spinner) findViewById(R.id.spinnerArea);
        spinnerArea.setSelection(0);

        spinnerHeight = (Spinner) findViewById(R.id.spinnerHeight);
        spinnerHeight.setSelection(1);

        Button callConduction = (Button) findViewById(R.id.calculateGasAmount);
        callConduction.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        areaText = (EditText) findViewById(R.id.areaText);
        heightText = (EditText) findViewById(R.id.heightText);
    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent(product_item_activity, GasAmountActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calculateGasAmount:
                try {
                    calculateGasAmount();
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

    private void calculateGasAmount() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        // Type
        jsonObject.accumulate(GasAmountModel.type, spinnerGases.getSelectedItemPosition());

        // Area
        jsonObject.accumulate(GasAmountModel.Area, ((EditText) findViewById(R.id.areaText)).getText().toString());
        jsonObject.accumulate(GasAmountModel.AreaMeasure, spinnerArea.getSelectedItemPosition());

        // Height
        jsonObject.accumulate(GasAmountModel.Height, ((EditText) findViewById(R.id.heightText)).getText().toString());
        jsonObject.accumulate(GasAmountModel.HeightMeasure, spinnerHeight.getSelectedItemPosition());

        final GasAmountModel response =
                jsonHttpClient.Post(ServiceUrl.GASAMOUNT, jsonObject, GasAmountModel.class);

        final Dialog dialog = new Dialog(GasAmountActivity.this);

        dialog.setContentView(R.layout.gasamount_dialog_layout);

        dialog.setTitle("Gas Amount");

        ((TextView) dialog.findViewById(R.id.volumeGasLEL)).setText(response.VolumeOfGasLEL);
        ((TextView) dialog.findViewById(R.id.volumeGasStoich)).setText(response.VolumeOfGasStoich);
        ((TextView) dialog.findViewById(R.id.volumeGasUEL)).setText(response.VolumeOfGasUEL);


        ((TextView) dialog.findViewById(R.id.weightOfGasLEL)).setText(response.WeightOfGasLEL);
        ((TextView) dialog.findViewById(R.id.weightOfGasStoich)).setText(response.WeightOfGasStoich);
        ((TextView) dialog.findViewById(R.id.weightOfGasUEL)).setText(response.WeightOfGasUEL);

        ((TextView) dialog.findViewById(R.id.volumeOfLiquidLEL)).setText(response.VolumeOfLiquidLEL);
        ((TextView) dialog.findViewById(R.id.volumeOfLiquidStoich)).setText(response.VolumeOfLiquidStoich);
        ((TextView) dialog.findViewById(R.id.volumeOfLiquidUel)).setText(response.VolumeOfLiquidUEL);

        Button button = (Button) dialog.findViewById(R.id.send_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callPatriceValues() {
        spinnerGases.setSelection(0);
        spinnerArea.setSelection(0);
        spinnerHeight.setSelection(1);

        areaText.setText("4000");
        heightText.setText("6");
    }

    private void callClear() {

        spinnerGases.setSelection(0);
        spinnerArea.setSelection(0);
        spinnerHeight.setSelection(1);

        areaText.setText("");
        heightText.setText("");
    }

    public boolean validate() {
        return true;
    }

    public void onLoginFailed() {
    }


}
