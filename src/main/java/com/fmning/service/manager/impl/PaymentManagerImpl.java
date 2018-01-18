package com.fmning.service.manager.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.PaymentDao;
import com.fmning.service.dao.UserDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.InnerQueryTerm;
import com.fmning.service.dao.impl.LogicalOpType;
import com.fmning.service.dao.impl.QueryBuilder;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.QueryType;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.domain.Payment;
import com.fmning.service.domain.User;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.PaymentManager;
import com.fmning.util.ErrorMessage;
import com.fmning.util.PaymentStatusType;

@Component
public class PaymentManagerImpl implements PaymentManager{
	
	@Autowired private PaymentDao paymentDao;
	@Autowired private UserDao userDao;

	@Override
	public int savePayment(String type, int mappingId, double amount, String status, String message, int payerId,
			int receiverId, String method, String nonce) {
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(PaymentDao.Field.TYPE.getQueryTerm(type));
		terms.add(PaymentDao.Field.MAPPING_ID.getQueryTerm(mappingId));
		terms.add(PaymentDao.Field.PAYER_ID.getQueryTerm(payerId));
		terms.add(PaymentDao.Field.RECEIVER_ID.getQueryTerm(receiverId));
		
		try {
			Payment payment = paymentDao.findObject(terms);
			payment.setAmount(amount);
			payment.setStatus(status);
			payment.setMessage(message);
			payment.setMethod(method);
			payment.setNonce(nonce);
			paymentDao.update(payment.getId(), payment);
			return payment.getId();
		} catch (NotFoundException e) {
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

	@Override
	public List<User> getPaidUserByType(String type, int mappingId) throws NotFoundException {
		QueryBuilder qb = QueryType.getQueryBuilder(CoreTableType.USERS, QueryType.FIND);
		
		QueryBuilder inner = qb.getInnerQueryBuilder(CoreTableType.PAYMENTS, QueryType.FIND);
		inner.addFirstQueryExpression(new QueryTerm(PaymentDao.Field.TYPE.name, type));
		inner.addNextQueryExpression(LogicalOpType.AND, 
				new QueryTerm(PaymentDao.Field.MAPPING_ID.name, mappingId));
		inner.addNextQueryExpression(LogicalOpType.AND, 
				new QueryTerm(PaymentDao.Field.STATUS.name, PaymentStatusType.DONE.getName()));
		inner.setReturnField(PaymentDao.Field.PAYER_ID.name);
		
		qb.addFirstQueryExpression(new InnerQueryTerm(UserDao.Field.ID.name, RelationalOpType.IN, inner));
		
		try{
	    	return userDao.findAllObjects(qb.createQuery());
	    }catch(NotFoundException e){
	    	throw new NotFoundException(ErrorMessage.NO_MORE_USER.getMsg());
	    } 
	}

}
