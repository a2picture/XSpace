package com.xspace.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class LocalUserInfoView extends LinearLayout
{
    private View mRootView;

    private Context context;

    public LocalUserInfoView(Context context)
    {
        super(context);
        this.context = context;
        initView();
    }

    public LocalUserInfoView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView()
    {

    }
}
