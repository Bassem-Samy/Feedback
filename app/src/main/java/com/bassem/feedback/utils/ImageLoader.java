package com.bassem.feedback.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

/** Helper class that is used to load images
 * it's purpose is to make the code independent of the 3rd party library used to load image, like if we decide to use picasso instead of glide, w will just have to update the usage here and not in every occurrence
 * Created by Bassem Samy on 6/3/2017.
 */

public class ImageLoader {
    public static void loadImage(Context context, String imageUrl, int placeHolderResourceID, ImageView imageView) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Glide.with(weakReference.get()).load(imageUrl).asBitmap().placeholder(placeHolderResourceID).into(imageView);
    }
}
