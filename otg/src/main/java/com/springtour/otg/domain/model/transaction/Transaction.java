package com.springtour.otg.domain.model.transaction;

import static com.springtour.otg.domain.model.transaction.CheckingState.*;

import java.util.*;

import lombok.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.*;

import com.springtour.otg.application.exception.*;
import com.springtour.otg.application.util.Constants;
import com.springtour.otg.domain.model.channel.*;
import com.springtour.otg.domain.model.merchant.*;
import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.partner.*;
import com.springtour.otg.domain.shared.*;
import com.springtour.shared.*;

/**
 * 交易 </p>
 * 
 * 交易是该聚合的聚合根，交易流水号是其标识。
 * 
 * </p>
 * 
 * 一笔完成的交易经过请求中-->已响应_支付成功-->已完成的生命周期。 </p>
 * 
 * 由于客户原因失败的交易经过请求中-->已响应_支付失败的生命周期。 </p>
 * 
 * 请求中是交易的初始状态，代表开始交易。 此状态是个中间状态 </p>
 * 当支付渠道返回交易结果后，交易响应此返回结果，更新自身状态为已响应。这个状态有两个分支：支付成功/支付失败。
 * 其中已响应_支付成功是中间状态，已响应_支付失败是最终状态。 </p> 交易通知业务系统完成支付成功的业务逻辑后，更新自身状态为已完成。已完成是最终状态。
 * </p>
 * 
 * 处于中间状态的交易都没有经过完整的生命周期，是不完整的。
 * 
 * @author Hippoom
 * 
 */
public class Transaction {

	/**
	 * 交易流水号
	 */
	private TransactionNo transactionNo;
	/**
	 * 对接伙伴
	 */
	private Partner partner;
	/**
	 * 请求交易的订单的标识
	 */
	private OrderIdentity orderId;
	/**
	 * 商户号（冗余）
	 */
	private String merchantCode;
	/**
	 * 商户
	 */
	private Merchant merchant;
	/**
	 * 支付渠道
	 */
	private Channel channel;
	/**
	 * 网关号码
	 */
	private String gateway;
	/**
	 * 金额
	 */
	private Money amount;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 核对状态，表示该交易的核心数据（比如金额、币种、状态等）是否已和供应商的copy进行了核对
	 */
	@Getter
	@Setter
	private String checkingState;
	@Getter
	@Setter
	private Date updateTime;

	/**
	 * 请求时间
	 */
	private Date whenRequested;
	/**
	 * 通知处理活动
	 */
	private HandlingActivity handlingActivity;
	/**
	 * 完成时间
	 */
	private Date whenConcluded;
	/**
	 * 交易类别
	 */
	@Setter
	@Getter
	private String transactionType = TransactionType.NEW.getCode();
	@Setter
	@Getter
	private String referenceTransactionNumber;
	@Setter
	@Getter
	private String chargeFor = Constants.EMPTY;

	public Transaction(TransactionNo transactionNo, Partner partner,
			Money amount, Date whenRequested, OrderIdentity orderId,
			Merchant merchant, Channel channel, String gateway) {
		this.transactionNo = transactionNo;
		this.partner = partner;
		this.amount = amount;
		this.whenRequested = new Date(whenRequested.getTime());
		this.orderId = orderId;
		this.merchantCode = merchant.getCode();
		this.merchant = merchant;
		this.channel = channel;
		this.state = State.REQUESTED.getCode();
		this.gateway = gateway;
		this.checkingState = CheckingState.UNCHECKED.getCode();
	}
	
	public Transaction(TransactionNo transactionNo, Partner partner,
			Money amount, Date whenRequested, OrderIdentity orderId,
			Merchant merchant, Channel channel, String gateway, String chargeFor) {
		this.transactionNo = transactionNo;
		this.partner = partner;
		this.amount = amount;
		this.whenRequested = new Date(whenRequested.getTime());
		this.orderId = orderId;
		this.merchantCode = merchant.getCode();
		this.merchant = merchant;
		this.channel = channel;
		this.state = State.REQUESTED.getCode();
		this.gateway = gateway;
		this.checkingState = CheckingState.UNCHECKED.getCode();
		this.chargeFor = chargeFor;
	}

	public Transaction(TransactionNo transactionNo, Partner partner,
			Money amount, Date whenRequested, OrderIdentity orderId,
			Merchant merchant, Channel channel, String gateway,
			TransactionType transactionType, String referenceTransactionNumber) {
		this.transactionNo = transactionNo;
		this.partner = partner;
		this.amount = amount;
		this.whenRequested = new Date(whenRequested.getTime());
		this.orderId = orderId;
		this.merchantCode = merchant.getCode();
		this.merchant = merchant;
		this.channel = channel;
		this.state = State.REQUESTED.getCode();
		this.gateway = gateway;
		this.checkingState = CheckingState.UNCHECKED.getCode();
		this.transactionType = transactionType.getCode();
		this.referenceTransactionNumber = referenceTransactionNumber;
	}
	
