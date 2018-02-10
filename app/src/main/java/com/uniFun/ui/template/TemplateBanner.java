package com.uniFun.ui.template;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.uniFun.R;
import com.uniFun.module.BaseModule;
import com.uniFun.module.TemplateModule;


public class TemplateBanner extends BaseView
{
    public static final String Template_ID = "template_banner";

    private View mRootView;

    private SimpleDraweeView icon;

    private TextView title;

    private TextView more;

    public TemplateBanner(Context context)
    {
        super(context);
        initView();
    }

    private void initView()
    {
        this.setOrientation(VERTICAL);
        this.setLayoutParams(new LayoutParams(-1, -2));
        this.setPadding(0, 0, 0, 10);
        mRootView = View.inflate(mContext, R.layout.template_banner, null);
        icon = mRootView.findViewById(R.id.icon);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        icon.getHierarchy().setRoundingParams(roundingParams);
        title = mRootView.findViewById(R.id.title);
        more = mRootView.findViewById(R.id.more);
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

        title.setText(((TemplateModule) module).title);
        more.setText(((TemplateModule) module).subtitle);
        icon.setImageURI(Uri.parse(((TemplateModule) module).img_url));
        this.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onItemClick(module);
            }
        });
        addTemplateView();
    }

    @Override
    public void addTemplateView()
    {
        LayoutParams lp = new LayoutParams(-1, (int) (mContext.getResources().getDisplayMetrics().density * 40));
        mRootView.setLayoutParams(lp);
        addView(mRootView);
        invalidate();
    }

    @Override
    public void reFresh()
    {

    }
}
