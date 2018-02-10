package com.uniFun.ui.template;

import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uniFun.R;
import com.uniFun.module.BaseModule;
import com.uniFun.module.TemplateModule;
import com.uniFun.utils.DisplayUtil;

public class TemplateCover extends BaseView
{
    public static String Template_ID = "template_cover";

    private SimpleDraweeView simpleDraweeView;

    private TextView title;

    private float scale = 0.333333f;

    public TemplateCover(Context context)
    {
        super(context);
        initView();
    }

    private void initView()
    {
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.CENTER);
        this.setPadding(0, 0, 0, 10);
        this.setLayoutParams(new LayoutParams(-1, -2));
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
    public void fillData(final BaseModule module)
    {
        if (module == null || !(module instanceof TemplateModule))
        {
            return;
        }

        if (simpleDraweeView == null)
        {
            simpleDraweeView = new SimpleDraweeView(mContext);
        }
        if (title == null)
        {
            title = new TextView(mContext);
        }
        int width = DisplayUtil.screenWidthPx(mContext);
        this.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onItemClick(module);
            }
        });
        if (((TemplateModule) module).scale > 0)
        {
            scale = ((TemplateModule) module).scale;
        }
        simpleDraweeView.setLayoutParams(new LayoutParams(width, (int) (width * scale)));
        simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_XY);
        simpleDraweeView.setBackground(mContext.getResources().getDrawable(R.drawable.loading));
        if ("".equals(((TemplateModule) module).subtitle))
        {
            title.setVisibility(GONE);
        }
        title.setText(((TemplateModule) module).title == null ? "" : ((TemplateModule) module).title);
        title.setPadding(20, 10, 0, 10);
        if (((TemplateModule) module).img_url != null)
        {
            simpleDraweeView.setImageURI(
                    Uri.parse("".equals(((TemplateModule) module).img_url) ? "" : ((TemplateModule) module).img_url));
        }
        addTemplateView();
    }

    @Override
    public void addTemplateView()
    {
        addView(simpleDraweeView);
        addView(title);
        invalidate();
    }

    @Override
    public void reFresh()
    {

    }
}
