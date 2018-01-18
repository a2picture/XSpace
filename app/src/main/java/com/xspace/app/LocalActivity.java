package com.xspace.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xspace.module.PageModule;
import com.xspace.ui.jumputils.AddressManager;

import demo.pplive.com.xspace.R;

public class LocalActivity extends BaseActivity implements View.OnClickListener
{
    private TextView title;

    private ImageView back;

    private PageModule pageModule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        title = findViewById(R.id.title_txt);
        back = findViewById(R.id.img_back);
        back.setOnClickListener(this);
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
                title.setText("历史记录");
                ApplyHistoryModule();
            }
        }
    }

    private void ApplyLocalModule()
    {

    }

    private void ApplyHistoryModule()
    {

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
