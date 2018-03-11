package com.teamlanka.firecalc.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.activities.MainActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;



public class Switch_Image_Helper {
    public int cur_image_switch_no = 0;
    public int image_switch_count;
    public ImageSwitcher imageSwitcher;
    public ArrayList<String> switch_images_list = new ArrayList<>();
    public ArrayList<Bitmap> swich_bitmap_list = new ArrayList<>();

    public void switch_image_next() {
        Animation in_next = AnimationUtils.loadAnimation(MainActivity.context, android.R.anim.slide_in_left);
        Animation out_next = AnimationUtils.loadAnimation(MainActivity.context, android.R.anim.slide_out_right);
        image_switch_count = switch_images_list.size();
        imageSwitcher.setInAnimation(in_next);
        imageSwitcher.setOutAnimation(out_next);
        loadImage();
        cur_image_switch_no = ++cur_image_switch_no % image_switch_count;

    }
    public void addImage(String filename)
    {

    }

    private void loadImage() {
        if(swich_bitmap_list.size() < switch_images_list.size())
        {
            swich_bitmap_list.add(getImageBitmap(switch_images_list.get(cur_image_switch_no)));
        }

        imageSwitcher.setImageDrawable(new BitmapDrawable(MainActivity.context.getResources(),
                swich_bitmap_list.get(cur_image_switch_no)));
    }
    public Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            bm = BitmapFactory.decodeStream(bis, null, options);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }
    public void switch_image_previous() {
        Animation in_previous = AnimationUtils.loadAnimation(MainActivity.context, R.anim.slide_in_right);
        Animation out_previous = AnimationUtils.loadAnimation(MainActivity.context, R.anim.slide_out_left);;

        imageSwitcher.setInAnimation(in_previous);
        imageSwitcher.setOutAnimation(out_previous);
        loadImage();
        if(cur_image_switch_no == 0)
            cur_image_switch_no = image_switch_count - 1;
        else
            cur_image_switch_no --;

    }

}
