package com.xspace;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xspace.module.PageModule;
import com.xspace.ui.jumputils.AddressManager;
import com.xspace.ui.uihelper.TemplateContainerImpl;
import com.xspace.utils.MyFileUtils;

import demo.pplive.com.xspace.R;

public class LocalActivity extends BaseActivity implements View.OnClickListener
{
    private TextView title;

    private ImageView back;

    private TemplateContainerImpl impl;

    private PullToRefreshListView listView;

    private PageModule pageModule;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        title = findViewById(R.id.title_txt);
        listView = findViewById(R.id.pull_to_refresh);
        back = findViewById(R.id.img_back);
        back.setOnClickListener(this);
        impl = new TemplateContainerImpl(context);
        pageModule = new PageModule();
        if (module != null && module.link != null && !"".equals(module.link))
        {
            if (AddressManager.Native_Local.equals(module.link))
            {
                title.setText("本地视频");
                ApplyLocalModule();
            }
            else if (AddressManager.Native_History.equals(module.link))
            {
                title.setText("播放历史");
                ApplyHistoryModule();
            }
        }
    }

    private void ApplyLocalModule()
    {
        if (AddressManager.Native_Local.equals(module.link))
        {
            pageModule = (PageModule) MyFileUtils.getSerializableFromLocal("localVideo");
            impl.setListView(listView);
            impl.startConstruct(pageModule);
        }
    }

    private void ApplyHistoryModule()
    {
        if (AddressManager.Native_History.equals(module.link))
        {
            pageModule = (PageModule) MyFileUtils.getSerializableFromLocal("history");
            impl.setListView(listView);
            impl.startConstruct(pageModule);
        }
    }

    @Override
    protected void show(String gson)
    {
        super.show(gson);
    }

    @Override
    protected void showEmpty()
    {
        super.showEmpty();
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
