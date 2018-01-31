package com.xspace;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xspace.module.ModuleParser;
import com.xspace.module.PageModule;
import com.xspace.module.TemplateModule;
import com.xspace.net.NetUtils;
import com.xspace.net.VersionUtils;
import com.xspace.ui.uihelper.TemplateConstant;
import com.xspace.ui.uihelper.TemplateContainerImpl;
import com.xspace.utils.MyFileUtils;
import com.xspace.utils.NetAddressManager;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import demo.pplive.com.xspace.R;

public class PlayerActivity extends BaseActivity
{
    private JZVideoPlayerStandard videoPlayer;

    private PullToRefreshListView listView;

    private TemplateContainerImpl impl;

    private PageModule pageModule;

    private PageModule historyPage;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case NetUtils.REQUEST_PAGEMODULE_OK:
                    show(msg.obj.toString());
                case NetUtils.REQUEST_PAGEMODULE_FAIL:
                    showEmpty();
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
    }

    private void initView()
    {
        videoPlayer = findViewById(R.id.videoplayer);
        listView = findViewById(R.id.pull_to_refresh);
        String url = NetAddressManager.root_website + NetAddressManager.player + "?vid=" + module.vid + "&appVer="
                + VersionUtils.getAppVersionName(context);
        if (module != null)
        {
            videoPlayer.setUp(module.url == null ? "" : module.url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                    module.title == null ? "" : module.title);
            // videoPlayer.thumbImageView.setImageURI(Uri.parse(module.img_url
            // == null ? "" : module.img_url));
            videoPlayer.thumbImageView.setImageResource(R.drawable.movie);
            historyPage = (PageModule) MyFileUtils.getSerializableFromLocal("history");
            if (historyPage == null)
            {
                historyPage = new PageModule();
            }
            if (historyPage.templateModules == null)
            {
                historyPage.templateModules = new ArrayList<>();
            }
            if (historyPage.templateModules.size() > 50)
            {
                historyPage.templateModules.remove(historyPage.templateModules.size() - 1);
            }
            TemplateModule templateModule = module;
            templateModule.templateId = TemplateConstant.template_index_item;
            for (int i = 0; i < historyPage.templateModules.size(); i++)
            {
                if (templateModule.url.equals(historyPage.templateModules.get(i).url))
                {
                    historyPage.templateModules.remove(i);
                }
            }
            historyPage.templateModules.add(0, templateModule);
            MyFileUtils.Serializable2Local("history", historyPage);
        }
        else
        {
            videoPlayer.thumbImageView.setImageResource(R.drawable.player_bg);
        }
        impl = new TemplateContainerImpl(context);
        impl.setListView(listView);
        ApplyNetGson(handler, url);
    }

    @Override
    public void onBackPressed()
    {
        if (JZVideoPlayer.backPress())
        {
            return;
        }
        super.onBackPressed();
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
    protected void onPause()
    {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void show(String gson)
    {
        super.show(gson);
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
    protected void showEmpty()
    {
        super.showEmpty();
    }
}
