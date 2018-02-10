package com.uniFun;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.crashreport.CrashReport;

public class MainApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Fresco.initialize(this);
        CrashReport.initCrashReport(getApplicationContext(), "9068dc4f25", false);
    }
}
