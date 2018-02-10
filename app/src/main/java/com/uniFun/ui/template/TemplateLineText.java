package com.uniFun.ui.template;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uniFun.module.BaseModule;
import com.uniFun.module.TemplateModule;
import com.uniFun.net.NetUtils;
import com.uniFun.net.VersionUtils;
import com.uniFun.ui.view.MyDialog;
import com.uniFun.utils.DisplayUtil;
import com.uniFun.utils.NetAddressManager;

import com.uniFun.R;

public class TemplateLineText extends BaseView
{
    public static final String TAG = "template_line_text";

    private boolean download_ok = true;

    private int width;

    private LinearLayout mRootView;

    public TemplateLineText(Context context)
    {
        super(context);
        initView();
    }

    private void initView()
    {
        this.setOrientation(VERTICAL);
        setPadding(0, 10, 0, 10);
        width = DisplayUtil.screenWidthPx(mContext);
        mRootView = new LinearLayout(mContext);
        mRootView.setOrientation(HORIZONTAL);
        mRootView.setLayoutParams(new LayoutParams(-1, -2));
        this.setOnClickListener(null);
    }

    @Override
    public void setData(BaseModule module)
    {
        if (module == null)
        {
            return;
        }
        this.module = module;
        fillData(module);
    }

    @Override
    public void fillData(final BaseModule module)
    {
        if (module == null || !(module instanceof TemplateModule))
        {
            return;
        }
        TextView title = new TextView(mContext);
        title.setSingleLine(true);
        title.setLayoutParams(new LayoutParams(width / 5, -1));
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setPadding(5, 0, 10, 5);
        title.setText("\u3000" + ((TemplateModule) module).title);

        View line = new View(mContext);
        line.setLayoutParams(new LayoutParams(1, -1));
        line.setBackgroundColor(Color.GREEN);
        line.setPadding(10, 0, 10, 0);

        final TextView subtitle = new TextView(mContext);
        subtitle.setPadding(10, 0, 10, 0);
        subtitle.setLayoutParams(new LayoutParams(-1, -1));
        subtitle.setText(((TemplateModule) module).subtitle);

        if (!(((TemplateModule) module).link == null || "".equals(((TemplateModule) module).link)))
        {
            subtitle.setTextColor(getResources().getColor(R.color.colorAccent));
            subtitle.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    downLoadTip();
                }
            });
        }
        mRootView.addView(title);
        mRootView.addView(subtitle);
        mRootView.addView(line, 1);
        addTemplateView();
    }

    private void downLoadTip()
    {
        MyDialog dialog = new MyDialog(mContext, R.style.dialog, "正在下载:" + ((TemplateModule) module).subtitle,
                new MyDialog.OnCloseListener()
                {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm)
                    {
                        if (confirm)
                        {
                            try
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(((TemplateModule) module).subtitle));
                                intent.addCategory("android.intent.category.DEFAULT");
                                mContext.startActivity(intent);
                                download_ok = true;
                            }
                            catch (ActivityNotFoundException e)
                            {
                                // 下载失败就反馈到数据库中
                                Toast.makeText(mContext, "下载失败  请检查是否安装迅雷", Toast.LENGTH_SHORT).show();
                                download_ok = false;
                                e.printStackTrace();
                            }
                            finally
                            {
                                if (!download_ok)
                                {
                                    String ftp_url = Uri.encode(((TemplateModule) module).subtitle == null ? ""
                                            : ((TemplateModule) module).subtitle, "iso-8859-1");
                                    String url = NetAddressManager.root_website + NetAddressManager.downloadfail
                                            + "?ftp_url=" + ftp_url + "&appVer="
                                            + VersionUtils.getAppVersionName(mContext);
                                    ApplyNetGson(new Handler(), url);
                                }
                            }
                        }
                        else
                        {
                            copyToClip(((TemplateModule) module).subtitle);
                        }
                    }
                }).setTitle("下载");
        dialog.setNegativeButton("复制链接");
        dialog.setPositiveButton("迅雷下载");
        dialog.show();
    }

    private void copyToClip(String redpay)
    {
        if (redpay == null || "".equals(redpay))
        {
            return;
        }
        ClipData myClip;
        myClip = ClipData.newPlainText("download", redpay);
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null)
        {
            cm.setPrimaryClip(myClip);
        }
        Toast.makeText(mContext, "已经复制:" + redpay + "到粘贴板", Toast.LENGTH_SHORT).show();
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

    @Override
    public void addTemplateView()
    {
        addView(mRootView);
    }

    @Override
    public void reFresh()
    {

    }
}
