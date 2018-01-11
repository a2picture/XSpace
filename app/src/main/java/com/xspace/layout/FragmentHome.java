package com.xspace.layout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xspace.module.PageModule;
import com.xspace.net.NetUtils;
import com.xspace.ui.uihelper.TemplateContainerImpl;

import demo.pplive.com.xspace.R;

public class FragmentHome extends Fragment
{
    private PullToRefreshListView listView;

    private TemplateContainerImpl impl;

    private View rootView;

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
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return rootView;
    }

    private void initView()
    {
        impl = new TemplateContainerImpl(getContext());
        listView = rootView.findViewById(R.id.pull_to_refresh);
        impl.setListView(listView);
        ApplyNetGson(handler);
    }

    private void show(String gson)
    {
        Gson g = new Gson();
        pageModule = g.fromJson(gson, PageModule.class);
        impl.startConstruct(pageModule);
    }

    private void ApplyNetGson(final Handler handler)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                NetUtils.getAsynPageModulekHttp(handler, "http://www.gitkub.com");
            }
        }).start();
    }
}
