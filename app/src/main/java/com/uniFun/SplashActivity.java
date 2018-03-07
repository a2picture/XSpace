package com.uniFun;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.List;

import abc.abc.abc.AdManager;
import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.sp.SplashViewSettings;
import abc.abc.abc.nm.sp.SpotListener;
import abc.abc.abc.nm.sp.SpotManager;
import abc.abc.abc.nm.sp.SpotRequestListener;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 开屏广告演示窗口
 *
 * @author Alian Lee
 * @since 2016-11-25
 */
public class SplashActivity extends BaseYoumiActivity implements EasyPermissions.PermissionCallbacks {

    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

    private static final int READ_PHONE_STATE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 移除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        locationAndContactsTask();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 跑应用的逻辑
     */
    private void runApp() {
        // 初始化SDK
        // appId = 5b24f43745cdcfc6
        // secret = 4409d16c1039e086
        AdManager.getInstance(mContext).init("5b24f43745cdcfc6", "4409d16c1039e086", true);
        preloadAd();
        setupSplashAd(); // 如果需要首次展示开屏，请注释掉本句代码
    }

    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }

    @AfterPermissionGranted(READ_PHONE_STATE)
    public void locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            // Have permissions, do the thing!
            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    "您的程序尚未打开部分权限，为确保程序的正常运行请打开以下权限！",
                    READ_PHONE_STATE,
                    LOCATION_AND_CONTACTS);
        }
    }

    /**
     * 预加载广告
     */
    private void preloadAd() {
        // 注意：不必每次展示插播广告前都请求，只需在应用启动时请求一次
        SpotManager.getInstance(mContext).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {
                logInfo("请求插播广告成功");
                // // 应用安装后首次展示开屏会因为本地没有数据而跳过
                // // 如果开发者需要在首次也能展示开屏，可以在请求广告成功之前展示应用的logo，请求成功后再加载开屏
//                setupSplashAd();
            }

            @Override
            public void onRequestFailed(int errorCode) {
                logError("请求插播广告失败，errorCode: %s", errorCode);
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
//                        showShortToast("网络异常");
                        break;
                    case ErrorCode.NON_AD:
//                        showShortToast("暂无视频广告");
                        break;
                    default:
//                        showShortToast("请稍后再试");
                        break;
                }
            }
        });
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd() {
        // 创建开屏容器2
        final RelativeLayout splashLayout = findViewById(R.id.rl_splash);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.view_divider);

        // 对开屏进行设置
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        // // 设置是否展示失败自动跳转，默认自动跳转
        // splashViewSettings.setAutoJumpToTargetWhenShowFailed(false);
        // 设置跳转的窗口类
        splashViewSettings.setTargetClass(MainActivity.class);
        // 设置开屏的容器
        splashViewSettings.setSplashViewContainer(splashLayout);

        // 展示开屏广告
        SpotManager.getInstance(mContext).showSplash(mContext, splashViewSettings, new SpotListener() {

            @Override
            public void onShowSuccess() {
                logInfo("开屏展示成功");
            }

            @Override
            public void onShowFailed(int errorCode) {
                logError("开屏展示失败");
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        logError("网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        logError("暂无开屏广告");
                        break;
                    case ErrorCode.RESOURCE_NOT_READY:
                        logError("开屏资源还没准备好");
                        break;
                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                        logError("开屏展示间隔限制");
                        break;
                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                        logError("开屏控件处在不可见状态");
                        break;
                    default:
                        logError("errorCode: %d", errorCode);
                        break;
                }
            }

            @Override
            public void onSpotClosed() {
                logDebug("开屏被关闭");
            }

            @Override
            public void onSpotClicked(boolean isWebPage) {
                logDebug("开屏被点击");
                logInfo("是否是网页广告？%s", isWebPage ? "是" : "不是");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 开屏展示界面的 onDestroy() 回调方法中调用
        SpotManager.getInstance(mContext).onDestroy();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        runApp();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
    }
}
