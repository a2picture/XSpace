package com.xspace.net;

import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * <一句话功能简述> <网络请求>
 *
 * @author jixiongxu
 * @version [版本号, 2017/12/15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class NetUtils
{
    public final static int REQUEST_PAGEMODULE_OK = 0X0000004;

    public final static int REQUEST_PAGEMODULE_FAIL = REQUEST_PAGEMODULE_OK + 1;

    public final static int REQUEST_CATAGORY_OK = REQUEST_PAGEMODULE_FAIL + 1;

    public final static int REQUEST_CATAGORY_FAIL = REQUEST_CATAGORY_OK + 1;

    public static void getAsynPageModulekHttp(final Handler handler, String url)
    {
        if (url == null || "".equals(url) || handler == null)
        {
            return;
        }
        final Message msg = handler.obtainMessage();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                msg.what = REQUEST_PAGEMODULE_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(final Response response) throws IOException
            {
                msg.what = REQUEST_PAGEMODULE_OK;
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

    public static void getAsynCatagorykHttp(final Handler handler, String url)
    {
        if (url == null || "".equals(url) || handler == null)
        {
            return;
        }
        final Message msg = handler.obtainMessage();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                msg.what = REQUEST_CATAGORY_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(final Response response) throws IOException
            {
                msg.what = REQUEST_CATAGORY_OK;
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
    }


}
