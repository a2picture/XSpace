package com.uniFun.utils;

import android.util.Log;

import com.uniFun.module.TemplateModule;
import com.uniFun.ui.jumputils.AddressManager;
import com.uniFun.ui.jumputils.CategoryUtil;
import com.uniFun.ui.uihelper.TemplateConstant;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class VideoScanUtils
{
    /**
     * 获取视频文件
     * 
     * @param list
     * @param file
     * @return
     */
    public static List<TemplateModule> getVideoFile(final List<TemplateModule> list, File file)
    {
        file.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File file)
            {
                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1)
                {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4") || name.equalsIgnoreCase(".3gp") || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts") || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov") || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi") || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp") || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv") || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx") || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm") || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram") || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8") || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v") || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra") || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid"))
                    {
                        file.getUsableSpace();
                        TemplateModule video = new TemplateModule();
                        video.title = file.getName();
                        video.templateId = TemplateConstant.template_index_item;
                        video.url = file.getAbsolutePath();
                        video.link = AddressManager.Native_Player;
                        video.type = CategoryUtil.TypeNative;
                        video.tag = "localVideo";
                        list.add(video);
                        return true;
                    }
                    // 判断是不是目录
                }
                else if (file.isDirectory())
                {
                    getVideoFile(list, file);
                }
                return false;
            }
        });
        return list;
    }

    /**
     * 10M=10485760 b,小于10m的过滤掉 过滤视频文件
     * 
     * @param videoInfos
     * @return
     */
    private List<TemplateModule> filterVideo(List<TemplateModule> videoInfos)
    {
        List<TemplateModule> newVideos = new ArrayList<TemplateModule>();
        for (TemplateModule videoInfo : videoInfos)
        {
            File f = new File(videoInfo.url);
            if (f.exists() && f.isFile() && f.length() > 10485760)
            {
                newVideos.add(videoInfo);
                Log.i("TGA", "文件大小" + f.length());
            }
            else
            {
                Log.i("TGA", "文件太小或者不存在");
            }
        }
        return newVideos;
    }
}
