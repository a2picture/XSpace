package com.xspace.layout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xspace.module.TemplateModule;
import com.xspace.ui.jumputils.AddressManager;
import com.xspace.ui.jumputils.CategoryUtil;

import demo.pplive.com.xspace.R;

public class FragmentUser extends Fragment implements View.OnClickListener
{
    private static int viewFrom = -1;

    private View email;

    private TextView emailTxt;

    private ClipboardManager cm;

    private View mRootView;

    private View source;

    private View history;

    private View local;

    private View about;

    private View setting;

    private View updata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        return mRootView;
    }

    private void initView()
    {
        cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        email = mRootView.findViewById(R.id.email);
        emailTxt = mRootView.findViewById(R.id.email_txt);
        source = mRootView.findViewById(R.id.scource);
        history = mRootView.findViewById(R.id.history);
        local = mRootView.findViewById(R.id.local);
        about = mRootView.findViewById(R.id.about);
        setting = mRootView.findViewById(R.id.setting);
        updata = mRootView.findViewById(R.id.updata_software);

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
        history.setOnClickListener(this);
        local.setOnClickListener(this);
        about.setOnClickListener(this);
        setting.setOnClickListener(this);
        updata.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.scource:
                jumpToSource();
                break;
            case R.id.history:
                jumpToHistory();
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
            default:
                break;
        }
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
    }

    private void jumpToSetting()
    {
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

    private void jumpToHistory()
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
        item.url = "https://github.com/jixiongxu/XSpace";
        CategoryUtil.jumpByTargetLink(getContext(), item, viewFrom);
    }
}
