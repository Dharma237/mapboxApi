package com.pcs.mapboxapi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Dharma Sai on 31/12/15.
 */
public class ImageUtils {

    /***
     * Changes color of image
     *
     * @param imageView
     * @param colorOfImage
     */
    public void changeColorOfImage(final ImageView imageView, final String colorOfImage) {
        //The color u want
        int color = Color.parseColor(colorOfImage);
        imageView.setColorFilter(color);
    }

    /****
     * resizes drawable based on width and height
     * @param context
     * @param image
     * @param width
     * @param height
     * @return Drawable
     */
    public Drawable resizeDrawable(Context context, Drawable image, int width, int height) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return new BitmapDrawable(context.getResources(), bitmapResized);
    }

    /****
     * load Bitmap from view
     * @param v
     * @return Bitmap from view
     */
    public Bitmap loadBitmapFromView(View v) {

        v.setDrawingCacheEnabled(true);
        v.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        // clear drawing cache
        v.setDrawingCacheEnabled(false);

        return b;
    }

}