	public Transaction(TransactionNo transactionNo, Partner partner,
			Money amount, Date whenRequested, OrderIdentity orderId,
			Merchant merchant, Channel channel, String gateway,
			TransactionType transactionType, String referenceTransactionNumber, String chargeFor) {
		this.transactionNo = transactionNo;
		this.partner = partner;
		this.amount = amount;
		this.whenRequested = new Date(whenRequested.getTime());
		this.orderId = orderId;
		this.merchantCode = merchant.getCode();
		this.merchant = merchant;
		this.channel = channel;
		this.state = State.REQUESTED.getCode();
		this.gateway = gateway;
		this.checkingState = CheckingState.UNCHECKED.getCode();
		this.transactionType = transactionType.getCode();
		this.referenceTransactionNumber = referenceTransactionNumber;
		this.chargeFor = chargeFor;
	}

	/**
	 * 获得网关的渠道方言代码
	 * 
	 * @return
	 */
	public String gatewayChannelDialect() {
		return this.channel.getGateway(this.gateway).getDialect();
	}

	/**
	 * 处理通知
	 * 
	 * @param notification
	 * @param whenHandled
	 * @throws IllegalStateException
	 * @throws DuplicateResponseException
	 */
	public void handle(Notification notification, Date whenHandled)
			throws IllegalAmountException, DuplicateResponseException {
		checkConstraintsWhenHandling(notification);
		this.handlingActivity = new HandlingActivity(notification, whenHandled);
		this.state = notification.isCharged() ? State.RESPONSED_SUCCESS
				.getCode() : State.RESPONSED_FAILURE.getCode();
		resurrectIfNecessary();
		this.responsedUpdated = true;// 设置动态更新标志
	}

	private void resurrectIfNecessary() {
		if (is(DEAD)) {
			update(UNCHECKED, null);
		}
	}

	private void checkConstraintsWhenHandling(Notification notification)
			throws DuplicateResponseException, IllegalAmountException {
		if (!State.REQUESTED.sameCodeAs(this.state)) {
			// 检查状态，只要不是请求中都算作重复通知
			throw new DuplicateResponseException(this.transactionNo,
					notification.getSequence());
		}
		if (!this.amount.sameValueAs(notification.getAmount())) {
			// 检查金额，主要是检查币种
			throw new IllegalAmountException(this.transactionNo, this.amount,
					notification.getAmount());
		}
	}

	/**
	 * 付款成功后，确认交易
	 * 
	 * @param whenConcluded
	 * @throws IllegalStateException
	 *             当交易此时的状态不为已响应_扣款成功时
	 */
	public void conclude(Date whenConcluded) throws IllegalStateException {
		checkConstraintsWhenConcluding();
		this.whenConcluded = new Date(whenConcluded.getTime());
		this.state = Transaction.State.CONCLUDED.getCode();
		this.concludedUpdated = true;// 设置动态更新标志
	}

	private void checkConstraintsWhenConcluding() {
		if (!isResponsedSuccess()) {
			throw new IllegalStateException("TransactionNo=" + transactionNo
					+ ",currentState=" + state + ",attempt="
					+ Transaction.State.CONCLUDED.getCode());
		}
	}

	public boolean isRequested() {
		return State.REQUESTED.sameCodeAs(this.state);
	}

	public boolean isResponsedSuccess() {
		return State.RESPONSED_SUCCESS.sameCodeAs(this.state);
	}

	public boolean isChargedFailure() {
		return State.RESPONSED_FAILURE.sameCodeAs(this.state);
	}

	public boolean isConcluded() {
		return State.CONCLUDED.sameCodeAs(this.state);
	}

	public void regardedAsValidAt(Date now)
			throws CannotChangeCheckingStateException {
		changeCheckingStateTo(VALID, now);
	}

	public CheckingState checkingState() {
		return CheckingState.of(checkingState);
	}

	public void regardedAsInvalidAt(Date now)
			throws CannotChangeCheckingStateException {
		changeCheckingStateTo(INVALID, now);
	}

	private void changeCheckingStateTo(CheckingState attempt, Date now)
			throws CannotChangeCheckingStateException {
		if (is(UNCHECKED)) {
			update(attempt, now);
		} else {
			throw new CannotChangeCheckingStateException(checkingState(),
					attempt);
		}
	}

	private void update(CheckingState attempt, Date now) {
		this.checkingState = attempt.getCode();
		this.updateTime = now;
		this.checkingStateUpdated = true;
	}

	public void regardedAsDeadAt(Date now)
			throws CannotChangeCheckingStateException {
		changeCheckingStateTo(DEAD, now);
	}

