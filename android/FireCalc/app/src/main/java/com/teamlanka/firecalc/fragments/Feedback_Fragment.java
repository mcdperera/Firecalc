package com.teamlanka.firecalc.fragments;

import android.content.Context;
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

public class Feedback_Fragment extends Fragment {

    public static Feedback_Fragment feedback_fragment;
    public static Timer timer = new Timer();
    public static View main_view = null;

    public static Context feedack_context;
    private Button submitButton;
    private TextView titleEditText, descEditText;
    SharedPreferences sharedpreferences;

    public Feedback_Fragment() {
    }

    public static Fragment newInstance() {
        Feedback_Fragment fragment = new Feedback_Fragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

             feedback_fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedpreferences = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity());

        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        main_view = view;

        //initializePullToRefresh(view);

       // if (this.checkAvailableSession()) {

        titleEditText = (EditText) view.findViewById(R.id.feedbackTitleEditText);
        descEditText = (EditText) view.findViewById(R.id.feedbackDescEditText);

        submitButton = (Button) view.findViewById(R.id.feedbackSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        submitFeedbackDetails();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

        return view;
    }

    private void submitFeedbackDetails() throws JSONException {

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate(FeedbackModel.Title, titleEditText.getText().toString());
        jsonObject.accumulate(FeedbackModel.Description, descEditText.getText().toString());
        jsonObject.accumulate(FeedbackModel.UserId, sharedpreferences.getString(SessionHelper.USERNAME, "user1"));


        final FeedbackModel response =
                jsonHttpClient.Post(ServiceUrl.Feedback, jsonObject, FeedbackModel.class);

        if (response != null) {
            AndyUtils.showToast("Thank you for your feedback.", MainActivity.context);
            clearEditText();

        } else {
           AndyUtils.showToast("Something went wrong please try again later.", MainActivity.context);
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
