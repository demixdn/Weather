package com.example.wheather.view.widget;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Aleksandr on 20.07.2016 in Wheather.
 */
public class TabletDrawerLayout extends DrawerLayout {

    private boolean isTablet = false;

    public TabletDrawerLayout(Context context) {
        super(context);
    }

    public TabletDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabletDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        View drawer = getChildAt(1);
        return !(isTablet && drawer != null && getDrawerLockMode(Gravity.LEFT) == LOCK_MODE_LOCKED_OPEN && ev.getRawX() > drawer.getWidth()) && super.onInterceptTouchEvent(ev);
    }
}
