package com.xspace.module;

import java.io.Serializable;

public class BaseModule implements Serializable
{
    /**
     * 序列化代码
     */
    private static final long serialVersionUID = 4095079126706689599L;

    /**
     * 接口错误码
     */
    public int errorCode;

    /**
     * 接口错误信息
     */
    public String message;

}
