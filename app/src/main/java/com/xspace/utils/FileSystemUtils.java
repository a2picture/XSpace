package com.xspace.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import com.xspace.module.BaseModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by jixiongxu on 2018/1/30.
 */

public class FileSystemUtils implements Serializable
{
    // 保存文件的路径
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xspace/local/";

    private static String videopic_path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/xspace/local/pic/";

    public static boolean Serializable2Local(String name, BaseModule module)
    {
        boolean res;
        ObjectOutputStream oos = null;
        CreatPath(path);
        try
        {
            oos = new ObjectOutputStream(new FileOutputStream(path + name + ".txt"));
            oos.writeObject(module);
            res = true;
        }
        catch (IOException e)
        {
            res = false;
            e.printStackTrace();
        }
        finally
        {
            if (oos != null)
            {
                try
                {
                    oos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    public static BaseModule getSerializableFromLocal(String name)
    {
        ObjectInputStream ois = null;
        BaseModule module = null;
        try
        {
            ois = new ObjectInputStream(new FileInputStream(path + name + ".txt"));
            try
            {
                module = (BaseModule) ois.readObject();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (ois != null)
            {
                try
                {
                    ois.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return module;
    }

    public static void CreatPath(String path)
    {
        File destDir = new File(path);
        if (!destDir.exists())
        {
            destDir.mkdirs();
        }
    }

    public static synchronized Bitmap getVideoThumbnail(String videoPath)
    {
        CreatPath(videopic_path);
        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            Bitmap img = BitmapFactory.decodeFile(videopic_path + new File(videoPath).getName() + ".jpg", options);
            if (img != null)
            {
                Log.d("jixiongxu", "from local" + videoPath);
                return img;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(videoPath);
            Bitmap bitmap = media.getFrameAtTime();
            saveBitmapToFile(videopic_path + new File(videoPath).getName() + ".jpg", bitmap);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean saveBitmapToFile(String filename, Bitmap bmp)
    {
        if (bmp == null || filename == null)
            return false;
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try
        {
            stream = new FileOutputStream(filename);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bmp.compress(format, quality, stream);
    }
}
