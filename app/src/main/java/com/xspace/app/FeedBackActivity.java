package com.xspace.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xspace.ui.jumputils.AddressManager;

import demo.pplive.com.xspace.R;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView back;

    private TextView titleTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView()
    {
        titleTxt = findViewById(R.id.title_txt);
        back = findViewById(R.id.img_back);
        back.setOnClickListener(this);
        if (module != null && module.link != null && !"".equals(module.link))
        {
            if (AddressManager.Native_FeedBack.equals(module.link))
            {
                titleTxt.setText("用户反馈");
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
