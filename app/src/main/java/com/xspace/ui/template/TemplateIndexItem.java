package com.xspace.ui.template;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xspace.module.BaseModule;
import com.xspace.module.TemplateModule;

import demo.pplive.com.xspace.R;

/**
 * <一句话功能简述> <首页item>
 *
 * @author jixiongxu
 * @version [版本号, 2018/1/9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class TemplateIndexItem extends BaseView
{
    public static final String Template_ID = "template_index_item";

    private View mRootView;

    private SimpleDraweeView chapterImg;

    private TextView chapterTitle;

    private TextView chapterDesc;

    public TemplateIndexItem(Context context)
    {
        super(context);
        initView();
    }

    public void initView()
    {
        this.setOrientation(VERTICAL);
        this.setLayoutParams(new LayoutParams(-1, -2));
        this.setPadding(0, 0, 0, 10);
        mRootView = View.inflate(mContext, R.layout.template_index, null);
        chapterImg = mRootView.findViewById(R.id.chapter_img);
        chapterTitle = mRootView.findViewById(R.id.chapter_title);
        chapterDesc = mRootView.findViewById(R.id.chapter_desc);
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
        chapterTitle.setText(((TemplateModule) module).title);
        chapterDesc.setText("\u3000"+((TemplateModule) module).description);
        chapterImg.setImageURI(Uri.parse(((TemplateModule) module).img_url));
        addTemplateView();
    }

    @Override
    public void addTemplateView()
    {
        LayoutParams lp = new LayoutParams(-1, -2);
        mRootView.setLayoutParams(lp);
        addView(mRootView);
        invalidate();
    }

    @Override
    public void reFresh()
    {

    }
}