	public boolean is(State state) {
		return State.of(this.state).equals(state);
	}

	public boolean is(CheckingState checkingState) {
		return CheckingState.of(this.checkingState).equals(checkingState);
	}

	public boolean sameAmountAs(Money amountFromSupplier) {
		return this.amount.sameValueAs(amountFromSupplier);
	}

	public Transaction.State state() {
		return State.of(this.state);
	}


	public boolean isNotificationRetrievedByCheck() {
		if (this.handlingActivity != null) {
			return this.handlingActivity.getNotification().isByCheck();
		} else {
			return false;
		}
	}
	
	public String getSynchronizingMethod(){
		if (this.handlingActivity != null) {
			return this.handlingActivity.getNotification().getSynchronizingMethod();
		}else{
			return StringUtils.EMPTY;
		}
	}


	public TransactionType transactionType() {
		return TransactionType.of(this.transactionType);
	}

	public boolean isNewTxn() {
		return TransactionType.NEW.sameCodeAs(this.transactionType);
	}

	public boolean isCancelTxn() {
		return TransactionType.CANCEL.sameCodeAs(this.transactionType);
	}
	
	/**   
     * 此处为类方法说明
     * @param 
     * @return
     *******************************************************************************
     * @creator ：010586  
     * @date ：2014-3-24     
     * @memo ：   
     **
     */
    public void changeOrderInfo(OrderIdentity orderIdentity, String chargeFor) {
        this.orderId = orderIdentity;
        this.chargeFor = chargeFor;
        this.orderInfoUpdated = true;
    }


	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append(transactionNo).append(state).toString();
	}

	/**
	 * auto-generated surrogate key
	 */
	private Long transactionId;
	/**
	 * optimistic lock version
	 */
	private int version = 1;

	/**
	 * for persistence
	 */
	public Transaction() {
	}

	/**
	 * 状态
	 * 
	 * @author Hippoom
	 * 
	 */
	public enum State implements ValueObject<State> {

		/**
		 * 请求中
		 */
		REQUESTED("0", "请求中"),
		/**
		 * 已响应_支付成功
		 */
		RESPONSED_SUCCESS("1", "已响应_扣款成功"),
		/**
		 * 已响应_支付失败
		 */
		RESPONSED_FAILURE("2", "已响应_扣款失败"),
		/**
		 * 已完成
		 */
		CONCLUDED("3", "已完成");
		/**
		 * 代码
		 */
		private final String code;
		private final String name;

		private State(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		public boolean sameCodeAs(String code) {
			return (code != null) && this.code.equals(code);
		}

		public static String getNameFromCode(String code) {
			for (State st : State.values()) {
				if (st.sameCodeAs(code)) {
					return st.getName();
				}
			}
			return "";
		}

		public static State of(String code) {
			for (State candidate : State.values()) {
				if (candidate.sameCodeAs(code)) {
					return candidate;
				}
			}
			throw new IllegalArgumentException("unrecognized txn state code["
					+ code + "]");
		}

		@Override
		public boolean sameValueAs(final State other) {
			return other != null && this.code.equals(other.getCode());
		}
	}

	private boolean responsedUpdated = false;
	private boolean concludedUpdated = false;
	@Getter
	private boolean checkingStateUpdated = false;
	@Getter
	private boolean orderInfoUpdated = false;

	public void resetUpdated() {
		this.responsedUpdated = false;
		this.concludedUpdated = false;
		this.checkingStateUpdated = false;
		this.orderInfoUpdated = false;
	}

	public boolean isUpdated() {
		return isResponsedUpdated() || isConcludedUpdated()
				|| isCheckingStateUpdated();
	}

	public TransactionNo getTransactionNo() {
		return transactionNo;
	}

	public Money getAmount() {
		return amount;
	}

	public String getState() {
		return state;
	}

	public Date getWhenRequested() {
		return whenRequested;
	}

	public Date getWhenConcluded() {
		return whenConcluded;
	}

	public OrderIdentity getOrderId() {
		return orderId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public int getVersion() {
		return version;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setTransactionNo(TransactionNo transactionNo) {
		this.transactionNo = transactionNo;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setWhenRequested(Date whenRequested) {
		this.whenRequested = whenRequested;
	}

	public void setWhenConcluded(Date whenConcluded) {
		this.whenConcluded = whenConcluded;
	}

	public void setOrderId(OrderIdentity orderId) {
		this.orderId = orderId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public boolean isResponsedUpdated() {
		return responsedUpdated;
	}

	public boolean isConcludedUpdated() {
		return concludedUpdated;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public HandlingActivity getHandlingActivity() {
		return handlingActivity;
	}

	public void setHandlingActivity(HandlingActivity handlingActivity) {
		this.handlingActivity = handlingActivity;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

}
