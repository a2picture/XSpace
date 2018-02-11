package com.uniFun;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

public class MainApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        initFresco();
        CrashReport.initCrashReport(getApplicationContext(), "9068dc4f25", false);
    }

    private void initFresco()
    {
        final int MAX_CACHE_ENTRIES = 56;
        final int MAX_CACHE_EVICTION_SIZE = 5;
        final int MAX_CACHE_EVICTION_ENTRIES = 5;
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(this);
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this).setBaseDirectoryPath(
                new File(Environment.getExternalStorageDirectory() + "/uniFun/")).setBaseDirectoryName(".img/").build();
        builder.setMainDiskCacheConfig(diskCacheConfig);
        Supplier<MemoryCacheParams> supplier = new Supplier<MemoryCacheParams>()
        {
            @Override
            public MemoryCacheParams get()
            {
                return new MemoryCacheParams((int) (Runtime.getRuntime().maxMemory() / 10.0f), MAX_CACHE_ENTRIES,
                        MAX_CACHE_EVICTION_SIZE, MAX_CACHE_EVICTION_ENTRIES, Integer.MAX_VALUE);
            }
        };
        builder.setBitmapMemoryCacheParamsSupplier(supplier);
        ImagePipelineConfig config = builder.build();
        DraweeConfig.Builder draweeConBuilder = DraweeConfig.newBuilder();
        Fresco.initialize(this, config, draweeConBuilder.build());
    }

}
