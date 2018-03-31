package com.chinadrtv.common.pagination;

/**
 * Mysql数据库分页类
 * 
 * @author huanghui
 * @version $Id: MySQLDialect.java, v 0.1 2013-7-22 下午5:22:46 huanghui Exp $
 */
public class MySql5Dialect extends BaseDialect {

    public String getLimitString(String sql, int offset, int maxRow) {
        sql = trim(sql);
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 40).append(sql);
        if (offset > 0) {
            return pagingSelect.append(" limit ").append(offset).append(", ").append(maxRow).toString();
        } else {
            return pagingSelect.append(" limit ").append(maxRow).toString();
        }
    }

}
