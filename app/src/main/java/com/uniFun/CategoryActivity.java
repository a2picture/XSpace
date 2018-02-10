package com.uniFun;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class CategoryActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView back;

    private ProgressBar loading;

    private TextView title;

    private View emptyView;

    private String url;

    private int page = 0;

    private PullToRefreshListView listView;

    private ImageView error;

    private TemplateContainerImpl impl;

    private PageModule pageModule;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            listView.onRefreshComplete();
            switch (msg.what)
            {
                case NetUtils.REQUEST_PAGEMODULE_OK:
                    show(msg.obj.toString());
                    break;
                default:
                    Toast.makeText(context, "数据请求失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        back = findViewById(R.id.img_back);
        listView = findViewById(R.id.pull_to_refresh);
        loading = findViewById(R.id.loading);
        emptyView = findViewById(R.id.empty_view);
        title = findViewById(R.id.title_txt);
        if (!"".equals(module.title))
        {
            title.setText(module.title);
        }
        error = findViewById(R.id.error);
        initView();
    }

    private void initView()
    {
        error.setOnClickListener(this);
        back.setOnClickListener(this);
        impl = new TemplateContainerImpl(context);
        impl.setListView(listView);
        url = NetAddressManager.root_website + NetAddressManager.category + "?categoryId=" + module.vid + "&page="
                + page + "&appVer=" + VersionUtils.getAppVersionName(context);
        ApplyNetGson(handler, url);
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
                url = NetAddressManager.root_website + NetAddressManager.category + "?categoryId=" + module.vid
                        + "&page=" + page + "&appVer=" + VersionUtils.getAppVersionName(context);
                ApplyNetGson(handler, url);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("嘿咻嘿咻");
                refreshView.getLoadingLayoutProxy().setPullLabel("客观、轻点儿");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("讨厌、还在拉");
                page++;
                url = NetAddressManager.root_website + NetAddressManager.category + "?categoryId=" + module.vid
                        + "&page=" + page + "&appVer=" + VersionUtils.getAppVersionName(context);
                ApplyNetGson(handler, url);
            }
        });
    }

    private void ApplyNetGson(final Handler handler, final String url)
    {
        loading.setVisibility(View.VISIBLE);
        if (url == null || "".equals(url))
        {
            loading.setVisibility(View.GONE);
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
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void showEmpty()
    {
        super.showEmpty();
        listView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void show(String gson)
    {
        emptyView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        if (page == 0)
        {
            pageModule = ModuleParser.getPageModules(gson);
            if (pageModule.templateModules == null)
            {
                return;
            }
            if (pageModule.templateModules.size() == 0)
            {
                TemplateModule end = new TemplateModule();
                end.templateId = TemplateConstant.template_end_banner;
                end.description = "^_^没有更多了*_*";
                pageModule.templateModules.add(end);
                return;
            }
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
                if (pageModule.templateModules == null)
                {
                    return;
                }
                if (pageModule.templateModules.size() == 0)
                {
                    TemplateModule end = new TemplateModule();
                    end.templateId = TemplateConstant.template_end_banner;
                    end.description = "^_^没有更多了*_*";
                    pageModule.templateModules.add(end);
                    return;
                }
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
            Toast.makeText(this, "数据配置错误", Toast.LENGTH_SHORT).show();
            showEmpty();
            return;
        }
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
            case R.id.error:
                ApplyNetGson(handler, url);
                break;
            default:
                break;
        }
    }
}
