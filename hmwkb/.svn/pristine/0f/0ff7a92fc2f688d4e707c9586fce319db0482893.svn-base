package com.chinadrtv.common.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.PersistenceExceptionTranslator;


public class BaseSqlSessionTemplate extends SqlSessionTemplate {

    protected final Log logger = LogFactory.getLog(BaseSqlSessionTemplate.class);
    

    /**
     * @param sqlSessionFactory
     * @param executorType
     * @param exceptionTranslator
     */
    public BaseSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType,
                                  PersistenceExceptionTranslator exceptionTranslator) {
        super(sqlSessionFactory, executorType, exceptionTranslator);
    }

    /**
     * @param sqlSessionFactory
     * @param executorType
     */
    public BaseSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType) {
        super(sqlSessionFactory, executorType);
    }

    /**
     * @param sqlSessionFactory
     */
    public BaseSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    /** 
     * @see org.mybatis.spring.SqlSessionTemplate#close()
     */
    @Override
    public void close() {
        try {
            super.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
}
