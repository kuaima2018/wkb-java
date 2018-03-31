package com.heima.service.biz;

import org.apache.commons.lang.StringUtils;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbBizResult {
    public WkbBizResult()
    {

    }

    public WkbBizResult(String code,String desc)
    {
        this.code=code;
        this.dsc=desc;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public boolean isSucc()
    {
        if(StringUtils.isNotEmpty(this.code))
            return false;
        return true;
    }

    private String code;
    private String dsc;
}
