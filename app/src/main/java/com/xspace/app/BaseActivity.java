package com.xspace.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xspace.module.TemplateModule;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity
{
    protected Context context;

    protected TemplateModule module;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.context = this;
        module = (TemplateModule) getIntent().getSerializableExtra("module");
    }

    protected void show(String gson)
    {
    }

    protected void showEmpty()
    {
    }

}
