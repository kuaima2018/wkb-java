package com.chinadrtv.common.pagination;

/**
 * 基础SQL方言
 *
 */
public abstract class BaseDialect implements Dialect {
    protected static final String SQL_END_DELIMITER = ";";

    protected String trim(String sql) {
        sql = sql.trim();
        if (sql.endsWith(SQL_END_DELIMITER)) {
            sql = sql.substring(0, sql.length() - 1 - SQL_END_DELIMITER.length());
        }
        return sql;
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }
    
    @Override
    public boolean supportOffsetLimit() {
        return true;
    }
}
