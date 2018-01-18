package com.xspace.ui.jumputils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.xspace.app.AboutActivity;
import com.xspace.app.DetailActivity;
import com.xspace.app.LocalActivity;
import com.xspace.app.UserActivity;
import com.xspace.app.WebActivity;
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

        TemplateModule item;

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
            return jumpToOther(mContext, item);
        }
        return false;
    }

    private static boolean jumpToOther(Context mContext, TemplateModule item)
    {
        if (item.tag == null)
        {
            return true;
        }
        if (item.tag.equals("a"))
        {
            Toast.makeText(mContext, "添加到下载......", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.tag.equals("b"))
        {
            Toast.makeText(mContext, "b", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private static boolean jumpToNative(Context mContext, TemplateModule item, int viewFrom)
    {
        if (AddressManager.Native_Usercenter.equals(item.link))
        {
            Intent intent = new Intent(mContext, UserActivity.class);
            intent.putExtra("module", item);
            mContext.startActivity(intent);
            return true;
        }
        else if (AddressManager.Native_Category.equals(item.link))
        {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("module", item);
            mContext.startActivity(intent);
            return true;
        }
        else if (AddressManager.Native_History.equals(item.link))
        {
            Intent intent = new Intent(mContext, LocalActivity.class);
            intent.putExtra("module", item);
            mContext.startActivity(intent);
        }
        else if (AddressManager.Native_Local.equals(item.link))
        {
            Intent intent = new Intent(mContext, LocalActivity.class);
            intent.putExtra("module", item);
            mContext.startActivity(intent);
        }
        else if (AddressManager.Native_About.equals(item.link))
        {
            Intent intent = new Intent(mContext, AboutActivity.class);
            intent.putExtra("module", item);
            mContext.startActivity(intent);
        }
        return false;
    }

    private static boolean jumpToWeb(Context mContext, TemplateModule item, int viewFrom)
    {
        if (item == null || item.url == null || "".equals(item.url))
        {
            return false;
        }
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("module", item);
        mContext.startActivity(intent);

        return false;
    }

    private static boolean jumpToHtml5(Context mContext, TemplateModule item, int viewFrom)
    {
        return false;
    }
}
