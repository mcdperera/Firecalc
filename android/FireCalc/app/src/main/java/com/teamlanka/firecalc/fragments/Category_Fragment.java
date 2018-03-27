package com.teamlanka.firecalc.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.Utils.AndyUtils;
import com.teamlanka.firecalc.Utils.DelayUtils;
import com.teamlanka.firecalc.Utils.JSONHttpClient;
import com.teamlanka.firecalc.activities.ConductionActivity;
import com.teamlanka.firecalc.activities.FlameHeightActivity;
import com.teamlanka.firecalc.activities.FlashoverActivity;
import com.teamlanka.firecalc.activities.GasAmountActivity;
import com.teamlanka.firecalc.activities.GasConcActivity;
import com.teamlanka.firecalc.activities.GaslayerActivity;
import com.teamlanka.firecalc.activities.HrrActivity;
import com.teamlanka.firecalc.activities.MainActivity;
import com.teamlanka.firecalc.activities.OpenPipeActivity;
import com.teamlanka.firecalc.activities.RadiationPoolActivity;
import com.teamlanka.firecalc.activities.T2FireActivity;
import com.teamlanka.firecalc.constants.ServiceUrl;
import com.teamlanka.firecalc.models.CardViewDataModel;
import com.teamlanka.firecalc.helper.Recycle_View_Helper;
import com.teamlanka.firecalc.helper.Switch_Image_Helper;
import com.teamlanka.firecalc.models.MenuModel;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Category_Fragment extends Fragment {

    public static Category_Fragment category_fragment;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static View.OnClickListener OnProductListViewItemClickListener;
    public static Recycle_View_Helper product_category_recycle_view_helper;
    public static Timer timer = new Timer();
    private OnFragmentInteractionListener mListener;
    private Switch_Image_Helper switch_image_helper = new Switch_Image_Helper();
    public static View main_view = null;
    public static MenuModel menuArray;

    public Category_Fragment() {
    }

    public static Fragment newInstance() {
        Category_Fragment fragment = new Category_Fragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        category_fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        main_view = view;
        initializePullToRefresh(view);
        InitProductList(view);
        return view;
    }

    private void InitProductList(View view) {

        getProductList_Data2();
    }

    public void getProductList_Data2() {

        JSONHttpClient jsonHttpClient = new JSONHttpClient();

        List<NameValuePair> args = new ArrayList<NameValuePair>();

        menuArray =
                jsonHttpClient.Get(ServiceUrl.GET_CATEGORY_LIST, args, MenuModel.class);

        InitRecycleView(main_view);
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

    private final Handler toastHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch_image_helper.switch_image_next();

        }
    };

    private void InitRecycleView(View view) {

        OnProductListViewItemClickListener = new MyCardViewOnClickListener(MainActivity.context);

        ArrayList<CardViewDataModel> data = new ArrayList<>();

        for (int i = 0; i < menuArray.getCategoryValueModelList().length; i++) {

            data.add(new CardViewDataModel(
                    menuArray.getCategoryValueModelList()[i].image,
                    menuArray.getCategoryValueModelList()[i].title
            ));

        }

        product_category_recycle_view_helper = new Recycle_View_Helper(data, (RecyclerView)

                view.findViewById(R.id.cagetory_recycle_view), new GridLayoutManager(MainActivity.context, 4), 5);
    }


    private void initializePullToRefresh(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_refresh_category);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        DelayUtils.delay(3, new DelayUtils.DelayCallback() {
                            @Override
                            public void afterDelay() {

                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }
        );
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private static class MyCardViewOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyCardViewOnClickListener(Context context) {
            this.context = context;

        }

        @Override
        public void onClick(View v) {
            int selectedItemPosition;

            selectedItemPosition = product_category_recycle_view_helper.recyclerView.getChildPosition(v);

            if (selectedItemPosition == 0) {
                FlashoverActivity.startIntent(MainActivity.context);
            } else if (selectedItemPosition == 1) {
                GaslayerActivity.startIntent(MainActivity.context);
            } else if (selectedItemPosition == 2) {
                ConductionActivity.startIntent(MainActivity.context);
            }
            else if (selectedItemPosition == 4) {
                RadiationPoolActivity.startIntent(MainActivity.context);
            }
            else if (selectedItemPosition == 6) {
                HrrActivity.startIntent(MainActivity.context);
            }
            else if (selectedItemPosition == 7) {
                T2FireActivity.startIntent(MainActivity.context);
            }
            else if (selectedItemPosition == 8) {
                FlameHeightActivity.startIntent(MainActivity.context);
            }else if (selectedItemPosition == 9) {
                GasAmountActivity.startIntent(MainActivity.context);
            }
            else if (selectedItemPosition == 10) {
                OpenPipeActivity.startIntent(MainActivity.context);
            }
            else if (selectedItemPosition == 11) {
                GasConcActivity.startIntent(MainActivity.context);
            }
            else
            {
                AndyUtils.showToast("Not developed yet!", MainActivity.context);
            }
        }


    }
}
