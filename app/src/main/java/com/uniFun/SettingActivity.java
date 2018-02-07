package com.uniFun;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uniFun.module.TemplateModule;
import com.uniFun.ui.jumputils.AddressManager;
import com.uniFun.ui.jumputils.CategoryUtil;
import com.uniFun.utils.CleanMessageUtil;

import demo.pplive.com.xspace.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView back;

    private TextView titleTxt;

    private int viewFrom = -1;

    private View feedback;

    private View clearCache;

    private TextView cache_txt;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView()
    {
        titleTxt = findViewById(R.id.title_txt);
        back = findViewById(R.id.img_back);
        feedback = findViewById(R.id.feedback);
        clearCache = findViewById(R.id.cache);
        cache_txt = findViewById(R.id.cache_txt);
        try
        {
            cache_txt.setText("清除缓存 (" + CleanMessageUtil.getTotalCacheSize(context) + ")");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        feedback.setOnClickListener(this);
        clearCache.setOnClickListener(this);
        back.setOnClickListener(this);

        if (module != null && module.link != null && !"".equals(module.link))
        {
            if (AddressManager.Native_Setting.equals(module.link))
            {
                titleTxt.setText("设置");
            }
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
            case R.id.feedback:
                jumpToFeedBack();
                break;
            case R.id.cache:
                clearCache();
                break;
            default:
                break;
        }
    }

    private void clearCache()
    {
        CleanMessageUtil.clearAllCache(context);
        try
        {
            cache_txt.setText("清除缓存 (" + CleanMessageUtil.getTotalCacheSize(context) + ")");
            Toast.makeText(context, "缓存清除完成", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(context, "缓存清除失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void jumpToFeedBack()
    {
        TemplateModule item = new TemplateModule();
        item.type = "native";
        item.link = AddressManager.Native_FeedBack;
        CategoryUtil.jumpByTargetLink(context, item, viewFrom);
    }
}
