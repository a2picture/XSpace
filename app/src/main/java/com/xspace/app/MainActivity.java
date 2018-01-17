package com.xspace.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.xspace.layout.FragmentFind;
import com.xspace.layout.FragmentHome;
import com.xspace.layout.FragmentHot;
import com.xspace.layout.FragmentUser;
import com.xspace.ui.view.NoScrollViewPage;

import java.util.ArrayList;
import java.util.List;

import demo.pplive.com.xspace.R;

public class MainActivity extends BaseActivity
{
    private static String TAG = "";

    private NoScrollViewPage mainContainer;

    private BottomNavigationView navTab;

    private FragmentAdapter mAdapter;

    private FragmentHome home;

    private FragmentHot hot;

    private FragmentFind find;

    private FragmentUser user;

    private List<Fragment> fragments;

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
        builder.setPositiveButton("Fuck Off", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finish();
            }
        });
        builder.setNegativeButton("再看看", null);
        builder.show();
    }

    private void initView()
    {
        mainContainer = findViewById(R.id.main_container);
        navTab = findViewById(R.id.nav_tab);
        fragments = new ArrayList<>();
        home = new FragmentHome();
        hot = new FragmentHot();
        find = new FragmentFind();
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
