package com.chinadrtv.common.test;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;


public final class BaseH2DatabaseSimpleClient extends BaseH2DatabaseClient {

    private static final String TESTING_SPRING_KEY_SQLSESSION          = "sqlSession";
    private static final String TESTING_SPRING_KEY_SESSIONFACTORY      = "sqlSessionFactory";

    /**
     * @see com.chinadrtv.common.test.BaseH2DatabaseClient#preTestingApplicationContext(java.lang.Object, java.lang.Class)
     */
    @Override
    protected void preTestingApplicationContext(Object bean, Class<?> clz) throws TestingBeansException {
        if (isMemoryDataSource()) {
            wrapGeneralDAO(bean);
        }
    }

    /**
     * check dao and convert datasource
     * 
     * @param bean : spring object
     */
    private void wrapGeneralDAO(Object bean) throws TestingBeansException {
        if (bean instanceof SqlSessionDaoSupport && null != getContext()) {
            SqlSessionDaoSupport session = (SqlSessionDaoSupport) bean;
            Object sessionFactory = getContext().getBean(TESTING_SPRING_KEY_SESSIONFACTORY);
            if (null != sessionFactory && sessionFactory instanceof SqlSessionFactory) {
                session.setSqlSessionFactory((SqlSessionFactory) sessionFactory);
            } else {
                throw new TestingBeansException("Error: SqlSessionFactory can be not found");
            }
            Object sqlSession = getContext().getBean(TESTING_SPRING_KEY_SQLSESSION);
            if (null != sqlSession && sqlSession instanceof SqlSessionTemplate) {
                session.setSqlSessionTemplate((SqlSessionTemplate) sqlSession);
            } else
                throw new TestingBeansException("Error: SqlSessionTemplate can be not found");
        }
    }

    /**
     * @see com.chinadrtv.common.test.BaseH2DatabaseClient#postTestingApplicationContext(java.lang.Object, java.lang.Class)
     */
    @Override
    protected void postTestingApplicationContext(Object bean, Class<?> clz) throws TestingBeansException {
    }
    
}