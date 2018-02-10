package com.uniFun.ui.uihelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.uniFun.module.PageModule;
import com.uniFun.ui.template.BaseView;

import java.lang.ref.WeakReference;

/**
 * <一句话功能简述> <模板构建实现>
 *
 * @author jixiongxu
 * @version [版本号, 2018/1/10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class TemplateContainerImpl
{
    private Context context;

    private PullToRefreshListView listView;

    private PullToRefreshListViewAdapter adapter;

    public TemplateContainerImpl(Context context)
    {
        this.context = context;
    }

    private void initListView()
    {
        if (listView == null)
        {
            return;
        }
        adapter = new PullToRefreshListViewAdapter();
        listView.setAdapter(adapter);
    }

    public void setListView(PullToRefreshListView listView)
    {
        this.listView = listView;
        initListView();
    }

    public void startConstruct(PageModule pageModule)
    {
        if (pageModule == null || pageModule.templateModules == null || pageModule.templateModules.size() == 0)
        {
            return;
        }
        adapter.setData(pageModule);
        adapter.notifyDataSetChanged();
    }

    public class PullToRefreshListViewAdapter extends BaseAdapter
    {
        private PageModule pageModule;

        public void setData(PageModule module)
        {
            if (module == null)
            {
                return;
            }
            pageModule = module;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }

        @Override
        public int getCount()
        {
            if (pageModule == null || pageModule.templateModules == null)
            {
                return 0;
            }
            return pageModule.templateModules.size();
        }

        @Override
        public Object getItem(int i)
        {
            if (pageModule == null || pageModule.templateModules == null)
            {
                return null;
            }
            return pageModule.templateModules.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            if (pageModule == null || pageModule.templateModules == null)
            {
                return null;
            }
            BaseView template = TemplateManager.findViewById(new WeakReference<>(context).get(),
                    pageModule.templateModules.get(i).templateId);
            if (template == null)
            {
                return null;
            }
            template.setData(pageModule.templateModules.get(i));
            view = template;
            return view;
        }
    }
}
