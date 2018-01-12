package com.xspace.ui.jumputils;

import android.content.Context;
import android.content.Intent;

import com.xspace.app.CategoryActivity;
import com.xspace.app.UserActivity;
import com.xspace.module.BaseModule;
import com.xspace.module.TemplateModule;

public class CategoryUtil
{
    private static String TypeNative = "native";

    private static String TypeWeb = "web";

    private static String TypeHtml5 = "html5";

    public static boolean jumpByTargetLink(Context mContext, BaseModule module, int viewFrom)
    {
        TemplateModule.TemplateItem item = new TemplateModule.TemplateItem();
        if (module instanceof TemplateModule)
        {
            item.link = ((TemplateModule) module).link;
            item.type = ((TemplateModule) module).type;
            item.title = ((TemplateModule) module).title;
        }
        else if (module instanceof TemplateModule.TemplateItem)
        {
            item = (TemplateModule.TemplateItem) module;
        }
        if (TypeNative.equals(item.type))
        {
            return jumpToNative(mContext, item, viewFrom);
        }
        else if (TypeWeb.equals(item.type))
        {
            return jumpToWeb(mContext, item, viewFrom);
        }
        else if (TypeHtml5.equals(item.type))
        {
            return jumpToHtml5(mContext, item, viewFrom);
        }
        return false;
    }

    private static boolean jumpToNative(Context mContext, TemplateModule.TemplateItem item, int viewFrom)
    {
        if (AddressManager.Native_Usercenter.equals(item.link))
        {
            Intent intent = new Intent(mContext, UserActivity.class);
            mContext.startActivity(intent);
        }
        else if (AddressManager.Native_Category.equals(item.link))
        {
            Intent intent = new Intent(mContext, CategoryActivity.class);
            mContext.startActivity(intent);
        }
        return false;
    }

    private static boolean jumpToWeb(Context mContext, BaseModule module, int viewFrom)
    {
        return false;
    }

    private static boolean jumpToHtml5(Context mContext, BaseModule module, int viewFrom)
    {
        return false;
    }
}
