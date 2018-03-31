package com.chinadrtv.common.test;

import com.chinadrtv.common.exception.BaseTestingException;

/**
 * 简单扩展平台测试基类
 * 
 * @author GaoPeng
 * @version $Id: BaseFunctionalitySimpleTests.java, v 0.1 2013-8-5 上午10:44:17 GaoPeng Exp $
 */
public class BaseFunctionalitySimpleTests extends BaseFunctionalityTests {

    /** 
     * @see com.chinadrtv.common.test.BaseFunctionalityTests#before()
     */
    @Override
    protected void before() throws BaseTestingException {
        //SOMETHING
    }


    @Override
    protected void after() throws BaseTestingException {
        //SOMETHING
    }


    @Override
    protected boolean isUniqueContext() {
        return false;
    }

}
