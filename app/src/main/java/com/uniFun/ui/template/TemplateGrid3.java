package com.uniFun.ui.template;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uniFun.R;
import com.uniFun.module.BaseModule;
import com.uniFun.module.TemplateModule;
import com.uniFun.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <一句话功能简述> <图片轮播>
 *
 * @author jixiongxu
 * @version [版本号, 2018/1/12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class TemplateGrid3 extends BaseView
{
    public static String TemplateID = "template_grid_3";

    private int max_count = 12;

    private double W21_H9 = 0.4285;

    private FrameLayout mRootView;

    private MyAdapter adapter;

    private ViewPager viewPager;

    private LinearLayout dotContainer;

    private TemplateModule newModule;

    private int currentViewPagePositon = 0;

    private int width;

    private Timer timer;

    public TemplateGrid3(Context context)
    {
        super(context);
        initView();
    }

    private void initView()
    {
        this.setLayoutParams(new LayoutParams(-1, -2));
        this.setPadding(0, 0, 0, 10);
        width = DisplayUtil.screenWidthPx(mContext);
        mRootView = new FrameLayout(mContext);
        mRootView.setLayoutParams(new LayoutParams(-1, -2));
    }

    @Override
    public void setData(BaseModule module)
    {
        mRootView.removeAllViews();
        if (module == null || !(module instanceof TemplateModule))
        {
            return;
        }
        this.module = module;

        if (((TemplateModule) module).templateItems.size() > max_count)
        {
            newModule = (TemplateModule) module;

            while (newModule.templateItems.size() > max_count)
            {
                newModule.templateItems.remove(newModule.templateItems.size());
            }
            fillData(newModule);
        }
        else
        {
            fillData(module);
        }
    }

    @Override
    public void fillData(BaseModule module)
    {
        viewPager = new ViewPager(mContext);
        viewPager.setLayoutParams(new LayoutParams(-1, (int) (width * W21_H9)));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                currentViewPagePositon = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        adapter = new MyAdapter();
        adapter.setItems(((TemplateModule) module).templateItems);
        viewPager.setAdapter(adapter);
        mRootView.addView(viewPager);
        addDot(((TemplateModule) module).templateItems);
        addTemplateView();
        auto();
    }

    private void addDot(ArrayList<TemplateModule> items)
    {
        dotContainer = new LinearLayout(mContext);
        dotContainer.setLayoutParams(new LayoutParams(-1, -2));
        dotContainer.setGravity(Gravity.END);
        dotContainer.setPadding(0, (int) (width * W21_H9) - 40, 20, 0);
        for (int i = 0; i < items.size(); i++)
        {
            ImageView dot = new ImageView(mContext);
            dot.setLayoutParams(new LayoutParams(25, 25));
            dot.setImageResource(R.drawable.dot_light);
            dotContainer.addView(dot);
        }
        mRootView.addView(dotContainer);
    }

    private void auto()
    {
        if (adapter.getCount() < 1)
        {
            return;
        }
        timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                viewPager.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for (int i = 0; i < dotContainer.getChildCount(); i++)
                        {
                            ImageView dot = (ImageView) dotContainer.getChildAt(i);
                            if (i == currentViewPagePositon % adapter.getCount())
                            {
                                dot.setImageResource(R.drawable.dot_normal);
                            }
                            else
                            {
                                dot.setImageResource(R.drawable.dot_light);
                            }
                        }
                        viewPager.setCurrentItem(currentViewPagePositon++ % adapter.getCount());
                    }
                });
            }
        };
        timer.schedule(task, 0, 2000);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    @Override
    public void addTemplateView()
    {
        this.removeAllViews();
        addView(mRootView);
    }

    @Override
    public void reFresh()
    {

    }

    class MyAdapter extends PagerAdapter
    {
        private ArrayList<TemplateModule> items;

        public void setItems(ArrayList<TemplateModule> items)
        {
            if (items == null || items.size() == 0)
            {
                return;
            }
            this.items = items;
        }

        @Override
        public int getCount()
        {
            if (items == null || items.size() == 0)
            {
                return 0;
            }
            return items.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position)
        {
            if (items == null || position > items.size())
            {
                return null;
            }
            FrameLayout itemContainer = new FrameLayout(mContext);
            itemContainer.setLayoutParams(new LayoutParams(-1, (int) (width * W21_H9)));
            SimpleDraweeView itemImage = new SimpleDraweeView(mContext);
            itemImage.setLayoutParams(new LayoutParams(-1, (int) (width * W21_H9)));
            itemImage.setScaleType(ImageView.ScaleType.FIT_XY);

            itemContainer.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    onItemClick(items.get(position));
                }
            });
            TextView title = new TextView(mContext);
            title.setGravity(Gravity.BOTTOM);
            title.setPadding(20, 0, 0, 10);
            itemContainer.addView(itemImage);
            itemContainer.addView(title);

            title.setTextColor(getResources().getColor(R.color.withe_alpha_1));
            title.setText(items.get(position).title);
            itemImage.setImageURI(Uri.parse(items.get(position).img_url));

            container.addView(itemContainer);
            return itemContainer;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }
    }
}
