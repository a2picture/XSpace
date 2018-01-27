package com.xspace.app;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.xspace.layout.FragmentFilm;
import com.xspace.layout.FragmentHome;
import com.xspace.layout.FragmentLive;
import com.xspace.layout.FragmentUser;
import com.xspace.ui.view.NoScrollViewPage;

import java.util.ArrayList;
import java.util.List;

import demo.pplive.com.xspace.R;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("客官再玩一会嘛，咩^_^");
        builder.setPositiveButton("残忍拒绝", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finish();
            }
        });
        builder.setNegativeButton("再快活@_@", null);
        builder.show();
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

        public FragmentAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void setFragments(List<Fragment> fragments)
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
