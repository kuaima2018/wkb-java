package com.chinadrtv.common.pagination;

/**
 * oracle数据库分页类
 *
 */
public class OracleDialect extends BaseDialect {

    public String getLimitString(String sql, int offset, int maxRow) {
        sql = trim(sql);
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }

        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        if (offset > 0) {
            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        } else {
            pagingSelect.append("select * from ( ");
        }

        pagingSelect.append(sql);

        if (offset > 0) {
            pagingSelect.append(" ) row_ ) where rownum_ <= ").append(offset + maxRow)
                .append(" and rownum_ > ").append(offset);
        } else {
            pagingSelect.append(" ) where rownum <= " + maxRow);
        }

        if (isForUpdate) {
            pagingSelect.append(" for update");
        }

        return pagingSelect.toString();
    }

}
