package com.teamlanka.firecalc.helper;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import com.teamlanka.firecalc.adapters.Custom_Card_View_Category_Adapter;
import com.teamlanka.firecalc.adapters.Custom_Card_View_Product_Adapter;
import com.teamlanka.firecalc.models.CardViewDataModel;

import java.util.ArrayList;



public class Recycle_View_Helper {
    public  RecyclerView recyclerView;
    public  ArrayList<CardViewDataModel> data;
    public Recycle_View_Helper(ArrayList<CardViewDataModel> data_param, RecyclerView recyclerView_param
            , RecyclerView.LayoutManager layoutManager_param, int i)
    {
        data = data_param;
        recyclerView = recyclerView_param;

        recyclerView.setLayoutManager(layoutManager_param);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(i == 5)
        {
            RecyclerView.Adapter adapter = new Custom_Card_View_Category_Adapter(data, i);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            RecyclerView.Adapter adapter = new Custom_Card_View_Product_Adapter(data, i);
            recyclerView.setAdapter(adapter);
        }

    }

}
