package com.xspace.ui.template;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xspace.module.BaseModule;
import com.xspace.module.TemplateModule;
import com.xspace.utils.DisplayUtil;

public class TemplateLineText extends BaseView
{
    public static final String TAG = "template_line_text";

    private ClipboardManager cm;

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
        cm = (ClipboardManager) getmContext().getSystemService(Context.CLIPBOARD_SERVICE);
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
        title.setPadding(10, 0, 10, 0);
        title.setText(((TemplateModule) module).title);

        View line = new View(mContext);
        line.setLayoutParams(new LayoutParams(1, -1));
        line.setBackgroundColor(Color.GREEN);
        line.setPadding(10, 0, 10, 0);

        final TextView subtitle = new TextView(mContext);
        subtitle.setPadding(10, 0, 10, 0);
        subtitle.setLayoutParams(new LayoutParams(-1, -1));
        subtitle.setText("\u3000\u3000" + ((TemplateModule) module).subtitle);

        if (!(((TemplateModule) module).link == null || "".equals(((TemplateModule) module).link)))
        {
            subtitle.setTextColor(Color.GREEN);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getmContext());
        builder.setTitle("下载");
        builder.setMessage("正在下载：" + ((TemplateModule) module).subtitle);
        builder.setPositiveButton("下载到本地", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                ClipData myClip;
                myClip = ClipData.newPlainText("e-mail", ((TemplateModule) module).subtitle);
                cm.setPrimaryClip(myClip);
                Toast.makeText(getContext(), "已添加" + ((TemplateModule) module).subtitle + "到下载列表",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(((TemplateModule) module).subtitle));
                getmContext().startActivity(intent);

            }
        });
        builder.setNegativeButton("复制链接", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(getContext(), "已复制" + ((TemplateModule) module).subtitle + "到粘贴板",
                        Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
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
