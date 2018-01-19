package com.xspace.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xspace.module.TemplateModule;
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
        version.setText("当前版本：v " + getAppVersionName(context));
    }

    public static String getAppVersionName(Context context)
    {
        String versionName = "";
        try
        {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0)
            {
                return "";
            }
        }
        catch (Exception e)
        {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
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
