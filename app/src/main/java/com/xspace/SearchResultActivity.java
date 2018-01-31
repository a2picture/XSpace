package com.xspace;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xspace.module.ModuleParser;
import com.xspace.module.PageModule;
import com.xspace.net.NetUtils;
import com.xspace.net.VersionUtils;
import com.xspace.ui.uihelper.TemplateContainerImpl;
import com.xspace.utils.NetAddressManager;

import demo.pplive.com.xspace.R;

public class SearchResultActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView back;

    private TextView title;

    private View emptyView;

    private String url;

    private PullToRefreshListView listView;

    private ImageView error;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resoult);
        back = findViewById(R.id.img_back);
        listView = findViewById(R.id.pull_to_refresh);
        emptyView = findViewById(R.id.empty_view);
        title = findViewById(R.id.title_txt);
        title.setText("搜索结果");
        error = findViewById(R.id.error);
        initView();
    }

    private void initView()
    {
        error.setOnClickListener(this);
        back.setOnClickListener(this);
        impl = new TemplateContainerImpl(context);
        impl.setListView(listView);
        if (module != null)
        {
            String keyword = Uri.encode(module.tag == null ? "" : module.tag, "iso-8859-1");
            url = NetAddressManager.root_website + NetAddressManager.search + "?keyworld=" + keyword + "&appVer="
                    + VersionUtils.getAppVersionName(context);
        }
        ApplyNetGson(handler, url);
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

    @Override
    protected void showEmpty()
    {
        super.showEmpty();
        listView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void show(String gson)
    {
        emptyView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        pageModule = ModuleParser.getPageModules(gson);
        if (pageModule == null)
        {
            Toast.makeText(context, "数据配置出错", Toast.LENGTH_SHORT).show();
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
