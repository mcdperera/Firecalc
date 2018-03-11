package com.teamlanka.firecalc.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.Utils.AndyUtils;
import com.teamlanka.firecalc.Utils.JSONHttpClient;
import com.teamlanka.firecalc.Utils.SessionHelper;
import com.teamlanka.firecalc.activities.MainActivity;
import com.teamlanka.firecalc.constants.ServiceUrl;
import com.teamlanka.firecalc.models.FeedbackModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

public class Unity3d_Fragment extends Fragment {

    public static Unity3d_Fragment unity3d_fragment;
    public static Timer timer = new Timer();
    public static View main_view = null;

    public static Context feedack_context;
    private Button submitButton;
    private TextView titleEditText, descEditText;
    SharedPreferences sharedpreferences;

    public Unity3d_Fragment() {
    }

    public static Fragment newInstance() {
        Unity3d_Fragment fragment = new Unity3d_Fragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unity3d_fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedpreferences = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity());

        View view = inflater.inflate(R.layout.fragment_unity3d, container, false);

        main_view = view;

        //initializePullToRefresh(view);

        // if (this.checkAvailableSession()) {

        titleEditText = (EditText) view.findViewById(R.id.feedbackTitleEditText);
        descEditText = (EditText) view.findViewById(R.id.feedbackDescEditText);

        submitButton = (Button) view.findViewById(R.id.unity3dSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    launchApp();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    private void launchApp() throws JSONException {

        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.teamlanka.table");

        launchIntent.putExtra("EXTRA_SESSION_ID", 1);

        if (launchIntent != null) {
            startActivity(launchIntent);
        }

    }

    private void clearEditText() {
        titleEditText.setText("");
        descEditText.setText("");
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

}
