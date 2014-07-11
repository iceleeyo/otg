package com.springtour.otg.infrastructure.channel.bill99pos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import lombok.Setter;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.application.util.Constants;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;
import com.springtour.otg.infrastructure.time.DateUtils;

public class Bill99PosRequestAdapter extends AbstractRequestWithoutCardAdapter {

	@Setter
	private TransactionRepository transactionRepository;

	public Bill99PosRequestAdapter(String channel) {
		super(channel);
	}

	/**
	 * TransType | Amount | POSID | OperatorID |TransTime| OrderID |
	 * Old_trace|Old_DateTime|Old_authcode| Old_Refno| Remark
	 */
	@Override
	public String request(Transaction transaction, String returnUrl,
			Map customParams) throws UnavailableChannelException,
			CannotLaunchSecurityProcedureException {
		StringBuilder requestToken = new StringBuilder();
		if (transaction.isCancelTxn()) {
			// 撤销交易
			Transaction oldTransaction = transactionRepository
					.find(new TransactionNo(transaction
							.getReferenceTransactionNumber()));
			String externalTxnNo = oldTransaction.getHandlingActivity()
					.getNotification().getExtTxnNo();

			requestToken.append(Bill99PosConstants.CANCEL_TYPE);
			requestToken.append(convertAmount(transaction.getAmount().negate()
					.getAmount()));
			requestToken.append(Bill99PosConstants.POS_ID);
			requestToken.append(Bill99PosConstants.OPERATOR_ID);
			// 此处设置为交易生成时间
			requestToken
					.append(convertTransTime(transaction.getWhenRequested()));
			requestToken.append(convertOrderId(transaction.getTransactionNo()
					.getNumber()));
			requestToken.append(getPosTermTraceId(externalTxnNo));
			requestToken.append(spaces(36));
		} else {
			// 新交易
			requestToken.append(Bill99PosConstants.TRANS_TYPE);
			requestToken.append(convertAmount(transaction.getAmount()
					.getAmount()));
			requestToken.append(Bill99PosConstants.POS_ID);
			requestToken.append(Bill99PosConstants.OPERATOR_ID);
			// 此处设置为交易生成时间
			requestToken
					.append(convertTransTime(transaction.getWhenRequested()));
			requestToken.append(convertOrderId(transaction.getTransactionNo()
					.getNumber()));
			requestToken.append(spaces(42));
		}
		return requestToken.toString();
	}

	/**
	 * 
	 * @param externalId
	 *            格式为：authCode , RRN , termTraceNo
	 * @return
	 */
	private String getPosTermTraceId(String externalId) {
		String termTraceNo = "";
		try {
			String[] ids = externalId.split(",");
			termTraceNo = ids[2];
		} catch (Exception e) {
			throw new IllegalArgumentException("external id 有误： externalId = "
					+ externalId);
		}
		return termTraceNo;
	}

	private String spaces(int number) {
		String spaces = "";
		for (int i = 0; i < number; i++) {
			spaces += Constants.SPACE;
		}
		return spaces;
	}

	private String convertOrderId(String number) {
		// 不足20位，补空格
		int lengthOfTxnNo = number.length();
		int lengthLast = 20 - lengthOfTxnNo;
		String emptyStrLast = Constants.EMPTY;
		for (int i = 0; i < lengthLast; i++) {
			emptyStrLast += Constants.SPACE;
		}
		return number + emptyStrLast;
	}

	private String convertTransTime(Date whenRequested) {
		// MMDDHHMMS
		return DateUtils.getDateAndTimeWithoutYear(whenRequested);
	}

	private String convertAmount(BigDecimal amount) {
		// 以分为单位，不够12位左补0
		String amountStr = amount
				.multiply(Bill99PosConstants.AMOUNT_CONVERTION_SCALE)
				.toBigInteger().toString();
		int amountLength = amountStr.length();
		int numberOfZeros = 12 - amountLength;
		for (int i = 0; i < numberOfZeros; i++) {
			amountStr = "0" + amountStr;
		}
		return amountStr;
	}
}
