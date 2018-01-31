package com.xspace.layout;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xspace.module.ModuleParser;
import com.xspace.module.PageModule;
import com.xspace.module.TemplateModule;
import com.xspace.net.NetUtils;
import com.xspace.net.VersionUtils;
import com.xspace.ui.jumputils.AddressManager;
import com.xspace.ui.jumputils.CategoryUtil;
import com.xspace.utils.MyFileUtils;
import com.xspace.utils.NetAddressManager;
import com.xspace.utils.VideoScanUtils;

import demo.pplive.com.xspace.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentUser extends Fragment implements View.OnClickListener
{
    private static int viewFrom = -1;

    private TextView historycount;

    private View email;

    private PageModule localpage = new PageModule();

    private PageModule pageModule;

    private TextView emailTxt;

    private ClipboardManager cm;

    private View rootView;

    private TextView lastversion;

    private View source;

    private boolean isClickUpdata = false;

    private View favorite;

    private TextView localvideos;

    private View local;

    private View about;

    private View setting;

    private View updata;

    private View recommend;

    private String url;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case NetUtils.REQUEST_PAGEMODULE_OK:
                    pageModule = ModuleParser.getPageModules(msg.obj.toString());
                    showUpdata(pageModule);
                    break;
                case NetUtils.REQUEST_PAGEMODULE_FAIL:
                    if (isClickUpdata)
                    {
                        Toast.makeText(getContext(), "更新失败请检查网络!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_user, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView()
    {
        cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        url = NetAddressManager.root_website + NetAddressManager.updata + "?appVer="
                + VersionUtils.getAppVersionName(getContext());
        email = rootView.findViewById(R.id.email);
        lastversion = rootView.findViewById(R.id.lastversion);
        lastversion.setVisibility(View.GONE);

        emailTxt = rootView.findViewById(R.id.email_txt);
        historycount = rootView.findViewById(R.id.historycount);
        source = rootView.findViewById(R.id.scource);
        favorite = rootView.findViewById(R.id.favorite);
        local = rootView.findViewById(R.id.local);
        about = rootView.findViewById(R.id.about);
        setting = rootView.findViewById(R.id.setting);
        localvideos = rootView.findViewById(R.id.text_localcount);
        localvideos.setText("0");
        updata = rootView.findViewById(R.id.updata_software);
        recommend = rootView.findViewById(R.id.recommend);
        email.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                addToClipBord();
                return true;
            }
        });

        source.setOnClickListener(this);
        favorite.setOnClickListener(this);
        local.setOnClickListener(this);
        about.setOnClickListener(this);
        setting.setOnClickListener(this);
        updata.setOnClickListener(this);
        recommend.setOnClickListener(this);
        isClickUpdata = false;
        ApplyNetGson(handler, url);
        scanLocalVideo();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.scource:
                jumpToSource();
                break;
            case R.id.favorite:
                jumpToFavorite();
                break;
            case R.id.local:
                jumpToLocal();
                break;
            case R.id.about:
                jumpToAbout();
                break;
            case R.id.setting:
                jumpToSetting();
                break;
            case R.id.updata_software:
                jumpToUpdata();
                break;
            case R.id.recommend:
                jumpToRecommend();
                break;
            default:
                break;
        }
    }

    private void scanLocalVideo()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                List<TemplateModule> videos = new ArrayList<>();
                videos = VideoScanUtils.getVideoFile(videos, Environment.getExternalStorageDirectory());
                localpage.templateModules = (ArrayList<TemplateModule>) videos;
                localpage.title = "本地视频";
                localpage.page = "local";
                localvideos.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        localvideos.setText(localpage.templateModules == null ? "0"
                                : String.valueOf(localpage.templateModules.size()));
                        Log.d("jixiongxu",
                                "Serializable2Local" + MyFileUtils.Serializable2Local("localVideo", localpage));
                    }
                });
                historycount.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        PageModule history = (PageModule) MyFileUtils.getSerializableFromLocal("history");
                        if (history == null || history.templateModules == null)
                        {
                            historycount.setText("0");
                        }
                        else
                        {
                            historycount.setText(String.valueOf(history.templateModules.size()));
                        }
                    }
                });
            }
        }).start();
    }

    @SuppressLint("SetTextI18n")
    private void showUpdata(final PageModule module)
    {
        if (module == null)
        {
            return;
        }
        if (!VersionUtils.getAppVersionName(getContext()).equals(module.title))
        {
            lastversion.setVisibility(View.VISIBLE);
            lastversion.setText("最新版本:" + module.title);
            if (isClickUpdata)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("软件更新");
                builder.setMessage("立即更新软件到:" + module.title + "  版本");
                builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        try
                        {
                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse((module.message) == null ? "" : module.message));
                            intent.addCategory("android.intent.category.DEFAULT");
                            getContext().startActivity(intent);
                        }
                        catch (ActivityNotFoundException e)
                        {
                            Toast.makeText(getContext(), "下载失败  未知错误", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(getContext(), "已取消", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();
            }
        }
        else
        {
            if (isClickUpdata)
            {
                lastversion.setVisibility(View.GONE);
                Toast.makeText(getContext(), "已经是最新版本", Toast.LENGTH_SHORT).show();
            }
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

    private void jumpToRecommend()
    {
        TemplateModule item = new TemplateModule();
        item.type = "native";
        item.link = AddressManager.Native_Recommend;
        CategoryUtil.jumpByTargetLink(getContext(), item, viewFrom);
    }

    private void addToClipBord()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("联系作者");
        builder.setMessage("正在复制邮箱信息：" + emailTxt.getText().toString());
        builder.setPositiveButton("复制", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                ClipData myClip;
                myClip = ClipData.newPlainText("e-mail", emailTxt.getText().toString());
                cm.setPrimaryClip(myClip);
                Toast.makeText(getContext(), "邮箱：" + emailTxt.getText().toString() + "已复制", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(getContext(), "已取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    private void jumpToUpdata()
    {
        isClickUpdata = true;
        ApplyNetGson(handler, url);
    }

    private void jumpToSetting()
    {
        TemplateModule item = new TemplateModule();
        item.type = "native";
        item.link = AddressManager.Native_Setting;
        CategoryUtil.jumpByTargetLink(getContext(), item, viewFrom);
    }

    private void jumpToAbout()
    {
        TemplateModule item = new TemplateModule();
        item.type = "native";
        item.link = AddressManager.Native_About;
        CategoryUtil.jumpByTargetLink(getContext(), item, viewFrom);
    }

    private void jumpToLocal()
    {
        TemplateModule item = new TemplateModule();
        item.type = "native";
        item.link = AddressManager.Native_Local;
        CategoryUtil.jumpByTargetLink(getContext(), item, viewFrom);
    }

    private void jumpToFavorite()
    {
        TemplateModule item = new TemplateModule();
        item.type = "native";
        item.link = AddressManager.Native_History;
        CategoryUtil.jumpByTargetLink(getContext(), item, viewFrom);
    }

    private void jumpToSource()
    {
        TemplateModule item = new TemplateModule();
        item.type = "web";
        item.url = NetAddressManager.myGithub;
        CategoryUtil.jumpByTargetLink(getContext(), item, viewFrom);
    }
}
