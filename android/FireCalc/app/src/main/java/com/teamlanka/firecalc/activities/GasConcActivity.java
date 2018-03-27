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
import com.teamlanka.firecalc.models.GasConcModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GasConcActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner spinnerQg,spinnerV;
    EditText airEditText, qgEditText, vEditText,timeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gasconc);

        initView();
    }

    private void initView() {

        spinnerQg = (Spinner) findViewById(R.id.spinnerQg);
        spinnerQg.setSelection(0);

        spinnerV = (Spinner) findViewById(R.id.spinnerV);
        spinnerV.setSelection(0);

        Button callConduction = (Button) findViewById(R.id.calculateGasConc);
        callConduction.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        airEditText = (EditText) findViewById(R.id.airEditText);
        qgEditText = (EditText) findViewById(R.id.qgEditText);
        vEditText = (EditText) findViewById(R.id.vEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);

    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent(product_item_activity, GasConcActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calculateGasConc:
                try {
                    calcuateGasConc();
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

    private void calcuateGasConc() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        // Air changes
        jsonObject.accumulate(GasConcModel.airchanges, ((EditText) findViewById(R.id.airEditText)).getText().toString());

        // Qg
        jsonObject.accumulate(GasConcModel.qg, ((EditText) findViewById(R.id.qgEditText)).getText().toString());
        jsonObject.accumulate(GasConcModel.qgMeasure, spinnerQg.getSelectedItemPosition());

        //  V
        jsonObject.accumulate(GasConcModel.v, ((EditText) findViewById(R.id.vEditText)).getText().toString());
        jsonObject.accumulate(GasConcModel.vMeasure, spinnerV.getSelectedItemPosition());

        // time
        jsonObject.accumulate(GasConcModel.timestep, ((EditText) findViewById(R.id.timeEditText)).getText().toString());

        final GasConcModel response =
                jsonHttpClient.Post(ServiceUrl.GASCONC, jsonObject, GasConcModel.class);

        final Dialog dialog = new Dialog(GasConcActivity.this);

        dialog.setContentView(R.layout.gasconc_dialog_layout);

        dialog.setTitle("Gas Concentration");

        ((TextView) dialog.findViewById(R.id.outputText)).setText(response.Output);

        Button button = (Button) dialog.findViewById(R.id.send_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callPatriceValues() {
        spinnerQg.setSelection(0);
        spinnerV.setSelection(0);

        airEditText.setText("0.5");
        qgEditText.setText("1");
        vEditText.setText("1000");
        timeEditText.setText("2");
    }

    private void callClear() {
        spinnerQg.setSelection(0);
        spinnerV.setSelection(0);

        airEditText.setText("");
        qgEditText.setText("");
        vEditText.setText("");
        timeEditText.setText("");
    }

    public boolean validate() {
        return true;
    }

    public void onLoginFailed() {
    }

}
