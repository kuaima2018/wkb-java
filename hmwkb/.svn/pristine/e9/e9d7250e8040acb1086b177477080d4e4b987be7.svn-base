package com.chinadrtv.common.log;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class LogInterceptor implements Interceptor {

    // 日志对象
    protected Logger log = LoggerFactory.getLogger("acorn.sql");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql bSql = statementHandler.getBoundSql();
        Object param = statementHandler.getParameterHandler().getParameterObject();

        StringBuffer sb = new StringBuffer();
        sb.append("sql:====>\r\n" + removeBreakingWhitespace(bSql.getSql()) + "\r\n");
        List<ParameterMapping> paramList = bSql.getParameterMappings();
        for (ParameterMapping mapping : paramList) {
            String proName = mapping.getProperty();
            try {
                sb.append("[" + proName + ":" + BeanUtils.getProperty(param, proName) + "]");
            } catch (Exception e) {
                sb.append("[" + proName + ":" + param + "]");
            }
        }
        sb.append("==================================");
        log.info(sb.toString());
        return invocation.proceed();
    }

    protected String removeBreakingWhitespace(String original) {
        StringTokenizer whitespaceStripper = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        for (; whitespaceStripper.hasMoreTokens(); builder.append(" "))
            builder.append(whitespaceStripper.nextToken());
        return builder.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties arg0) {
        //Auto-generated method stub
    }
}