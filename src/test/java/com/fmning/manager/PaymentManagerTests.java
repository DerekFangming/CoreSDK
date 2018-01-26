package com.fmning.manager;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fmning.service.domain.Payment;
import com.fmning.service.manager.PaymentManager;
import com.fmning.util.PaymentType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class PaymentManagerTests {

	@Autowired private PaymentManager paymentManager;
	
	@BeforeClass
    public static void setUpBaseClass() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	@Test
	public void testGetPaymentById(){
		Payment payment = paymentManager.getPaymentById(1);
		assertEquals(payment.getMappingId(), 2);
		assertEquals(payment.getPayerId(), 11);
	}
	
	@Test
	public void testGetPaymentByType(){
		Payment payment = paymentManager.getPaymentByType(PaymentType.EVENT.getName(), 2);
		assertEquals(payment.getPayerId(), 11);
		assertEquals(payment.getReceiverId(), 11);
	}
	
	@Test
	public void testGetPaymentCountByType(){
//		int count = paymentManager.getPaidUsersCountByType(PaymentType.EVENT.getName(), 2);
//		assertEquals(count, 4);

		int count = paymentManager.getPaidUsersCountByType(PaymentType.EVENT.getName(), 9999);
		assertEquals(count, 0);
	}
}
