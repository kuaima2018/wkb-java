package com.chinadrtv.common.log;

public enum LOG_TYPE {

    ACORN_BIZ("acorn.biz"),
    ACORN_SERVICE("acorn.service"),
    ACORN_COMMON("acorn.common");
    /*
    ACORN_CACHE("paff.cache"),
    ACORN_SQL("paff.sql");
    */
    public String val;

    private LOG_TYPE(String val) {
        this.val = val;
    }
}
