package com.uniFun.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.uniFun.R;

/**
 * Created by jixiongxu on 2018/2/8.
 */

public class MyDialog extends Dialog implements View.OnClickListener
{
    private TextView contentTxt;

    private TextView titleTxt;

    private TextView submitTxt;

    private TextView cancelTxt;

    private Context mContext;

    private String content;

    private OnCloseListener listener;

    private int gravity = Gravity.CENTER;

    private String positiveName;

    private String negativeName;

    private String title;

    public MyDialog(Context context)
    {
        super(context);
        this.mContext = context;
    }

    public MyDialog(Context context, int themeResId, String content)
    {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public MyDialog(Context context, int themeResId, String content, OnCloseListener listener)
    {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public void setOnCloseListener(OnCloseListener listener)
    {
        this.listener = listener;
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public MyDialog setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public MyDialog setPositiveButton(String name)
    {
        this.positiveName = name;
        return this;
    }

    public MyDialog setNegativeButton(String name)
    {
        this.negativeName = name;
        return this;
    }

    public void setContentTxtGravity(int g)
    {
        this.gravity = g;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView()
    {
        contentTxt = findViewById(R.id.content);
        titleTxt = findViewById(R.id.title);
        submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);
        contentTxt.setGravity(gravity);
        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName))
        {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName))
        {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title))
        {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.cancel:
                if (listener != null)
                {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if (listener != null)
                {
                    listener.onClick(this, true);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener
    {
        void onClick(Dialog dialog, boolean confirm);
    }
}
