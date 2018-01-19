package com.xspace.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xspace.module.TemplateModule;
import com.xspace.ui.jumputils.AddressManager;
import com.xspace.ui.jumputils.CategoryUtil;

import demo.pplive.com.xspace.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView back;

    private TextView titleTxt;

    private int viewFrom = -1;

    private View feedback;

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
        feedback.setOnClickListener(this);
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
            default:
                break;
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
