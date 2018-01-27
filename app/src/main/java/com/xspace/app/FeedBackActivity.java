package com.xspace.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.xspace.module.ModuleParser;
import com.xspace.module.PageModule;
import com.xspace.net.NetUtils;
import com.xspace.net.VersionUtils;
import com.xspace.ui.jumputils.AddressManager;
import com.xspace.utils.NetAddressManager;

import demo.pplive.com.xspace.R;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("反馈失败");
        builder.setMessage("哎呀、不知道怎么就出错了π_π");
        builder.setPositiveButton("再试一次", null);
        builder.show();
    }

    private void showFeedBackOk()
    {
        if (pageModule == null || pageModule.errorCode != 200)
        {
            showFeedBackFail();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("反馈完成");
        builder.setMessage(getResources().getString(R.string.app_name) + "\u3000已经查收、正在安排FBI信息特工查看您的意见");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finish();
            }
        });
        builder.show();
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
                String rec = editRecoment.getText().toString();
                String user = editConnect.getText().toString();
                if ("".equals(rec))
                {
                    Toast.makeText(context, "请填写反馈内容", Toast.LENGTH_SHORT).show();
                    break;
                }
                if ("".equals(user))
                {
                    Toast.makeText(context, "请填写您的联系方式", Toast.LENGTH_SHORT).show();
                    break;
                }
                String url = NetAddressManager.root_website + NetAddressManager.feedback + "?content=" + rec + "&user="
                        + user + "&appVer=" + VersionUtils.getAppVersionName(context);
                submitRecoment(handler, url);
                break;
            default:
                break;
        }
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
