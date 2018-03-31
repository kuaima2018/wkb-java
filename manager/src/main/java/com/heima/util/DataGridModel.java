package com.heima.util;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@SuppressWarnings({ "serial", "unused" })
public class DataGridModel implements Serializable {

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    private int page = 1;// 当前页
    private int rows = 10;// 当前页条数
    private int count;// 总记录数
    private int startRow;// 开始行
    private int endRow;//结束行

    public int getEndRow() {
        return this.getStartRow()+rows-1;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStartRow() {
        return rows * (page - 1) + 1;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

}

