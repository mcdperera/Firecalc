package com.teamlanka.firecalc.adapters;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.Utils.AndyUtils;
import com.teamlanka.firecalc.activities.MainActivity;
import com.teamlanka.firecalc.models.CardViewDataModel;
import com.teamlanka.firecalc.fragments.Category_Fragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Charmal on 2/15/2018.
 */

public class Custom_Card_View_Product_Adapter extends RecyclerView.Adapter<Custom_Card_View_Product_Adapter.MyViewHolder> {

    private ArrayList<CardViewDataModel> dataSet;
    int recycle_view_number;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMain;
        TextView product_title_text;
        TextView real_price;
        TextView previous_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageViewMain = (ImageView) itemView.findViewById(R.id.card_view_image);
            this.real_price = (TextView) itemView.findViewById(R.id.product_real_price);
            this.previous_price = (TextView) itemView.findViewById(R.id.card_view_product_item_text3);
            this.product_title_text = (TextView) itemView.findViewById(R.id.card_view_product_item_text2);


        }
    }

    public Custom_Card_View_Product_Adapter(ArrayList<CardViewDataModel> data, int i) {
        this.dataSet = data;
        recycle_view_number = i;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_product_item, parent, false);

        AndyUtils.showToast("onCreateViewHolder" , MainActivity.context);

        switch (recycle_view_number)
        {

//            case 4:
//                view.setOnClickListener(Product_List_Activity.OnProductListViewItemClickListener);
//                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                view.setLayoutParams(layoutParams);
//                break;
            case 5:
                view.setOnClickListener(Category_Fragment.OnProductListViewItemClickListener);
                ViewGroup.LayoutParams layoutParams1 = view.getLayoutParams();
                layoutParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;

                view.setLayoutParams(layoutParams1);
                break;
        }


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        ImageView imageViewMain = holder.imageViewMain;
        TextView product_title_text = holder.product_title_text;
        TextView real_price = holder.real_price;
        TextView previous_price = holder.previous_price;
        previous_price.setPaintFlags(previous_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        product_title_text.setText(dataSet.get(listPosition).getProductName());
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        dataSet.get(listPosition).setImage();
        imageViewMain.setImageBitmap(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}