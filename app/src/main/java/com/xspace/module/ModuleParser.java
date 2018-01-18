package com.xspace.module;

import com.google.gson.Gson;

public class ModuleParser
{
    public static <T> T getPageModules(String json)
    {
        if (json == null)
        {
            return null;
        }
        try
        {
            PageModule module;
            Gson gson = new Gson();
            module = gson.fromJson(json, PageModule.class);
            return (T) module;
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }

    public static <T> T getTemplateModule(String json)
    {
        if (json == null)
        {
            return null;
        }
        try
        {
            TemplateModule module;
            Gson gson = new Gson();
            module = gson.fromJson(json, TemplateModule.class);
            return (T) module;
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
}
