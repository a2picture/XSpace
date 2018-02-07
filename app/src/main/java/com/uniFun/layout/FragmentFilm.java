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
import com.uniFun.module.TemplateModule;
import com.uniFun.net.NetUtils;
import com.uniFun.net.VersionUtils;
import com.uniFun.ui.uihelper.TemplateConstant;
import com.uniFun.ui.uihelper.TemplateContainerImpl;
import com.uniFun.utils.NetAddressManager;

import demo.pplive.com.xspace.R;

public class FragmentFilm extends Fragment
{
    private PullToRefreshListView listView;

    private ProgressBar loading;

    private View emptyView;

    private ImageView error;

    private int page = 0;

    private TemplateContainerImpl impl;

    private View rootView;

    private PageModule pageModule;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            listView.onRefreshComplete();
            loading.setVisibility(View.GONE);
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
            rootView = inflater.inflate(R.layout.fragment_find, container, false);
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
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("嘿咻嘿咻");
                refreshView.getLoadingLayoutProxy().setPullLabel("客观、轻点儿");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("讨厌、还在拉");
                page = 0;
                ApplyNetGson(handler, page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("嘿咻嘿咻");
                refreshView.getLoadingLayoutProxy().setPullLabel("客观、轻点儿");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("讨厌、还在拉");
                page++;
                ApplyNetGson(handler, page);
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
                ApplyNetGson(handler, page);
            }
        });
        impl.setListView(listView);
        ApplyNetGson(handler, page);
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
        if (page == 0)
        {
            pageModule = ModuleParser.getPageModules(gson);
        }
        else
        {
            PageModule temp = ModuleParser.getPageModules(gson);
            if (temp.templateModules != null && temp.templateModules.size() > 0)
            {
                pageModule.templateModules.addAll(temp.templateModules);
            }
            else
            {
                if (!pageModule.templateModules.get(pageModule.templateModules.size() - 1).templateId.equals(
                        TemplateConstant.template_end_banner))
                {
                    TemplateModule end = new TemplateModule();
                    end.templateId = TemplateConstant.template_end_banner;
                    end.description = "^_^没有更多了*_*";
                    pageModule.templateModules.add(end);
                }
            }
        }
        if (pageModule == null)
        {
            Toast.makeText(getContext(), "数据配置错误", Toast.LENGTH_SHORT).show();
            showEmpty();
            return;
        }
        impl.startConstruct(pageModule);
    }

    private void ApplyNetGson(final Handler handler, final int p)
    {
        loading.setVisibility(View.VISIBLE);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                NetUtils.getAsynPageModulekHttp(handler, NetAddressManager.root_website + NetAddressManager.film
                        + "?appVer=" + VersionUtils.getAppVersionName(getContext()) + "&page=" + p);
            }
        }).start();
    }
}
