package com.xspace.ui.jumputils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.xspace.app.CategoryActivity;
import com.xspace.app.UserActivity;
import com.xspace.module.BaseModule;
import com.xspace.module.TemplateModule;

public class CategoryUtil
{
    private static String TypeNative = "native";

    private static String TypeWeb = "web";

    private static String TypeHtml5 = "html5";

    private static String TypeOther = "other";

    public static boolean jumpByTargetLink(Context mContext, BaseModule module, int viewFrom)
    {
        if (module == null)
        {
            return false;
        }
        TemplateModule item = null;
        if (module instanceof TemplateModule)
        {
            item = (TemplateModule) module;
        }
        else
        {
            return false;
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
        else if (TypeOther.equals(item.type))
        {
            if (item.tag == null)
            {
                return false;
            }
            if (item.tag.equals("a"))
            {
                Toast.makeText(mContext, "添加到下载......", Toast.LENGTH_SHORT).show();
            }
            else if (item.tag.equals("b"))
            {
                Toast.makeText(mContext, "b", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    private static boolean jumpToNative(Context mContext, TemplateModule item, int viewFrom)
    {
        if (AddressManager.Native_Usercenter.equals(item.link))
        {
            Intent intent = new Intent(mContext, UserActivity.class);
            mContext.startActivity(intent);
            return true;
        }
        else if (AddressManager.Native_Category.equals(item.link))
        {
            Intent intent = new Intent(mContext, CategoryActivity.class);
            intent.putExtra("", item);
            mContext.startActivity(intent);
            return true;
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
