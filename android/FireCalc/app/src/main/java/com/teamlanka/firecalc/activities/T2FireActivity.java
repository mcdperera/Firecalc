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
import com.teamlanka.firecalc.models.T2FireModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class T2FireActivity extends AppCompatActivity implements View.OnClickListener {
    EditText t1EditText, hrrEditText, timeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t2fire);

        initView();
    }

    private void initView() {

        Button callConduction = (Button) findViewById(R.id.calculateT2Fire);
        callConduction.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        t1EditText = (EditText) findViewById(R.id.t1EditText);
        hrrEditText = (EditText) findViewById(R.id.hrrEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);

    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent(product_item_activity, T2FireActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calculateT2Fire:
                try {
                    calcuateT2Fire();
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

    private void calcuateT2Fire() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        // t1
        jsonObject.accumulate(T2FireModel.t1, ((EditText) findViewById(R.id.t1EditText)).getText().toString());

        // hrr
        jsonObject.accumulate(T2FireModel.hrr, ((EditText) findViewById(R.id.hrrEditText)).getText().toString());

        // time
        jsonObject.accumulate(T2FireModel.time, ((EditText) findViewById(R.id.timeEditText)).getText().toString());

        final T2FireModel response =
                jsonHttpClient.Post(ServiceUrl.T2FIRE, jsonObject, T2FireModel.class);

        final Dialog dialog = new Dialog(T2FireActivity.this);

        dialog.setContentView(R.layout.t2fire_dialog_layout);

        dialog.setTitle("T2 Fire");

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
        t1EditText.setText("200");
        hrrEditText.setText("1000");
        timeEditText.setText("20");
    }

    private void callClear() {

        t1EditText.setText("");
        hrrEditText.setText("");
        timeEditText.setText("");
    }

    public boolean validate() {
        return true;
    }

    public void onLoginFailed() {
    }

}
