package com.uniFun;

import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uniFun.module.ModuleParser;
import com.uniFun.module.PageModule;
import com.uniFun.net.NetUtils;
import com.uniFun.net.VersionUtils;
import com.uniFun.ui.jumputils.AddressManager;
import com.uniFun.ui.view.MyDialog;
import com.uniFun.utils.NetAddressManager;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView back;

    private TextView titleTxt;

    private Button submit;

    private PageModule pageModule;

    private EditText editConnect;

    private EditText editRecoment;

    private ProgressBar loading;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            loading.setVisibility(View.GONE);
            switch (msg.what)
            {
                case NetUtils.REQUEST_PAGEMODULE_OK:
                    pageModule = ModuleParser.getPageModules((String) msg.obj);
                    showFeedBackOk();
                    break;
                case NetUtils.REQUEST_PAGEMODULE_FAIL:
                    showFeedBackFail();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView()
    {
        titleTxt = findViewById(R.id.title_txt);
        back = findViewById(R.id.img_back);
        submit = findViewById(R.id.submit);
        editConnect = findViewById(R.id.editConnect);
        editRecoment = findViewById(R.id.editRecoment);
        loading = findViewById(R.id.loading);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        if (module != null && module.link != null && !"".equals(module.link))
        {
            if (AddressManager.Native_FeedBack.equals(module.link))
            {
                titleTxt.setText("用户反馈");
            }
        }
    }

    private void showFeedBackFail()
    {
        MyDialog dialog = new MyDialog(context, R.style.dialog, "客观再玩一会嘛,咩..^_^");
        dialog.setTitle("反馈失败");
        dialog.setPositiveButton("再试一次");
        dialog.setNegativeButton("取消");
        dialog.setOnCloseListener(new MyDialog.OnCloseListener()
        {
            @Override
            public void onClick(Dialog dialog, boolean confirm)
            {
                if (confirm)
                {
                    submit();
                }
            }
        });
        dialog.show();
    }

    private void showFeedBackOk()
    {
        if (pageModule == null || pageModule.errorCode != 200)
        {
            showFeedBackFail();
            return;
        }
        MyDialog dialog = new MyDialog(context, R.style.dialog, "\u3000已经查收、正在安排FBI信息特工查看您的意见");
        dialog.setTitle("反馈完成");
        dialog.setPositiveButton("确定");
        dialog.setNegativeButton("取消");
        dialog.setOnCloseListener(new MyDialog.OnCloseListener()
        {
            @Override
            public void onClick(Dialog dialog, boolean confirm)
            {
                finish();
            }
        });
        dialog.show();
    }

    @Override
    protected void show(String gson)
    {
        super.show(gson);
    }

    @Override
    protected void showEmpty()
    {
        super.showEmpty();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.submit:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit()
    {
        String rec = Uri.encode(editRecoment.getText().toString(), "iso-8859-1");
        String user = Uri.encode(editConnect.getText().toString(), "iso-8859-1");
        if ("".equals(rec))
        {
            Toast.makeText(context, "请填写反馈内容", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(user))
        {
            Toast.makeText(context, "请填写您的联系方式", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = NetAddressManager.root_website + NetAddressManager.feedback + "?content=" + rec + "&user=" + user
                + "&appVer=" + VersionUtils.getAppVersionName(context);
        submitRecoment(handler, url);
    }

    private void submitRecoment(final Handler handler, final String url)
    {
        loading.setVisibility(View.VISIBLE);
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
}
