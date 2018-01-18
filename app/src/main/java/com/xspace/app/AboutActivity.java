package com.xspace.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xspace.ui.jumputils.AddressManager;

import demo.pplive.com.xspace.R;

public class AboutActivity extends BaseActivity implements View.OnClickListener
{
    private TextView title;

    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        title = findViewById(R.id.title_txt);
        back = findViewById(R.id.img_back);
        back.setOnClickListener(this);
        if (module != null && module.link != null && !"".equals(module.link))
        {
            if (AddressManager.Native_About.equals(module.link))
            {
                title.setText("软件信息");
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
            default:
                break;
        }
    }
}
