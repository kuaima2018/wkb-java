package com.chinadrtv.common.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称
 * 功能描述：
 * User: leo
 * Date: 13-10-9
 * Time: 下午4:07
 */
public class PaginationBean<T> {

    /** 是否还有上一页 */
    private boolean hasPrePage;

    /** 是否有下一页 */
    private boolean hasNextPage;

    private String id;

    private int totalRecords;

    private List<T> pageList;

    private int pageSize;

    private int totalPage;

    private int currentPage;

    /** 位移的数 */
    private int beginIndex;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int i) {
        pageSize = i;
    }

    public PaginationBean(Page page, int i) {
        pageSize = 0;
        totalPage = 0;
        currentPage = 0;
        init(page, i);
    }

    public PaginationBean() {
        pageSize = 0;
        totalPage = 0;
        currentPage = 0;
    }

    public void init() {
        pageList = new ArrayList<T>();
        createPage(currentPage, totalRecords, pageSize);
    }

    public static int getIntParameter(Object object, int i) {
        if (object == null) {
            return i;
        }
        try {
            String s1 = ((String[]) object)[0];
            return Integer.parseInt(s1);
        } catch (Exception e) {
            try {
                return Integer.parseInt((String) object);
            } catch (Exception e1) {
                return (Integer) object;
            }
        }
    }

    private void init(Page page, int i) {
        currentPage =page.getCurrPage();
        pageSize = page.getPageSize();

        totalRecords = i;
        pageList = new ArrayList<T>();
        createPage(currentPage, totalRecords, pageSize);
    }

    public void createPage(int currentPage, int totalRecords, int pageSize) {

        beginIndex = getBeginIndex(pageSize, currentPage);

        totalPage = getTotalPage(pageSize, totalRecords);

        hasNextPage = hasNextPage(currentPage, totalPage);

        hasPrePage = hasPrePage(currentPage);

        this.currentPage = getCurrentPage(currentPage, totalPage);

    }

    private static int getCurrentPage(int currentPage, int totalPage) {
        if (currentPage > totalPage) {
            if (totalPage == 0) {
                return currentPage;
            }
            return totalPage;
        }
        return currentPage == 0 ? 1 : currentPage;
    }

    private static int getBeginIndex(int everyPage, int currentPage) {
        return (currentPage - 1) * everyPage;
    }

    private static int getTotalPage(int everyPage, int totalRecords) {
        int totalPage = 0;

        if (totalRecords % everyPage == 0)
            totalPage = totalRecords / everyPage;
        else
            totalPage = totalRecords / everyPage + 1;

        return totalPage;
    }

    private static boolean hasPrePage(int currentPage) {
        return currentPage == 1 ? false : true;
    }

    private static boolean hasNextPage(int currentPage, int totalPage) {
        return currentPage == totalPage || totalPage == 0 ? false : true;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int i) {
        totalRecords = i;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> list) {
        pageList = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int i) {
        currentPage = i;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int i) {
        totalPage = i;
    }

    public String getId() {
        return id;
    }

    public void setId(String s) {
        id = s;
    }

    /**
     * @return the beginIndex
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * @param beginIndex
     *            the beginIndex to set
     */
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    /**
     * @return the hasNextPage
     */
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    /**
     * @param hasNextPage
     *            the hasNextPage to set
     */
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    /**
     * @return the hasPrePage
     */
    public boolean isHasPrePage() {
        return hasPrePage;
    }

    /**
     * @param hasPrePage
     *            the hasPrePage to set
     */
    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }
}

