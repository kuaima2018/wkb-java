package com.chinadrtv.common.test;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.chinadrtv.common.exception.BaseTestingException;


@ContextConfiguration(locations = { "classpath:/testing/default-testing-config.xml" })
@TransactionConfiguration(defaultRollback = false, transactionManager = "transactionManager")
public abstract class BaseFunctionalityTests extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String         TESTING_SPRING_KEY_BASEH2DATABASECLIENT = "testingBaseH2DatabaseSimpleClient";
    private final String                initDbscript                            = getClass().getSimpleName() + ".sql";
    private final String                dropDbscript                            = "testing-h2-drop.sql";
    private static Map<Method, Boolean> methodMap                               = new HashMap<Method, Boolean>();
    private static boolean              isUniqueContext                         = true;
    private static int                  executeCount                            = 0;
    private static ApplicationContext   applicationContext;

    /**
     * 预处理（前置）
     */
    protected abstract void before() throws BaseTestingException;

    /**
     * 预处理（后置）
     */
    protected abstract void after() throws BaseTestingException;

    /**
     * 是否唯一上下文
     * 
     * @return true：是；false：否
     */
    protected abstract boolean isUniqueContext();

    @Before
    public final void defaultBefore() throws BaseTestingException {
        executeCount++;
        if (isUniqueContext) {
            methodMap.clear();
            Method[] ms = getClass().getMethods();
            for (final Method method : ms) {
                if (method.isAnnotationPresent(Test.class)) {
                    methodMap.put(method, method.isAnnotationPresent(Rollback.class));
                }
            }
            applicationContext = super.applicationContext;
            if (null != applicationContext) {
                this.before();
                BaseH2DatabaseClient client = getBean(TESTING_SPRING_KEY_BASEH2DATABASECLIENT,
                    BaseH2DatabaseSimpleClient.class);
                if (null != client) {
                    logger.info("Debug: initialize memory data...");
                    client.executeScript(null, initDbscript, this.getClass());
                    logger.info("Debug: memory data initialization is completed!");
                } else {
                    throw new BaseTestingException("Error: H2 client not found!");
                }
            }
        }
        isUniqueContext = isUniqueContext();
    }

    @After
    public final void defaultAfter() throws BaseTestingException {
        if (executeCount != 0 && methodMap.size() == executeCount) {
            logger.info("Debug: clear memory data...");
            if (null != applicationContext) {
                this.after();
                StringBuffer filePath = new StringBuffer();
                URL url = BaseH2DatabaseClient.class.getResource("");
                if (null != url) {
                    String packagePath = BaseH2DatabaseClient.class.getPackage().getName()
                        .replace('.', '/');
                    filePath.append(url.getPath().substring(1, url.getPath().indexOf(packagePath)));
                } else {
                    throw new BaseTestingException("Error: drop s;ql file path not found!");
                }
                BaseH2DatabaseClient client = getBean(TESTING_SPRING_KEY_BASEH2DATABASECLIENT,
                    BaseH2DatabaseSimpleClient.class);
                if (null != client) {
                    client.executeScript(filePath.append("testing/").toString(), dropDbscript, null);
                } else {
                    throw new BaseTestingException("Error: H2 client not found!");
                }
            }
            logger.info("Debug: memory data cleanup is completed!");
        }
    }

    /** 
     * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#countRowsInTable(java.lang.String)
     */
    @Override
    protected final int countRowsInTable(String tableName) {
        return super.countRowsInTable(tableName);
    }

    /** 
     * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#countRowsInTableWhere(java.lang.String, java.lang.String)
     */
    @Override
    protected final int countRowsInTableWhere(String tableName, String whereClause) {
        return super.countRowsInTableWhere(tableName, whereClause);
    }

    /** 
     * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#deleteFromTables(java.lang.String[])
     */
    @Override
    protected final int deleteFromTables(String... names) {
        return super.deleteFromTables(names);
    }

    /** 
     * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#dropTables(java.lang.String[])
     */
    @Override
    protected final void dropTables(String... names) {
        super.dropTables(names);
    }

    /** 
     * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#executeSqlScript(java.lang.String, boolean)
     */
    @Override
    protected final void executeSqlScript(String sqlResourcePath, boolean continueOnError)
                                                                                          throws DataAccessException {
        super.executeSqlScript(sqlResourcePath, continueOnError);
    }

    /** 
     * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#setDataSource(javax.sql.DataSource)
     */
    @Override
    public final void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /** 
     * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#setSqlScriptEncoding(java.lang.String)
     */
    @Override
    public final void setSqlScriptEncoding(String sqlScriptEncoding) {
        super.setSqlScriptEncoding(sqlScriptEncoding);
    }

    public final Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    public final <T> T getBean(String beanId, Class<T> target) {
        return applicationContext.getBean(beanId, target);
    }

}