package com.chinadrtv.common;

import java.math.BigDecimal;

import org.junit.*;

import com.chinadrtv.common.money.Money;

/**
 * @author dell
 *
 */
@Ignore
public class MoneyTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		/*  Money amount2 = new Money();
		  	System.out.println(Long.MAX_VALUE/1000000);
		  	
		  
	        Money amount21 = new Money(1999999.53);
	        Money amount22 = new Money(100000.4600);
	        amount2 = amount21.add(amount22);
	        System.out.println(amount2.getYuan());*/
	        
		//
		//Money pm1 = new Money(11.11D);
	//	Money pm2 = new Money(11,11);
		//Money pm3 = pm1.add(pm2);
		/*System.out.println(pm3.getYuan());
		System.out.println(pm3.getCent());
		System.out.println(pm3.getAmount());*/
		
		//Money _pm1 = pm1.addTo(pm2);
		/*System.out.println(_pm1.getYuan());
		System.out.println(_pm1.getCent());*/
		
		//pm1.addTo(pm2);
		//System.out.println(pm1.getAmount());

	/*	System.out.println(pm3.getYuan());
		System.out.println(pm3.getCent());
		System.out.println(pm3.getAmount());*/
	}
	@Test
	public void testMultiply() {
	/*	Money pm1 = new Money(5,100);
		Money pm2 = new Money(5,100);
		Money pm3 = pm1.multiply(pm2.getAmount());*/
	/*	System.out.println(pm3.getYuan());
		System.out.println(pm3.getCent());
		System.out.println(pm3.getAmount());*/
	}
	
	@Test
	public void testDivide() {
		
		//验证是否相等
		Money pm1 = new Money(33.30000);
		Money pm2 = new Money(33,30);
		Money pm3 = new Money(new BigDecimal("33.300000000"));
		Money pm4 = new Money(new BigDecimal(33.300D));
		Assert.assertEquals(pm1, pm2);
		Assert.assertEquals(pm1, pm3);
		Assert.assertEquals(pm1, pm4);
		Assert.assertEquals(pm3, pm4);
		//验证除法
		Assert.assertEquals(new Money(new BigDecimal("1")), pm1.divide(new BigDecimal(33.30000)));
		Assert.assertEquals(new Money("33.3333333"), new Money(10,00).divide(0.3));

	}
	
	
	@Test
	public void tesmuSubtract() {
	/*	Money pm1 = new Money(10,100);
		Money pm2 = new Money(-3,200);
		Money pm3 = pm1.subtract(pm2);*/
	/*	System.out.println(pm3.getYuan());
		System.out.println(pm3.getCent());
		System.out.println(pm3.getAmount());*/
	}

}
