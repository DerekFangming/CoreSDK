package com.fmning.service.manager.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.PaymentDao;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.domain.Payment;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.PaymentManager;
import com.fmning.util.ErrorMessage;

@Component
public class PaymentManagerImpl implements PaymentManager{
	
	@Autowired private PaymentDao paymentDao;

	@Override
	public int createPayment(String type, int mappingId, double amount, String status, String message, int payerId,
			int receiverId, String method, String nonce) {
		Payment payment = new Payment();
		payment.setType(type);
		payment.setMappingId(mappingId);
		payment.setAmount(amount);
		payment.setStatus(status);
		payment.setMessage(message);
		payment.setPayerId(payerId);
		payment.setReceiverId(receiverId);
		payment.setMethod(method);
		payment.setNonce(nonce);
		payment.setCreatedAt(Instant.now());
		return paymentDao.persist(payment);
	}

	@Override
	public Payment getPaymentById(int id) throws NotFoundException {
		try{
			return paymentDao.findById(id);
		} catch (NotFoundException e){
			throw new NotFoundException(ErrorMessage.PAYMENT_NOT_FOUND.getMsg());
		}
	}

	@Override
	public Payment getPaymentByType(String type, int mappingId) throws NotFoundException {
		try {
			List<QueryTerm> values = new ArrayList<QueryTerm>();
			values.add(PaymentDao.Field.TYPE.getQueryTerm(type));
			values.add(PaymentDao.Field.MAPPING_ID.getQueryTerm(mappingId));
			
			return paymentDao.findObject(values);
		} catch (NotFoundException e) {
			throw new NotFoundException(ErrorMessage.PAYMENT_NOT_FOUND.getMsg());
		}
	}

	@Override
	public Payment getPaymentByTypeAndPayer(String type, int mappingId, int payerId, int receiverId)
			throws NotFoundException {
		try {
			List<QueryTerm> values = new ArrayList<QueryTerm>();
			values.add(PaymentDao.Field.TYPE.getQueryTerm(type));
			values.add(PaymentDao.Field.MAPPING_ID.getQueryTerm(mappingId));
			values.add(PaymentDao.Field.PAYER_ID.getQueryTerm(payerId));
			values.add(PaymentDao.Field.RECEIVER_ID.getQueryTerm(receiverId));
			
			return paymentDao.findObject(values);
		} catch (NotFoundException e) {
			throw new NotFoundException(ErrorMessage.PAYMENT_NOT_FOUND.getMsg());
		}
	}

}
