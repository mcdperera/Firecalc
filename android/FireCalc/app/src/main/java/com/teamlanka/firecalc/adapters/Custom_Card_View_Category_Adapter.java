package com.teamlanka.firecalc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.activities.Product_List_Activity;
import com.teamlanka.firecalc.models.CardViewDataModel;
import com.teamlanka.firecalc.fragments.Category_Fragment;

import java.util.ArrayList;

/**
 * Created by Charmal on 2/21/2018.
 */

public class Custom_Card_View_Category_Adapter extends RecyclerView.Adapter<Custom_Card_View_Category_Adapter.MyViewHolder> {

    private ArrayList<CardViewDataModel> dataSet;
    int recycle_view_number;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMain;
        TextView category_name_textview;



        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageViewMain = (ImageView) itemView.findViewById(R.id.card_view_image);


            this.category_name_textview = (TextView) itemView.findViewById(R.id.card_view_product_item_text2);

        }
    }

    public Custom_Card_View_Category_Adapter(ArrayList<CardViewDataModel> data, int i) {
        this.dataSet = data;
        recycle_view_number = i;
    }

    @Override
    public Custom_Card_View_Category_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_category_item, parent, false);

        switch (recycle_view_number)
        {

            case 4:
                view.setOnClickListener(Product_List_Activity.OnProductListViewItemClickListener);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                view.setLayoutParams(layoutParams);
                break;
            case 5:
                view.setOnClickListener(Category_Fragment.OnProductListViewItemClickListener);
                ViewGroup.LayoutParams layoutParams1 = view.getLayoutParams();
                layoutParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;

                view.setLayoutParams(layoutParams1);
                break;
        }


        Custom_Card_View_Category_Adapter.MyViewHolder myViewHolder = new Custom_Card_View_Category_Adapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final Custom_Card_View_Category_Adapter.MyViewHolder holder, final int listPosition) {

        ImageView imageViewMain = holder.imageViewMain;


        TextView category_name_textview = holder.category_name_textview;

        category_name_textview.setText(dataSet.get(listPosition).getProductName());
        dataSet.get(listPosition).setImage();

        imageViewMain.setImageBitmap(dataSet.get(listPosition).getImage());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}