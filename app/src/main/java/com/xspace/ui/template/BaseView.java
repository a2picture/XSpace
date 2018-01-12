package com.xspace.ui.template;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.xspace.module.BaseModule;
import com.xspace.ui.jumputils.CategoryUtil;

public abstract class BaseView extends LinearLayout
{
    protected int viewCode = 0;

    protected Context mContext;

    protected BaseModule module;

    public BaseView(Context context)
    {
        super(context);
        mContext = context;
    }

    public BaseView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
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
