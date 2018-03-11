package com.teamlanka.firecalc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.models.CardViewDataModel;
import com.teamlanka.firecalc.helper.Recycle_View_Helper;

import java.util.ArrayList;

public class Product_List_Activity extends AppCompatActivity {
    public static Context context;
    private Recycle_View_Helper product_list_recycle_view_helper;
    public static View.OnClickListener OnProductListViewItemClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list_);
        context = this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        InitRecycleView();
    }

    private void InitRecycleView() {

        OnProductListViewItemClickListener = new MyCardViewOnClickListener(this);

        ArrayList<CardViewDataModel> data = new ArrayList<>();

        product_list_recycle_view_helper = new Recycle_View_Helper(data, (RecyclerView) findViewById(R.id.product_list_recycle_view), new GridLayoutManager(context, 2), 4);
    }

    public static void StartIntent(Context context)
    {
        Intent intent = new Intent(context, Product_List_Activity.class);
        context.startActivity(intent);
    }
    private static class MyCardViewOnClickListener implements View.OnClickListener {

        private final Product_List_Activity context;

        private MyCardViewOnClickListener(Product_List_Activity context) {
            this.context = context;

        }



        @Override
        public void onClick(View v) {
            int selectedItemPosition;
            RecyclerView.ViewHolder viewHolder;
            TextView textView1;
            selectedItemPosition = context.product_list_recycle_view_helper.recyclerView.getChildPosition(v);


        }


    }
}
