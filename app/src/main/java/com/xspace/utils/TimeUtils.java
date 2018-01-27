package com.xspace.utils;



public class TimeUtils
{
    private static int ONEHOUR = 3600000;

    private static int ONEMIN = ONEHOUR / 60;

    private static int ONSEC = ONEMIN / 60;

    public static String getPercent(long msc)
    {
        int mscTem = (int) msc;
        int hour = mscTem / ONEHOUR;
        mscTem = mscTem - hour * ONEHOUR;
        int mini = mscTem / ONEMIN;
        mscTem = mscTem - mini * ONEMIN;
        int sec = mscTem / ONSEC;
        return hour + ":" + mini + ":" + sec;
    }

}
