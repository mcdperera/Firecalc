package com.teamlanka.firecalc.models;

import android.graphics.Bitmap;

import com.teamlanka.firecalc.Utils.ImageHelper;

/**
 * Created by Chaarmal on 2/15/2018.
 */

public class CardViewDataModel {
    private String  main_image;

    private String product_name;

    public CardViewDataModel(String main_image_param,
                             String product_name_param) {

        this.main_image = main_image_param;
        this.product_name = product_name_param;
    }


    public Bitmap SelectedImage;

    public String getProductName() {
        return product_name;
    }

    public Bitmap getImage() {
        return this.SelectedImage;
    }

    public void setImage() {
        this.SelectedImage = ImageHelper.GetImage(this.main_image);
    }
}