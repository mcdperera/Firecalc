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
import com.teamlanka.firecalc.models.FlameHeightModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class FlameHeightActivity extends AppCompatActivity implements View.OnClickListener {


    Spinner spinnerHeatRelease, spinnerDiameter;

    EditText TextHeatRelease, TextDiameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flameheight);

        initView();
    }

    private void initView() {

        spinnerHeatRelease = (Spinner) findViewById(R.id.spinnerHeatRelease);
        spinnerHeatRelease.setSelection(0);

        spinnerDiameter = (Spinner) findViewById(R.id.spinnerDiameter);
        spinnerDiameter.setSelection(3);

        Button buttonFlashover = (Button) findViewById(R.id.callFlashover);
        buttonFlashover.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        TextHeatRelease = (EditText) findViewById(R.id.TextHeatRelease);
        TextDiameter = (EditText) findViewById(R.id.TextDiameter);

    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent(product_item_activity, FlameHeightActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callFlashover:
                try {
                    viewFlashoverDialog();
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

    private void viewFlashoverDialog() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();


        // Heat Release
        jsonObject.accumulate(FlameHeightModel.Q, ((EditText) findViewById(R.id.TextHeatRelease)).getText().toString());
        Spinner spinnerHeatRelease = (Spinner) findViewById(R.id.spinnerHeatRelease);
        jsonObject.accumulate(FlameHeightModel.QMeasure, spinnerHeatRelease.getSelectedItemPosition());

        // Pool Diameter
        jsonObject.accumulate(FlameHeightModel.D, ((EditText) findViewById(R.id.TextDiameter)).getText().toString());
        Spinner spinnerDiameter = (Spinner) findViewById(R.id.spinnerDiameter);
        jsonObject.accumulate(FlameHeightModel.DMeasure, spinnerDiameter.getSelectedItemPosition());

        final FlameHeightModel response =
                jsonHttpClient.Post(ServiceUrl.FLAMEHEIGHT, jsonObject, FlameHeightModel.class);

        final Dialog dialog = new Dialog(FlameHeightActivity.this);

        dialog.setContentView(R.layout.flameheight_dialog_layout);

        dialog.setTitle("Flame Height");

        ((TextView) dialog.findViewById(R.id.txtLMeters)).setText(response.LMeters);

       ((TextView) dialog.findViewById(R.id.txtLInches)).setText(response.LInches);

        Button button = (Button) dialog.findViewById(R.id.send_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callPatriceValues() {
        TextHeatRelease.setText("1126");
        TextDiameter.setText("1.1283");

        spinnerHeatRelease.setSelection(1);
        spinnerDiameter.setSelection(3);
    }

    private void callClear() {
        TextHeatRelease.setText("");
        TextDiameter.setText("");

        spinnerHeatRelease.setSelection(1);
        spinnerDiameter.setSelection(3);
    }

    public boolean validate() {
        boolean valid = true;
        return valid;
    }

    public void onLoginFailed() {
    }

}
