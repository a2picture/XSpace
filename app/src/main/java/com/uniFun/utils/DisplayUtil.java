package com.uniFun.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class DisplayUtil
{

    public static float getDensity(Context context)
    {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int dip2px(Context context, double dipValue)
    {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public static int px2dip(Context context, double pxValue)
    {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    /**
     * 长的为宽度 <功能详细描述>
     * 
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int screenHeightPx(Context context)
    {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? widthPx : heightPx;
    }

    /**
     * 小的为高度
     * 
     * @param context
     * @return
     */
    public static int screenWidthPx(Context context)
    {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? heightPx : widthPx;
    }

    public static int sp2px(float spValue, float fontScale)
    {
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * 
     * @param activity Activity
     * @return Width
     * @see [类、类#方法、类#成员]
     */
    public static int getDisplayWidth(final Activity activity)
    {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static void setStatusBarColor(Activity activity, int statusColor)
    {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        {
            // 取消状态栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置状态栏颜色
            window.setStatusBarColor(statusColor);
            // 设置系统状态栏处于可见状态
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            // 让view不根据系统窗口来调整自己的布局
            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null)
            {
                ViewCompat.setFitsSystemWindows(mChildView, false);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

}
