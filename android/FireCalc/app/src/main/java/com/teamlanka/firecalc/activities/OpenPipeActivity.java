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
import com.teamlanka.firecalc.models.OpenPipeModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OpenPipeActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerPressure, spinnerPipeDiameter, spinnerLength;

    EditText pressureDropText, pipeDiameterText, lengthText,sgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_openpipe);

        initView();
    }

    private void initView() {

        spinnerPressure = (Spinner) findViewById(R.id.spinnerPressure);
        spinnerPressure.setSelection(0);

        spinnerPipeDiameter = (Spinner) findViewById(R.id.spinnerPipeDiameter);
        spinnerPipeDiameter.setSelection(2);

        spinnerLength = (Spinner) findViewById(R.id.spinnerLength);
        spinnerLength.setSelection(1);

        Button callConduction = (Button) findViewById(R.id.calculateOpenPipe);
        callConduction.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        pressureDropText = (EditText) findViewById(R.id.pressureDropText);
        pipeDiameterText = (EditText) findViewById(R.id.pipeDiameterText);
        lengthText = (EditText) findViewById(R.id.lengthText);
        sgText = (EditText) findViewById(R.id.sgText);
    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent(product_item_activity, OpenPipeActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calculateOpenPipe:
                try {
                    calculateOpenPipe();
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

    private void calculateOpenPipe() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        // pressure drop
        jsonObject.accumulate(OpenPipeModel.dp, ((EditText) findViewById(R.id.pressureDropText)).getText().toString());
        jsonObject.accumulate(OpenPipeModel.dpMeasure, spinnerPressure.getSelectedItemPosition());

        // pressure drop
        jsonObject.accumulate(OpenPipeModel.D, ((EditText) findViewById(R.id.pipeDiameterText)).getText().toString());
        jsonObject.accumulate(OpenPipeModel.DMeasure, spinnerPipeDiameter.getSelectedItemPosition());

        // pressure drop
        jsonObject.accumulate(OpenPipeModel.L, ((EditText) findViewById(R.id.lengthText)).getText().toString());
        jsonObject.accumulate(OpenPipeModel.LMeasure, spinnerLength.getSelectedItemPosition());

        // pressure drop
        jsonObject.accumulate(OpenPipeModel.Sg, ((EditText) findViewById(R.id.sgText)).getText().toString());

        final OpenPipeModel response =
                jsonHttpClient.Post(ServiceUrl.OPENPIPE, jsonObject, OpenPipeModel.class);

        final Dialog dialog = new Dialog(OpenPipeActivity.this);

        dialog.setContentView(R.layout.openpipe_dialog_layout);

        dialog.setTitle("Open Pipe");

        ((TextView) dialog.findViewById(R.id.Qm3hrText)).setText(response.Qm3hr);
        ((TextView) dialog.findViewById(R.id.QcfmrText)).setText(response.Qcfmr);

        Button button = (Button) dialog.findViewById(R.id.send_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callPatriceValues() {
        spinnerPressure.setSelection(0);
        spinnerPipeDiameter.setSelection(2);
        spinnerLength.setSelection(1);

        pressureDropText .setText("11");
        pipeDiameterText.setText("0.2");
        lengthText.setText("15");
        sgText.setText("1.5");
    }

    private void callClear() {

        spinnerPressure.setSelection(0);
        spinnerPipeDiameter.setSelection(2);
        spinnerLength.setSelection(1);

        pressureDropText .setText("");
        pipeDiameterText.setText("");
        lengthText.setText("");
        sgText.setText("");
    }

    public boolean validate() {
        return true;
    }

    public void onLoginFailed() {
    }
}
