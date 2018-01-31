package com.xspace;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import demo.pplive.com.xspace.R;

/**
 * <一句话功能简述> <推荐给好友>
 *
 * @author jixiongxu
 * @version [版本号, 2018/1/29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class RecommendActivity extends BaseActivity implements View.OnClickListener
{
    private TextView title;

    private ImageView back;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoment);
        initView();
    }

    private void initView()
    {
        title = findViewById(R.id.title_txt);
        title.setText("推荐");
        back = findViewById(R.id.img_back);
        back.setOnClickListener(this);
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
