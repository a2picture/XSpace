package com.xspace.ui.uihelper;

import android.content.Context;

import com.xspace.ui.template.BaseView;
import com.xspace.ui.template.TemplateGrid;
import com.xspace.ui.template.TemplateBanner;
import com.xspace.ui.template.TemplateCover;
import com.xspace.ui.template.TemplateEndBanner;
import com.xspace.ui.template.TemplateGrid2;
import com.xspace.ui.template.TemplateIndexItem;

public class TemplateManager
{
    public static BaseView findViewById(Context context, String tempId)
    {
        if (tempId == null || tempId.equals(""))
        {
            return null;
        }
        if (tempId.equals(TemplateConstant.template_index_item))
        {
            return new TemplateIndexItem(context);
        }
        else if (tempId.equals(TemplateConstant.template_banner))
        {
            return new TemplateBanner(context);
        }
        else if (tempId.equals(TemplateConstant.template_end_banner))
        {
            return new TemplateEndBanner(context);
        }
        else if (tempId.equals(TemplateConstant.template_cover))
        {
            return new TemplateCover(context);
        }
        else if (tempId.equals(TemplateConstant.template_grid))
        {
            return new TemplateGrid(context);
        }
        else if (tempId.equals(TemplateConstant.template_grid_2))
        {
            return new TemplateGrid2(context);
        }
        return null;
    }

}
