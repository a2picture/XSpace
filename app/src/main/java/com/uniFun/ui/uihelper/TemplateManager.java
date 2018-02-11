package com.uniFun.ui.uihelper;

import android.content.Context;

import com.uniFun.ui.template.BaseView;
import com.uniFun.ui.template.TemplateBanner;
import com.uniFun.ui.template.TemplateCover;
import com.uniFun.ui.template.TemplateEndBanner;
import com.uniFun.ui.template.TemplateFilmDisco;
import com.uniFun.ui.template.TemplateGrid;
import com.uniFun.ui.template.TemplateGrid2;
import com.uniFun.ui.template.TemplateGrid3;
import com.uniFun.ui.template.TemplateIndexItem;
import com.uniFun.ui.template.TemplateLineText;
import com.uniFun.ui.template.TemplateSearch;

public class TemplateManager
{
    public static int getViewType(String tempId)
    {
        if (tempId == null || tempId.equals(""))
        {
            return -1;
        }
        if (tempId.equals(TemplateConstant.template_index_item))
        {
            return 0;
        }
        else if (tempId.equals(TemplateConstant.template_banner))
        {
            return 1;
        }
        else if (tempId.equals(TemplateConstant.template_end_banner))
        {
            return 2;
        }
        else if (tempId.equals(TemplateConstant.template_cover))
        {
            return 3;
        }
        else if (tempId.equals(TemplateConstant.template_grid))
        {
            return 4;
        }
        else if (tempId.equals(TemplateConstant.template_grid_2))
        {
            return 5;
        }
        else if (tempId.equals(TemplateConstant.template_grid_3))
        {
            return 6;
        }
        else if (tempId.equals(TemplateConstant.template_line_text))
        {
            return 7;
        }
        else if (tempId.equals(TemplateConstant.template_search))
        {
            return 8;
        }
        else if (tempId.equals(TemplateConstant.template_film_disco))
        {
            return 9;
        }
        return -2;
    }

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
        else if (tempId.equals(TemplateConstant.template_grid_3))
        {
            return new TemplateGrid3(context);
        }
        else if (tempId.equals(TemplateConstant.template_line_text))
        {
            return new TemplateLineText(context);
        }
        else if (tempId.equals(TemplateConstant.template_search))
        {
            return new TemplateSearch(context);
        }
        else if (tempId.equals(TemplateConstant.template_film_disco))
        {
            return new TemplateFilmDisco(context);
        }
        return null;
    }

}
