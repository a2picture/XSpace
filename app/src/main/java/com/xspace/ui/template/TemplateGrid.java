package com.xspace.ui.template;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xspace.module.BaseModule;
import com.xspace.module.TemplateModule;
import com.xspace.utils.DisplayUtil;

import demo.pplive.com.xspace.R;

public class TemplateGrid extends BaseView
{
    private int width;

    private static int maxCow = 2;

    public static String TemplateID = "template_grid";

    private LinearLayout mRootView;

    public TemplateGrid(Context context)
    {
        super(context);
        initView();
    }

    private void initView()
    {
        this.setOrientation(VERTICAL);
        this.setLayoutParams(new LayoutParams(-1, -2));
        this.setPadding(0, 0, 0, 10);
        width = DisplayUtil.screenWidthPx(mContext);
        mRootView = new LinearLayout(mContext);
        mRootView.setOrientation(VERTICAL);
        mRootView.setLayoutParams(new LayoutParams(-1, -2));
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
        // 大标题
        TextView title = new TextView(mContext);
        title.setPadding(20, 20, 20, 20);
        title.setSingleLine(true);
        title.setTextSize(
                getResources().getDimension(R.dimen.dt_text_size_lager) / DisplayUtil.getDensity(mContext) + 1);
        title.setText(((TemplateModule) module).title);
        addView(title);

        if (((TemplateModule) module).templateItems == null)
        {
            return;
        }
        int i = 0;
        LinearLayout line = null;
        for (final TemplateModule.TemplateItem item : ((TemplateModule) module).templateItems)
        {
            if (i % maxCow == 0)
            {
                line = new LinearLayout(mContext);
                line.setLayoutParams(new LayoutParams(-1, -2));
                line.setOrientation(HORIZONTAL);
                mRootView.addView(line);
            }
            LayoutParams itemParams = new LayoutParams(width / maxCow, -2);
            LinearLayout itemContainer = new LinearLayout(mContext);
            itemContainer.setOrientation(VERTICAL);
            itemContainer.setLayoutParams(itemParams);
            itemContainer.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    onItemClick(item);
                }
            });
            // item标题
            TextView itemTitle = new TextView(mContext);
            itemTitle.setLayoutParams(itemParams);
            itemTitle.setText(item.title);
            itemTitle.setSingleLine(true);
            itemTitle.setPadding(20, 0, 20, 0);
            itemTitle.setTextSize(
                    getResources().getDimension(R.dimen.dt_text_size_normal) / DisplayUtil.getDensity(mContext) + 1);

            // item描述
            TextView itemDesc = new TextView(mContext);
            itemDesc.setLayoutParams(itemParams);
            itemDesc.setMaxLines(2);
            itemDesc.setText(item.description);
            itemDesc.setPadding(20, 10, 20, 10);
            itemDesc.setTextSize(
                    getResources().getDimension(R.dimen.dt_text_size_small) / DisplayUtil.getDensity(mContext) + 1);

            // item图片
            SimpleDraweeView itemImg = new SimpleDraweeView(mContext);
            itemImg.setImageURI(Uri.parse(item.img_url));
            itemImg.setLayoutParams(new LayoutParams(width / maxCow, (int) ((width / maxCow) * 0.5)));
            itemImg.setPadding(2, 2, 2, 2);

            itemContainer.addView(itemImg);
            itemContainer.addView(itemTitle);
            itemContainer.addView(itemDesc);

            if (line != null)
            {
                line.addView(itemContainer);
            }
            i++;
        }
        addTemplateView();
    }

    @Override
    public void addTemplateView()
    {
        addView(mRootView);
    }

    @Override
    public void reFresh()
    {

    }
}
