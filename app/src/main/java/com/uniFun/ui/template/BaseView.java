package com.uniFun.ui.template;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.uniFun.module.BaseModule;
import com.uniFun.ui.jumputils.CategoryUtil;

public abstract class BaseView extends LinearLayout
{
    protected int viewCode = 0;

    protected Context mContext;

    protected boolean hasAddView = false;

    protected BaseModule module;

    public BaseView(Context context)
    {
        super(context);
        this.mContext = context;
    }

    public BaseView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
    }

    protected void onItemClick(BaseModule module)
    {
        CategoryUtil.jumpByTargetLink(mContext, module, viewCode);
    }

    public BaseModule getModule()
    {
        return this.module;
    }

    public Context getmContext()
    {
        return this.mContext;
    }

    public abstract void setData(BaseModule module);

    public abstract void fillData(BaseModule module);

    public abstract void addTemplateView();

    public abstract void reFresh();
}
