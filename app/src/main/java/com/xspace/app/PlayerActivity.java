package com.xspace.app;

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
import com.xspace.net.NetUtils;
import com.xspace.net.VersionUtils;
import com.xspace.ui.uihelper.TemplateContainerImpl;
import com.xspace.utils.NetAddressManager;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import demo.pplive.com.xspace.R;

public class PlayerActivity extends BaseActivity
{
    private JZVideoPlayerStandard videoPlayer;

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
            videoPlayer.thumbImageView.setImageResource(R.drawable.github);
        }
        else
        {
            videoPlayer.thumbImageView.setImageResource(R.drawable.github);
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
