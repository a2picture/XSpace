package com.xspace;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xspace.module.TemplateModule;
import com.xspace.net.VersionUtils;
import com.xspace.ui.jumputils.AddressManager;
import com.xspace.ui.jumputils.CategoryUtil;

import demo.pplive.com.xspace.R;

public class AboutActivity extends BaseActivity implements View.OnClickListener
{
    private TextView website;

    private TextView title;

    private ImageView back;

    private TextView version;

    private int viewFrom = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        title = findViewById(R.id.title_txt);
        back = findViewById(R.id.img_back);
        website = findViewById(R.id.website);
        website.setOnClickListener(this);
        version = findViewById(R.id.version);
        back.setOnClickListener(this);
        if (module != null && module.link != null && !"".equals(module.link))
        {
            if (AddressManager.Native_About.equals(module.link))
            {
                title.setText("软件信息");
            }
        }
        version.setText("当前版本：v " + VersionUtils.getAppVersionName(context));
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
            case R.id.website:
                jumpToWeb();
                break;
            default:
                break;
        }
    }

    private void jumpToWeb()
    {
        TemplateModule item = new TemplateModule();
        item.type = "web";
        item.url = "http://" + website.getText().toString();
        CategoryUtil.jumpByTargetLink(context, item, viewFrom);
    }
}
