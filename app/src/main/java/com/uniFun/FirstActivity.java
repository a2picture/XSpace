package com.uniFun;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import abc.abc.abc.AdManager;
import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.sp.SplashViewSettings;
import abc.abc.abc.nm.sp.SpotManager;
import abc.abc.abc.nm.sp.SpotRequestListener;

public class FirstActivity extends Activity
{
    protected static final String TAG = "youmi_ad";

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first);
        mContext = this;
        runApp();
    }

    private void runApp()
    {
        // 初始化SDK
        AdManager.getInstance(mContext).init("5b24f43745cdcfc6", "4409d16c1039e086", true);
        preloadAd();
        setupSplashAd();
    }

    private void preloadAd()
    {
        SpotManager.getInstance(mContext).requestSpot(new SpotRequestListener()
        {
            @Override
            public void onRequestSuccess()
            {
                setupSplashAd();
            }

            @Override
            public void onRequestFailed(int errorCode)
            {
                switch (errorCode)
                {
                    case ErrorCode.NON_NETWORK:
                        Log.d(TAG, "网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        Log.d(TAG, "暂无视频广告");
                        break;
                    default:
                        Log.d(TAG, "请稍后再试");
                        break;
                }
            }
        });
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd()
    {
        final RelativeLayout splashLayout = findViewById(R.id.ad_view);
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        splashViewSettings.setTargetClass(MainActivity.class);
        splashViewSettings.setSplashViewContainer(splashLayout);
        SpotManager.getInstance(mContext).showSplash(mContext, splashViewSettings, null);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SpotManager.getInstance(mContext).onDestroy();
    }
}
