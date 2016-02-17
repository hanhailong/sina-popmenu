package com.hhl.rebound;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 弹出菜单
 * Created by HanHailong on 16/2/17.
 */
public class PopMenu {


    /**
     * 默认的列数为4个
     */
    private static final int DEFAULT_COLUMN_COUNT = 3;

    /**
     * 动画时间
     */
    private static final int DEFAULT_DURATION = 300;

    /**
     * 拉力系数
     */
    private static final int DEFAULT_TENSION = 40;
    /**
     * 摩擦力系数
     */
    private static final int DEFAULT_FRICTION = 5;

    /**
     * item之间的间距
     */
    private static final int DEFAULT_PADDING = 40;

    private Activity mActivity;
    private int mColumnCount;
    private List<PopMenuItem> mMenuItems = new ArrayList<>();
    private FrameLayout mAnimateLayout;
    private GridLayout mGridLayout;
    private int mDuration;
    private double mTension;
    private double mFriction;
    private int mPadding;

    private int mScreenWidth;
    private int mScreenHeight;

    private boolean isShowing = false;

    private SpringSystem mSpringSystem;

    {
        mSpringSystem = SpringSystem.create();
    }

    private PopMenu(Builder builder) {
        this.mActivity = builder.activity;
        this.mMenuItems = builder.itemList;

        this.mColumnCount = builder.columnCount;
        this.mDuration = builder.duration;
        this.mTension = builder.tension;
        this.mFriction = builder.friction;
        this.mPadding = builder.padding;

        mScreenWidth = mActivity.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mActivity.getResources().getDisplayMetrics().heightPixels;

        buildAnimateGridLayout();
    }

    /**
     * 显示菜单
     */
    public void show() {
        if (mAnimateLayout != null) {

            if (mAnimateLayout.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) mAnimateLayout.getParent();
                viewGroup.removeView(mAnimateLayout);
            }

            ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
            decorView.addView(mAnimateLayout);

            //ImageView
            ImageView closeIv = new ImageView(mActivity);
            closeIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            closeIv.setImageResource(R.drawable.tabbar_compose_background_icon_close);
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hide();
                }
            });
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            layoutParams.bottomMargin = dp2px(mActivity, 25);
            mAnimateLayout.addView(closeIv, layoutParams);

            //执行显示动画
            showSubMenus(mGridLayout);

            isShowing = true;
        }
    }

    /**
     * 隐藏菜单
     */
    public void hide() {
        //先执行消失的动画
        if (isShowing && mGridLayout != null) {
            hideSubMenus(mGridLayout, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
                    decorView.removeView(mAnimateLayout);
                }
            });
            isShowing = false;
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    /**
     * 构建动画布局
     */
    private void buildAnimateGridLayout() {
        if (mAnimateLayout == null) {

            mAnimateLayout = new FrameLayout(mActivity);

            mGridLayout = new GridLayout(mActivity);
            mGridLayout.setColumnCount(mColumnCount);
            mGridLayout.setBackgroundColor(Color.parseColor("#f0ffffff"));

            int padding = dp2px(mActivity, mPadding);
            int itemWidth = (mScreenWidth - (mColumnCount + 1) * padding) / mColumnCount;

            int rowCount = mMenuItems.size() % mColumnCount == 0 ? mMenuItems.size() / mColumnCount :
                    mMenuItems.size() / mColumnCount + 1;

            int topMargin = (mScreenHeight - (itemWidth + padding) * rowCount + padding) / 2;

            for (int i = 0; i < mMenuItems.size(); i++) {
                ImageView imageView = new ImageView(mActivity);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                PopMenuItem menuItem = mMenuItems.get(i);
                imageView.setImageDrawable(menuItem.getDrawable());

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.width = itemWidth;
                lp.height = itemWidth;
                lp.leftMargin = padding;
                if (i / mColumnCount == 0) {
                    lp.topMargin = topMargin;
                } else {
                    lp.topMargin = padding;
                }

                mGridLayout.addView(imageView, lp);
            }

            mAnimateLayout.addView(mGridLayout);
        }
    }

    /**
     * show sub menus with animates
     *
     * @param viewGroup
     */
    private void showSubMenus(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            animateViewDirection(view, mScreenHeight, 0, mTension, mFriction);
        }
    }

    /**
     * hide sub menus with animates
     *
     * @param viewGroup
     * @param listener
     */
    private void hideSubMenus(ViewGroup viewGroup, final AnimatorListenerAdapter listener) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate().translationY(mScreenHeight).setDuration(mDuration).setListener(listener).start();
        }
    }

    /**
     * 弹簧动画
     *
     * @param v        动画View
     * @param from
     * @param to
     * @param tension  拉力系数
     * @param friction 摩擦力系数
     */
    private void animateViewDirection(final View v, float from, float to, double tension, double friction) {
        Spring spring = mSpringSystem.createSpring();
        spring.setCurrentValue(from);
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                v.setTranslationY((float) spring.getCurrentValue());
            }
        });
        spring.setEndValue(to);
    }

    public static class Builder {

        private Activity activity;
        private int columnCount = DEFAULT_COLUMN_COUNT;
        private List<PopMenuItem> itemList = new ArrayList<>();
        private int duration = DEFAULT_DURATION;
        private double tension = DEFAULT_TENSION;
        private double friction = DEFAULT_FRICTION;
        private int padding = DEFAULT_PADDING;

        public Builder attachToActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder columnCount(int count) {
            this.columnCount = count;
            return this;
        }

        public Builder addMenuItem(PopMenuItem menuItem) {
            this.itemList.add(menuItem);
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder tension(double tension) {
            this.tension = tension;
            return this;
        }

        public Builder friction(double friction) {
            this.friction = friction;
            return this;
        }

        public Builder padding(int padding) {
            this.padding = padding;
            return this;
        }

        public PopMenu build() {
            final PopMenu popMenu = new PopMenu(this);
            return popMenu;
        }
    }

    /**
     * dp 2 px
     *
     * @param context
     * @param dpVal
     * @return
     */
    protected int dp2px(Context context, int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
