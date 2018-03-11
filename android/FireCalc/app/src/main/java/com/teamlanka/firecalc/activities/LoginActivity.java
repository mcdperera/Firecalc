package com.teamlanka.firecalc.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.Utils.AndyUtils;
import com.teamlanka.firecalc.Utils.JSONHttpClient;
import com.teamlanka.firecalc.Utils.SessionHelper;
import com.teamlanka.firecalc.constants.ServiceUrl;
import com.teamlanka.firecalc.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    public static Context log_in_context;
    public static int LOG_IN_SUCCESS = 0;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    public static void StartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        log_in_context = this;
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    try {
                        attemptLogin();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        sharedpreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    private void attemptLogin() throws JSONException {

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        JSONObject jsonObject = new JSONObject();

        String email = mEmailView.getText().toString();
        jsonObject.accumulate(UserModel.Email, mEmailView.getText().toString());
        jsonObject.accumulate(UserModel.PASSWORD, mPasswordView.getText().toString());

        final UserModel response =
                jsonHttpClient.Post(ServiceUrl.Signin, jsonObject, UserModel.class);

        if (response != null) {
            setSessionAndCallMainActivity(email);

        } else {
            AndyUtils.showToast("Either username or password is wrong.", log_in_context);
        }

    }

    private void setSessionAndCallMainActivity(String username) {
        setSharedPreferences(username);

        Intent homepage = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(homepage);
    }

    private void setSharedPreferences(String username) {

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.clear();
        editor.commit();

        editor.putString(SessionHelper.USERNAME, username);
        editor.commit();

    }

}

