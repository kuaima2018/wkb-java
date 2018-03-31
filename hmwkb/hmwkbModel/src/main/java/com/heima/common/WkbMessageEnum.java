package com.heima.common;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum WkbMessageEnum {
    SYSTEM_ERROR("sys_001","系统错误"),
    USER_NO_ID("user_001","未提供用户标识"),
    USER_NO_NAME("user_002","未提供用户名称"),
    USER_NO_PASS("user_003","未提供密码"),
    USER_PASS_UNFORMAT("user_004","用户密码错误"),
    USER_NO_EXIST("user_005","用户不存在"),
    USER_USER_PASS_UNMATCH("user_006","用户密码错误"),
    USER_APPLY_ERROR("user_007","未能申请加入公司"),
    USER_JOINT_OTHER("user_008","已经加入其他公司"),
    USER_APPLY_OTHER("user_009","已经申请加入其他公司"),
    USER_FRIEND_EXIST("user_010","已成好友"),
    USER_APPLY_NO_EXIST("user_011","没有好友请求信息"),
    USER_APPLY_NO_TARGET("user_012","请求加入的公司或者用户无效"),
    USER_NO_COMPANY("user_013","用户未加入公司"),
    USER_REST_NO_MOBILE("user_014","未提供手机号"),
    USER_ADD_ALREADY_EXIST_MOBILE("user_015","手机号对应用户已存在"),

    TASK_NO_RECEIVER("task_001","未选择接收人"),
    TASK_NO_ID("task_002","未提供任务标识"),
    TASK_NO_SENDER("task_003","未提供任务发送者"),
    TASK_COMPLETE_USER_ERROR("task_004","只有发送者才能结束任务"),
    TASK_NO_EXIST("task_005","任务不存在"),
    TASK_DELETE_USER_ERROR("task_006","非任务用户不能删除"),
    TASK_FILE_NO_EXIST("task_007","任务文件不存在"),

    USER_NO_LOGIN("user_100","用户未登陆"),

    ORA_EXIST_ERROR("org_001","公司部门已经存在"),
    ORA_NO_PARENT("org_002","上级部门不存在"),
    ORA_PARENT_INVALID("org_003","指定的上级部门不存在"),
    ORA_NOT_EXIST("org_004","部门不存在"),

    COMPANY_APPLY_NAME_INVALID("cpy_001","未提供公司名称或代码"),
    COMPANY_APPLY_NAME_EXIST("cpy_002","公司名称或代码申请已经存在"),
    COMPANY_APPLY_NO_EXIST("cpy_003","公司申请信息不存在"),
    COMPANY_APPLY_HANDLED("cpy_004","公司申请信息已经处理过了"),
    COMPANY_NAME_EXIST("cpy_005","该公司已经存在"),
    COMPANY_NOT_EXIST("cpy_006","公司不存在"),

    ROLE_NOT_EXIST("role_001","岗位不存在"),
    ROLE_EXIST_ERROR("role_002","岗位已经存在");

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String desc;
    private String code;

    WkbMessageEnum(String code, String desc) {
        this.code=code;
        this.desc=desc;
    }

    public static WkbMessageEnum getEntry(String code)
    {
        if(StringUtils.isEmpty(code))
        {
            return null;
        }
        for(WkbMessageEnum wkbMessageEnum:WkbMessageEnum.values())
        {
            if(wkbMessageEnum.getCode().equals(code))
            {
                return wkbMessageEnum;
            }
        }
        return null;
    }



    //public final static String SYSTEM_ERROR="sys_001";

    //public final static String USER_ADD_NO_ID="user_001";
    //public final static String USER_ADD_NO_NAME="user_002";
    //public final static String USER_ADD_NO_PASS="user_003";
    //public final static String USER_ADD_PASS_UNFORMAT="user_004";
    //public final static String USER_ADD_USER_NO_EXIST="user_005";
    //public final static String USER_ADD_USER_PASS_UNMATCH="user_006";
}
