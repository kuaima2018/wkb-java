package com.chinadrtv.common.pagination;

public interface Dialect {
    /**
     * 是否支持分页
     * 
     * @return
     */
    public boolean supportsLimit();

    /**
     * 是否支持位移分页
     * 
     * @return
     */
    public boolean supportOffsetLimit();

    /**
     * 获取分页sql
     * 
     * @param sql 原sql
     * @param offset 位移量
     * @param maxRow 最大记录数
     * @return
     */
    public String getLimitString(String sql, int offset, int maxRow);
}
