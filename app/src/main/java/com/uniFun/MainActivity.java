package com.uniFun;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MenuItem;

import com.uniFun.layout.FragmentFilm;
import com.uniFun.layout.FragmentHome;
import com.uniFun.layout.FragmentLive;
import com.uniFun.layout.FragmentUser;
import com.uniFun.module.ModuleParser;
import com.uniFun.module.PageModule;
import com.uniFun.net.NetUtils;
import com.uniFun.net.VersionUtils;
import com.uniFun.ui.view.MyDialog;
import com.uniFun.ui.view.NoScrollViewPage;
import com.uniFun.utils.NetAddressManager;
import com.uniFun.utils.SharedPreferencesUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
{
    private static String TAG = "MainActivity";

    private static String[] PERMISSIONS_STORAGE = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private NoScrollViewPage mainContainer;

    private BottomNavigationView navTab;

    private FragmentAdapter mAdapter;

    private FragmentHome home;

    private FragmentLive hot;

    private FragmentFilm find;

    private FragmentUser user;

    private List<Fragment> fragments;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case NetUtils.REQUEST_PAGEMODULE_OK:
                    copyToClip(msg.obj.toString());
                    break;
                case NetUtils.REQUEST_PAGEMODULE_FAIL:
                    break;
                default:
                    break;
            }
        }
    };

    private Handler handlerTip = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case NetUtils.REQUEST_PAGEMODULE_OK:
                    showTip(msg.obj.toString());
                    break;
                case NetUtils.REQUEST_PAGEMODULE_FAIL:
                    break;
                default:
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onBackPressed()
    {
        MyDialog dialog = new MyDialog(context, R.style.dialog, "客官再玩一会嘛,咩..^_^");
        dialog.setTitle("退出");
        dialog.setPositiveButton("退出");
        dialog.setNegativeButton("取消");
        dialog.setOnCloseListener(new MyDialog.OnCloseListener()
        {
            @Override
            public void onClick(Dialog dialog, boolean confirm)
            {
                if (confirm)
                {
                    finish();
                }
            }
        });
        dialog.show();
    }

    private void initView()
    {
        mainContainer = findViewById(R.id.main_container);
        navTab = findViewById(R.id.nav_tab);
        fragments = new ArrayList<>();
        home = new FragmentHome();
        hot = new FragmentLive();
        find = new FragmentFilm();
        user = new FragmentUser();
        fragments.add(home);
        fragments.add(hot);
        fragments.add(find);
        fragments.add(user);
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mAdapter.setFragments(fragments);
        mainContainer.setAdapter(mAdapter);
        mainContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                int[] resId = {R.id.nav_home, R.id.nav_hot, R.id.nav_find, R.id.nav_user};
                navTab.setSelectedItemId(resId[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
        navTab.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        mainContainer.setCurrentItem(0);
                        break;
                    case R.id.nav_hot:
                        mainContainer.setCurrentItem(1);
                        break;
                    case R.id.nav_find:
                        mainContainer.setCurrentItem(2);
                        break;
                    case R.id.nav_user:
                        mainContainer.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
        checkAndApplyPermission();
        String url = NetAddressManager.root_website + NetAddressManager.redpay + "?appVer="
                + VersionUtils.getAppVersionName(context);
        ApplyNetGson(handler, url);
        // 一周显示一次提示604800000
        if (Math.abs(System.currentTimeMillis() - SharedPreferencesUtils.getLong(context, "time")) > 604800000)
        {
            if (!SharedPreferencesUtils.getBool(context, "showTip"))
            {
                String tip_url = NetAddressManager.root_website + NetAddressManager.tip + "?appVer="
                        + VersionUtils.getAppVersionName(context);
                ApplyNetGson(handlerTip, tip_url);
            }
            else
            {
                SharedPreferencesUtils.put(context, "showTip", false);
            }
        }
    }

    private void showTip(String str)
    {
        if (str == null || "".equals(str))
        {
            return;
        }
        PageModule tipModule;
        tipModule = ModuleParser.getPageModules(str);
        if (tipModule == null || tipModule.errorCode != 200)
        {
            return;
        }
        MyDialog dialog = new MyDialog(context, R.style.dialog,
                tipModule.message == null ? "程序有未知错误" : tipModule.message);
        dialog.setTitle(tipModule.title == null ? "提示" : tipModule.title);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setPositiveButton("不再提示");
        dialog.setNegativeButton("取消");
        dialog.setOnCloseListener(new MyDialog.OnCloseListener()
        {
            @Override
            public void onClick(Dialog dialog, boolean confirm)
            {
                if (confirm)
                {
                    Context ctx = new WeakReference<>(context).get();
                    SharedPreferencesUtils.put(ctx, "showTip", true);
                    SharedPreferencesUtils.put(context, "time", System.currentTimeMillis());
                }
            }
        });
        dialog.setContentTxtGravity(Gravity.START);
        dialog.show();
    }

    private void copyToClip(String redpay)
    {
        if (redpay == null || "".equals(redpay))
        {
            return;
        }
        ClipData myClip;
        myClip = ClipData.newPlainText("redpay", redpay);
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null)
        {
            cm.setPrimaryClip(myClip);
        }
    }

    private void ApplyNetGson(final Handler handler, final String url)
    {
        if (url == null || "".equals(url))
        {
            return;
        }
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                NetUtils.getAsynPageModulekHttp(handler, url);
            }
        }).start();
    }

    // 权限申请
    public void checkAndApplyPermission()
    {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> fragments;

        FragmentAdapter(FragmentManager fm)
        {
            super(fm);
        }

        void setFragments(List<Fragment> fragments)
        {
            if (fragments != null && fragments.size() > 0)
            {
                this.fragments = fragments;
            }
        }

        @Override
        public int getCount()
        {
            if (fragments == null || fragments.size() == 0)
            {
                return 0;
            }
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            if (fragments == null || position > fragments.size())
            {
                return null;
            }
            return fragments.get(position);
        }
    }
}
