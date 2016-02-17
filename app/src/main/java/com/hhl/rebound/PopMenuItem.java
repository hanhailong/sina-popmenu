package com.hhl.rebound;

import android.graphics.drawable.Drawable;

/**
 * Created by HanHailong on 16/2/17.
 */
public class PopMenuItem {

    private String title;
    private Drawable drawable;

    public PopMenuItem(String title, Drawable drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
