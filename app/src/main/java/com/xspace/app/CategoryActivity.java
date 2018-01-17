package com.xspace.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xspace.module.PageModule;
import com.xspace.net.NetUtils;
import com.xspace.ui.uihelper.TemplateContainerImpl;

import demo.pplive.com.xspace.R;

public class CategoryActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView back;

    private TextView title;

    private PullToRefreshListView listView;

    private TemplateContainerImpl impl;

    private PageModule pageModule;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case NetUtils.REQUEST_PAGEMODULE_OK:
                    show(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        back = findViewById(R.id.img_back);
        title = findViewById(R.id.txt_detail);
        listView = findViewById(R.id.pull_to_refresh);
        initView();
    }

    private void initView()
    {
        back.setOnClickListener(this);
        impl = new TemplateContainerImpl(context);
        impl.setListView(listView);
        ApplyNetGson(handler, "http://www.pptv.com");
    }

    private void ApplyNetGson(final Handler handler, final String url)
    {
        if (url == null || "".equals(url))
        {
            return;
        }
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                NetUtils.getAsynPageModulekHttp(handler, url);
            }
        }).start();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    private void show(String gson)
    {
        Gson g = new Gson();
        pageModule = g.fromJson(gson, PageModule.class);
        impl.startConstruct(pageModule);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }
}
