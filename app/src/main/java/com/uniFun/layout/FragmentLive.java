package com.uniFun.layout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.uniFun.module.ModuleParser;
import com.uniFun.module.PageModule;
import com.uniFun.net.NetUtils;
import com.uniFun.net.VersionUtils;
import com.uniFun.ui.uihelper.TemplateContainerImpl;
import com.uniFun.utils.NetAddressManager;

import demo.pplive.com.xspace.R;

public class FragmentLive extends Fragment
{
    private PullToRefreshListView listView;

    private ProgressBar loading;

    private View emptyView;

    private ImageView error;

    private TemplateContainerImpl impl;

    private View rootView;

    private PageModule pageModule;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            loading.setVisibility(View.GONE);
            listView.onRefreshComplete();
            switch (msg.what)
            {
                case NetUtils.REQUEST_PAGEMODULE_OK:
                    show(msg.obj.toString());
                    break;
                default:
                    showEmpty();
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
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_live, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView()
    {
        impl = new TemplateContainerImpl(getContext());
        listView = rootView.findViewById(R.id.pull_to_refresh);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("嘿咻嘿咻");
                refreshView.getLoadingLayoutProxy().setPullLabel("客观、轻点儿");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("讨厌、还在拉");
                ApplyNetGson(handler);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
            }
        });
        emptyView = rootView.findViewById(R.id.empty_view);
        loading = rootView.findViewById(R.id.loading);
        emptyView.setVisibility(View.GONE);
        error = rootView.findViewById(R.id.error);
        error.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ApplyNetGson(handler);
            }
        });
        impl.setListView(listView);
        ApplyNetGson(handler);
    }

    private void showEmpty()
    {
        emptyView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    private void show(String gson)
    {
        emptyView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        pageModule = ModuleParser.getPageModules(gson);
        if (pageModule == null)
        {
            Toast.makeText(getContext(), "数据配置错误", Toast.LENGTH_SHORT).show();
            showEmpty();
            return;
        }
        impl.startConstruct(pageModule);
    }

    private void ApplyNetGson(final Handler handler)
    {
        loading.setVisibility(View.VISIBLE);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                NetUtils.getAsynPageModulekHttp(handler, NetAddressManager.root_website + NetAddressManager.live + "?appVer="
                        + VersionUtils.getAppVersionName(getContext()));
            }
        }).start();
    }
}
