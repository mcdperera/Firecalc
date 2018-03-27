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
import com.teamlanka.firecalc.models.RadiationPoolModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RadiationPoolActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerDiameter, spinnerDistance;

    EditText diameterText, distanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_radiationpool);

        initView();
    }

    private void initView() {

        spinnerDiameter = (Spinner) findViewById(R.id.spinnerDiameter);
        spinnerDiameter.setSelection(1);

        spinnerDistance = (Spinner) findViewById(R.id.spinnerDistance);
        spinnerDistance.setSelection(1);

        Button callConduction = (Button) findViewById(R.id.callCalculateRadiationPool);
        callConduction.setOnClickListener(this);

        Button buttonPatrice = (Button) findViewById(R.id.buttonPatrice);
        buttonPatrice.setOnClickListener(this);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        diameterText = (EditText) findViewById(R.id.diameterText);
        distanceText = (EditText) findViewById(R.id.distanceText);
    }

    public static void startIntent(Context product_item_activity) {
        Intent intent = new Intent(product_item_activity, RadiationPoolActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        product_item_activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callCalculateRadiationPool:
                try {
                    calculateRadiationPool();
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

    private void calculateRadiationPool() throws JSONException {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        // Diameter
        jsonObject.accumulate(RadiationPoolModel.Diameter, ((EditText) findViewById(R.id.diameterText)).getText().toString());
        jsonObject.accumulate(RadiationPoolModel.DiameterMeasure, spinnerDiameter.getSelectedItemPosition());

        // Distance
        jsonObject.accumulate(RadiationPoolModel.Distance, ((EditText) findViewById(R.id.distanceText)).getText().toString());
        jsonObject.accumulate(RadiationPoolModel.DistanceMeasure, spinnerDistance.getSelectedItemPosition());

        final RadiationPoolModel response =
                jsonHttpClient.Post(ServiceUrl.RADIATIONPOOL, jsonObject, RadiationPoolModel.class);

        final Dialog dialog = new Dialog(RadiationPoolActivity.this);

        dialog.setContentView(R.layout.radiationpool_dialog_layout);

        dialog.setTitle("Radiation Pool Fire");

        ((TextView) dialog.findViewById(R.id.heatFluxText)).setText(response.Heatflux);
        ((TextView) dialog.findViewById(R.id.heatFluxBtuText)).setText(response.HeatfluxBtu);
        ((TextView) dialog.findViewById(R.id.checkValidityText)).setText(response.Vaidity);

        Button button = (Button) dialog.findViewById(R.id.send_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callPatriceValues() {
        diameterText.setText("6");
        distanceText.setText("12");

        spinnerDiameter.setSelection(1);
        spinnerDistance.setSelection(1);
    }

    private void callClear() {

        diameterText.setText("");
        distanceText.setText("");

        spinnerDiameter.setSelection(1);
        spinnerDistance.setSelection(1);
    }

    public boolean validate() {
        return true;
    }

    public void onLoginFailed() {
    }


}
