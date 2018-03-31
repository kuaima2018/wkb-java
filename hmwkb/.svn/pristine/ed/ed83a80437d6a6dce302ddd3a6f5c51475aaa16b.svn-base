package com.chinadrtv.common.pagination;

import java.io.Serializable;

/**
 * 类名称
 * 功能描述：
 * User: leo
 * Date: 13-10-9
 * Time: 下午1:24
 */
public class Page implements Serializable {
    /**  */
    private static final long serialVersionUID = -2332161085849113773L;

    private int               pageSize;

    private int               totalPage;

    private int               currPage         = 1;

    private int               totalRecords;

    public Page(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currPage = currentPage;
    }

    public Page() {

    }

    /**
     * 获取显示的数据数量
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置显示数据数量
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 设置总页数
     *
     * @param totalPage
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 获取当前页
     *
     * @return
     */
    public int getCurrPage() {
        return currPage;
    }

    /**
     * 设置当前页
     *
     * @param currPage
     */
    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    /**
     * 获取总记录条数
     *
     * @return
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * 获取总记录条数
     *
     * @param totalRecords
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    @Override
    public String toString() {
        return "Page [pageSize=" + pageSize + ", totalPage=" + totalPage + ", currPage=" + currPage
                + ", totalRecords=" + totalRecords + "]";
    }

}
