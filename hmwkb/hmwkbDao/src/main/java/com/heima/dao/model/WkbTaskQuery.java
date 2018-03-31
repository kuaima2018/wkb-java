package com.heima.dao.model;

import com.heima.model.WkbTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbTaskQuery extends WkbTask {
    private List<Integer> uIdList;
    private Date dateFrom;
    private Date dateEnd;
    private Integer startPos;
    private Integer endPos;
    private Integer queryType;//1-发送 2-接收 null-全部
    /**
     * 是否查询未读数量
     */
    private Boolean queryNewCount;

    public Integer gettPerfm() {
        return tPerfm;
    }

    public void settPerfm(Integer tPerfm) {
        this.tPerfm = tPerfm;
    }

    private Integer tPerfm;

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }


    public WkbTaskQuery()
    {
        uIdList=new ArrayList<Integer>();
        this.settIscomplete(null);
        this.settIsimportt(null);

    }
    public List<Integer> getuIdList() {
        return uIdList;
    }

    public void setuIdList(List<Integer> uIdList) {
        this.uIdList = uIdList;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public void setEndPos(Integer endPos) {
        this.endPos = endPos;
    }

    public Boolean getQueryNewCount() {
        return queryNewCount;
    }

    public void setQueryNewCount(Boolean queryNewCount) {
        this.queryNewCount = queryNewCount;
    }

}
