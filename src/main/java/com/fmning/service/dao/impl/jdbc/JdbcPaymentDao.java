package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.PaymentDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.Payment;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcPaymentDao extends JdbcBaseDao<Payment> implements PaymentDao{
	public JdbcPaymentDao() {
		super(CoreTableType.PAYMENTS);
	}

	@Override
	protected NVPairList getNVPairs(Payment obj) {
		NVPairList params = new NVPairList();

		params.addValue(PaymentDao.Field.TYPE.name, obj.getType());
		params.addNullableNumValue(PaymentDao.Field.MAPPING_ID.name, obj.getMappingId());
		params.addValue(PaymentDao.Field.AMOUNT.name, obj.getAmount());
		params.addValue(PaymentDao.Field.STATUS.name, obj.getStatus());
		params.addValue(PaymentDao.Field.MESSAGE.name, obj.getMessage());
		params.addValue(PaymentDao.Field.PAYER_ID.name, obj.getPayerId());
		params.addValue(PaymentDao.Field.RECEIVER_ID.name, obj.getReceiverId());
		params.addValue(PaymentDao.Field.METHOD.name, obj.getMethod());
		params.addValue(PaymentDao.Field.NONCE.name, obj.getNonce());
		params.addValue(PaymentDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));

		return params;
	}

	@Override
	protected RowMapper<Payment> getRowMapper() {
		RowMapper<Payment> rm = new RowMapper<Payment>() {
			@Override
			public Payment mapRow(ResultSet rs, int row) throws SQLException {
				Payment obj = new Payment();

				obj.setId(rs.getInt(PaymentDao.Field.ID.name));
				obj.setType(rs.getString(PaymentDao.Field.TYPE.name));
				obj.setMappingId(Util.getNullableInt(rs, PaymentDao.Field.MAPPING_ID.name));
				obj.setAmount(rs.getDouble(PaymentDao.Field.AMOUNT.name));
				obj.setStatus(rs.getString(PaymentDao.Field.STATUS.name));
				obj.setMessage(rs.getString(PaymentDao.Field.MESSAGE.name));
				obj.setPayerId(rs.getInt(PaymentDao.Field.PAYER_ID.name));
				obj.setReceiverId(rs.getInt(PaymentDao.Field.RECEIVER_ID.name));
				obj.setMethod(rs.getString(PaymentDao.Field.METHOD.name));
				obj.setNonce(rs.getString(PaymentDao.Field.NONCE.name));
				obj.setCreatedAt(rs.getTimestamp(PaymentDao.Field.CREATED_AT.name).toInstant());

				return obj;
			}
		};
		return rm;
	}

}