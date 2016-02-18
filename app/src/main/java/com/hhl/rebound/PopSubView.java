package com.hhl.rebound;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 弹出菜单SubView 上部图片 底部文字
 * Created by HanHailong on 16/2/18.
 */
public class PopSubView extends LinearLayout {

    private static final float factor = 1.2f;

    private ImageView icon;
    private TextView textView;

    public PopSubView(Context context) {
        this(context, null);
    }

    public PopSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        icon = new ImageView(context);
        icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(icon, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        textView = new TextView(context);
        LayoutParams tvLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLp.topMargin = 10;
        addView(textView, tvLp);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scaleViewAnimation(PopSubView.this, factor);
                        break;
                    case MotionEvent.ACTION_UP:
                        scaleViewAnimation(PopSubView.this, 1);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 赋值
     *
     * @param popMenuItem
     */
    public void setPopMenuItem(PopMenuItem popMenuItem) {
        if (popMenuItem == null) return;
        icon.setImageDrawable(popMenuItem.getDrawable());
        textView.setText(popMenuItem.getTitle());
    }

    /**
     * 缩放动画
     *
     * @param value
     */
    private void scaleViewAnimation(View view, float value) {
        view.animate().scaleX(value).scaleY(value).setDuration(80).start();
    }
}
