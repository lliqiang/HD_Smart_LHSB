package com.hengda.frame.tileview.bitmaploader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.qozix.tileview.graphics.BitmapProvider;
import com.qozix.tileview.tiles.Tile;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * @author Mike Dunn, 2/19/16.
 */
public class HttpBitmapProviderWithPicasso implements BitmapProvider {
    public Bitmap getBitmap(Tile tile, Context context) {
        Object data = tile.getData();
        if (data instanceof String) {
            String unformattedFileName = (String) tile.getData();
            String formattedFileName = String.format(unformattedFileName, tile.getColumn(), tile.getRow());
            try {
                Log.i("111", "getBitmap: "+formattedFileName );
                return Picasso.with(context).load(formattedFileName).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).get();
            } catch (Throwable t) {
            }
        }else {
            Log.i("111", "getBitmap: null" );
        }
        return null;
    }
}