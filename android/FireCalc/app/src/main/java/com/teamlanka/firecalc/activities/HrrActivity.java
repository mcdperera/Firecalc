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
import com.teamlanka.firecalc.models.HrrModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class HrrActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerFuels, spinnerLengths2, spinnerRadius;

    EditText radiusText, areaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hrr);

        initView();
    }

    private void initView() {

        spinnerFuels = (Spinner) findViewById(R.id.spinnerFuels);
        spinnerFuels.setSelection(1);

        spinnerLengths2 = (Spinner) findViewById(R.id.spinnerLengths2);
        spinnerLengths2.setSelection(2);

        spinnerRadius = (Spinner) findViewById(R.id.spinnerRadius);
        spinnerRadius.setSelection(2);

        Button callConduction = (Button) findViewById(R.id.calculateHrr);
        callConduction.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        areaText = (EditText) findViewById(R.id.areaText);
        radiusText = (EditText) findViewById(R.id.radiusText);

    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent(product_item_activity, HrrActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calculateHrr:
                try {
                    calculateHrr();
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

    private void calculateHrr() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        // Fuel
        jsonObject.accumulate(HrrModel.Fuel, spinnerFuels.getSelectedItemPosition());

        // Area
        jsonObject.accumulate(HrrModel.A, ((EditText) findViewById(R.id.areaText)).getText().toString());
        jsonObject.accumulate(HrrModel.AMeasure, spinnerLengths2.getSelectedItemPosition());

        // Radius
        jsonObject.accumulate(HrrModel.Radius, ((EditText) findViewById(R.id.radiusText)).getText().toString());
        jsonObject.accumulate(HrrModel.RadiusMeasure, spinnerRadius.getSelectedItemPosition());

        final HrrModel response =
                jsonHttpClient.Post(ServiceUrl.HRR, jsonObject, HrrModel.class);

        final Dialog dialog = new Dialog(HrrActivity.this);

        dialog.setContentView(R.layout.hrr_dialog_layout);

        dialog.setTitle("HRR");

        ((TextView) dialog.findViewById(R.id.mText)).setText(response.m);
        ((TextView) dialog.findViewById(R.id.dHcText)).setText(response.dHc);
        ((TextView) dialog.findViewById(R.id.qText)).setText(response.Q);
        ((TextView) dialog.findViewById(R.id.areaFeetText)).setText(response.AreaF2);
        ((TextView) dialog.findViewById(R.id.areaMetersText)).setText(response.AreaM2);

        Button button = (Button) dialog.findViewById(R.id.send_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callPatriceValues() {
        spinnerFuels.setSelection(1);
        spinnerLengths2.setSelection(2);
        spinnerRadius.setSelection(2);

        areaText.setText("12.566");
        radiusText.setText("2");
    }

    private void callClear() {

        spinnerFuels.setSelection(1);
        spinnerLengths2.setSelection(2);
        spinnerRadius.setSelection(2);

        areaText.setText("");
        radiusText.setText("");
    }

    public boolean validate() {
        return true;
    }

    public void onLoginFailed() {
    }


}
