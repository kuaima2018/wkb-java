package com.heima.service.biz;

import com.heima.model.WkbTask;
import com.heima.model.WkbTaskdetail;
import com.heima.model.WkbTaskfile;
import com.heima.model.WkbTaskuser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbTaskExt extends WkbTask {
    public WkbTaskExt()
    {
        this.wkbTaskdetailList=new ArrayList<WkbTaskdetail>();
    }

    public List<WkbTaskdetail> getWkbTaskdetailList() {
        return wkbTaskdetailList;
    }

    public void setWkbTaskdetailList(List<WkbTaskdetail> wkbTaskdetailList) {
        this.wkbTaskdetailList = wkbTaskdetailList;
    }

    private List<WkbTaskdetail> wkbTaskdetailList;
}
