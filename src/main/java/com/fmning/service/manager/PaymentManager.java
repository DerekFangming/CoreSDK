package com.fmning.service.manager;

import java.util.List;

import com.fmning.service.domain.Payment;
import com.fmning.service.domain.User;
import com.fmning.service.exceptions.NotFoundException;

public interface PaymentManager {
	
	/**
	 * Create a payment object and save it into database if it does not exist by type, mapping id, payer and payee id
	 * If such combination exists, update other fields
	 * @param type
	 * @param mappingId
	 * @param amount
	 * @param status
	 * @param message
	 * @param payerId
	 * @param receiverId
	 * @param method
	 * @param nonce
	 * @return
	 */
	public int savePayment(String type, int mappingId, double amount, String status, String message,
			int payerId, int receiverId, String method, String nonce);
	
	/**
	 * Get payment by database id
	 * @param id the database id
	 * @return the payment object
	 * @throws NotFoundException if the payment does not exist
	 */
	public Payment getPaymentById(int id) throws NotFoundException;
	
	/**
	 * Get payment by type and mapping id
	 * @param type
	 * @param mappingId
	 * @return the payment object
	 * @throws NotFoundException if the payment does not exist
	 */
	public Payment getPaymentByType(String type, int mappingId) throws NotFoundException;
	
	/**
	 * Get payment by type, mapping id, payer id and receiver id
	 * @param type
	 * @param mappingId
	 * @param payerId
	 * @param receiverId
	 * @return the payment object
	 * @throws NotFoundException if the payment does not exist
	 */
	public Payment getPaymentByTypeAndPayer(String type, int mappingId, int payerId, int receiverId)
			throws NotFoundException;
	
	/**
	 * Get a list of user that successfully paid for the specific mapping
	 * @param type the type of payment
	 * @param mappingId the mapping id for the payment
	 * @return List of users, if any
	 * @throws NotFoundException if no users found
	 */
	public List<User> getPaidUserByType(String type, int mappingId) throws NotFoundException;
	
}
