package com.teamlanka.firecalc.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.Utils.AndyUtils;
import com.teamlanka.firecalc.Utils.SessionHelper;
import com.teamlanka.firecalc.fragments.Category_Fragment;
import com.teamlanka.firecalc.fragments.Feedback_Fragment;
import com.teamlanka.firecalc.fragments.Unity3d_Fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Category_Fragment.OnFragmentInteractionListener {
    public static Context context;
    public static int mSelectedItem;
    public static BottomNavigationView mBottomNav;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sharedpreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        context = this;

        InitChildControl();

        Init_bottom_Nav();

        selectFragment(mBottomNav.getMenu().getItem(0));

        mBottomNav.getMenu().getItem(0).setChecked(true);
    }

    private void Init_bottom_Nav() {
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        if (checkAvailableSession()) {
            mBottomNav.getMenu().removeItem(R.id.sigup_menu);
            mBottomNav.getMenu().removeItem(R.id.sign_in_menu);
        } else {
            mBottomNav.getMenu().removeItem(R.id.feedback_menu);
            mBottomNav.getMenu().removeItem(R.id.unity3d_menu);
        }
    }

    public void selectFragment(MenuItem item) {

        mSelectedItem = item.getItemId();

        Fragment frag = null;

        for (int i = 0; i < mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        switch (item.getItemId()) {
            case R.id.home_menu:
                frag = Category_Fragment.newInstance();

                break;
            case R.id.sigup_menu:
                SignupActivity.StartIntent(context);
                break;

            case R.id.unity3d_menu:
                frag = Unity3d_Fragment.newInstance();
                break;
            case R.id.feedback_menu:
                frag = Feedback_Fragment.newInstance();

                break;
            case R.id.sign_in_menu:
                LoginActivity.StartIntent(context);
                break;
        }


        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, frag);
            ft.commit();
        }

        updateToolbarText(item.getTitle());

    }

    private void updateToolbarText(CharSequence title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
        }

    }

    protected void InitChildControl() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public static void StartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {

            selectFragment(homeItem);
            mBottomNav.getMenu().getItem(0).setChecked(true);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    public void onFragmentInteraction(Uri uri) {

    }

    private boolean checkAvailableSession() {
        String username = sharedpreferences.getString(SessionHelper.USERNAME, "email");

        if (username.equals("email")) {
            return false;
        }

        return true;
    }
}