package com.chinadrtv.common.pagination;

/**
 * H2数据库方言类
 * @author huanghui
 * @version $Id: H2Dialect.java, v 0.1 2013-7-23 上午11:50:29 huanghui Exp $
 */
public class H2Dialect extends BaseDialect {

   
    @Override
    public String getLimitString(String sql, int offset, int maxRow) {
        sql = trim(sql);
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 40).append(sql);
        if(offset > 0){
            return pagingSelect.append(" limit ").append(maxRow).append(" offset ").append(offset).toString();
        }else {
            return pagingSelect.append(" limit ").append(maxRow).toString();
        }
    }

}
