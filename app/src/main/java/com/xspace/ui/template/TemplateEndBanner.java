package com.xspace.ui.template;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xspace.module.BaseModule;
import com.xspace.module.TemplateModule;

public class TemplateEndBanner extends BaseView
{
    public static final String Template_ID = "template_end_banner";

    private TextView endTex;

    public TemplateEndBanner(Context context)
    {
        super(context);
        initView();
    }

    private void initView()
    {
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.CENTER);
        this.setLayoutParams(new LayoutParams(-1, -2));
        this.setPadding(0, 0, 0, 10);

        this.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onItemClick(module);
            }
        });
    }

    @Override
    public void setData(BaseModule module)
    {
        if (module == null)
        {
            return;
        }
        this.module = module;
        fillData(module);
    }

    @Override
    public void fillData(BaseModule module)
    {
        if (module == null || !(module instanceof TemplateModule))
        {
            return;
        }
        if (endTex == null)
        {
            endTex = new TextView(mContext);
        }
        endTex.setText(((TemplateModule) module).description);
        addTemplateView();
    }

    @Override
    public void addTemplateView()
    {
        LayoutParams lp = new LayoutParams(-2, -2);
        endTex.setLayoutParams(lp);
        endTex.setPadding(0, 20, 0, 20);
        addView(endTex);
        invalidate();
    }

    @Override
    public void reFresh()
    {

    }
}
