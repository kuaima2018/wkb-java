package com.chinadrtv.common.test;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.Driver;
import org.h2.tools.RunScript;
import org.h2.tools.Server;
import org.h2.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.chinadrtv.common.exception.BaseTestingException;


public abstract class BaseH2DatabaseClient implements BeanPostProcessor, ApplicationContextAware {

    private Server                  server;
    private DriverManagerDataSource dataSource;
    private String                  serverParams       = "-tcpAllowOthers";
    private boolean                 isMemoryDataSource = true;
    private ApplicationContext      context;
    protected final Log             logger = LogFactory.getLog(getClass());

    public final void initialize() throws BaseTestingException {
        if (!isMemoryDataSource) {
            return;
        }
        try {
            if (null == dataSource) {
                throw new Exception("Error: memory database datasource is null!");
            } else {
                Driver.load();
                if (serverParams != null) {
                    String[] params = StringUtils.arraySplit(serverParams, ' ', true);
                    server = Server.createTcpServer(params);
                    server.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseTestingException(e.getMessage(), e);
        }
    }

    public final void executeScript(String filePath, String dbscript, Class<?> clz) throws BaseTestingException {
        if (server.isRunning(true)) {
            String url = dataSource.getUrl();
            String user = dataSource.getUsername();
            String password = dataSource.getPassword();
            StringBuffer path = new StringBuffer();
            if (null != dbscript && !dbscript.isEmpty()) {
                if (null != filePath && !filePath.isEmpty()){
                    path.append(filePath).append(dbscript);
                } else {
                    URL scriptUri = clz.getResource(dbscript);
                    if (null == scriptUri) {
                        logger.warn("Warning: " + dbscript + " database script not found!");
                        return;
                    } else {
                        path.append(scriptUri.getPath().substring(1));
                    }
                }
            } else {
                logger.warn("Warning: " + dbscript + " database script not found!");
                return;
            }
            try {
                if (!new File(path.toString()).exists()){
                    logger.warn("Warning: This " + path.toString() + " path database script not found!");
                    return;
                }
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
                return;
            }
            try {
                new RunScript().runTool("-url", url, "-user", user, "-password", password, "-script",
                    path.toString());
            } catch (SQLException e) {
                throw new BaseTestingException(e.getMessage(), e);
            }
        }
    }

    public void destroy() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }

    protected abstract void preTestingApplicationContext(Object bean, Class<?> clz) throws TestingBeansException;
    
    protected abstract void postTestingApplicationContext(Object bean, Class<?> clz)  throws TestingBeansException;

    /**
     * Setter method for property <tt>isMemoryDataSource</tt>.
     * 
     * @param isMemoryDataSource value to be assigned to property isMemoryDataSource
     */
    public void setMemoryDataSource(boolean isMemoryDataSource) {
        this.isMemoryDataSource = isMemoryDataSource;
    }

    /**
     * Getter method for property <tt>isMemoryDataSource</tt>.
     * 
     * @return property value of isMemoryDataSource
     */
    protected boolean isMemoryDataSource() {
        return isMemoryDataSource;
    }

    /**
     * Setter method for property <tt>dataSource</tt>.
     * 
     * @param dataSource value to be assigned to property dataSource
     */
    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /** 
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public final Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        preTestingApplicationContext(bean, bean.getClass());
        return bean;
    }

    /** 
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public final Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        postTestingApplicationContext(bean, bean.getClass());
        return bean;
    }

    /** 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Getter method for property <tt>context</tt>.
     * 
     * @return property value of context
     */
    protected ApplicationContext getContext() {
        return context;
    }

    /**
     * Bean Exception
     * 
     * @author GaoPeng
     * @version $Id: BaseH2DatabaseClient.java, v 0.1 2013-8-5 上午10:26:51 GaoPeng Exp $
     */
    protected final class TestingBeansException extends BeansException {

        /** Serial Version UID */
        private static final long serialVersionUID = 1206034163448093806L;

        /**
         * @param msg
         * @param cause
         */
        public TestingBeansException(String msg, Throwable cause) {
            super(msg, cause);
        }

        /**
         * @param msg
         */
        public TestingBeansException(String msg) {
            super(msg);
        }
    }
}