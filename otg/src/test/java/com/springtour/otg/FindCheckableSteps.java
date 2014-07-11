package com.springtour.otg;

import java.text.*;
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;

import org.jmock.*;

import com.springtour.otg.application.*;
import com.springtour.otg.domain.model.channel.*;
import com.springtour.otg.domain.model.merchant.*;
import com.springtour.otg.domain.model.partner.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.shared.*;
import com.springtour.otg.infrastructure.time.*;
import com.springtour.test.*;

import cucumber.api.java.*;
import cucumber.api.java.en.*;

@Slf4j
public class FindCheckableSteps {

	private CheckingBatch target;

	private TransactionRepository transactionRepository;
	private TransactionCheckingEvents transactionCheckingEvents;
	private Clock clock;
	private CheckingBatchConfigurations configurations;

	private Date now;

	private List<Transaction> transactions;

	private List<ActionExpectation> expectations;

	@Before(value = "@FindCheckableSteps")
	public void setUp() {

		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		TestDoubleBeanPostProcessor.resetContext();
		TestDoubleBeanPostProcessor.addMock(
				"otg.IBatisTransactionRepositoryImpl",
				TransactionRepository.class);
		TestDoubleBeanPostProcessor.addMock("otg.JmsCheckingEventsImpl",
				TransactionCheckingEvents.class);
		TestDoubleBeanPostProcessor.addMock("otg.IBatisClockImpl", Clock.class);

		final ApplicationContextGateway applicationContext = ApplicationContextGateway
				.newInstance();
		target = (CheckingBatch) applicationContext.get("otg.CheckingBatch");

		transactionRepository = (TransactionRepository) applicationContext
				.get("otg.IBatisTransactionRepositoryImpl");
		transactionCheckingEvents = (TransactionCheckingEvents) applicationContext
				.get("otg.JmsCheckingEventsImpl");
		clock = (Clock) applicationContext.get("otg.IBatisClockImpl");
		configurations = (CheckingBatchConfigurations) applicationContext
				.get("otg.CheckingBatchConfigurations");

	}

	@Given("^newBornRound=(\\d+)$")
	public void newBornRound(int value) {
		this.configurations.setNewBornRound(value);
	}

	@And("^unlivelyRound=(\\d+)$")
	public void unlivelyRound(int value) {
		this.configurations.setUnlivelyRound(value);
	}

	@And("^deadRound=(\\d+)$")
	public void deadRound(int value) {
		this.configurations.setDeadRound(value);
	}

	@And("^intervalMinutes=(\\d+)$")
	public void intervalMinutes(int value) {
		this.configurations.setIntervalMinutes(value);
	}

	@And("^unlivelyTurnRounds$")
	public void intervalMinutes(List<String> values) {
		int[] unlivelyAndTakeRounds = new int[values.size()];
		for (int i = 0; i < values.size(); i++) {
			unlivelyAndTakeRounds[i] = Integer.valueOf(values.get(i));
		}
		this.configurations.setUnlivelyTurnRounds(unlivelyAndTakeRounds);
	}

	@When("^现在时间=(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+):(\\d+)，找到如下交易$")
	public void findsSuchTransactions(String year, String month, String day,
			String hour, String minute, String second,
			List<TransactionInfo> transactions) throws ParseException {
		at(year, month, day, hour, minute, second);
		initTxns(transactions);
	}

	private void initTxns(List<TransactionInfo> transactions)
			throws ParseException {
		this.transactions = new ArrayList<Transaction>();

		for (TransactionInfo dto : transactions) {
			this.transactions.add(from(dto));
		}
	}

	@Then("^处理结果如下$")
	public void notifiesSpottingDeadTransaction(List<ActionExpectation> aes) {

		this.expectations = aes;

		TestDoubleBeanPostProcessor.context.checking(new Expectations() {

			{
				{

					allowing(transactionRepository).findBy(
							with(any(ToBeCheckedSpecification.class)));
					will(returnValue(transactions));

					allowing(clock).now();
					will(returnValue(now));

					for (ActionExpectation expectation : expectations) {
						final Transaction transaction = expectation
								.getTransactionFrom(transactions);

						if (expectation.notifyToCheck()) {
							oneOf(transactionCheckingEvents).notifyToCheck(
									transaction);
						} else if (expectation.notifySpottingDead()) {
							oneOf(transactionCheckingEvents)
									.notifySpottingDead(transaction);
						}
					}

				}
			}
		});

		target.run();
		TestDoubleBeanPostProcessor.context.assertIsSatisfied();
	}

	@After
	public void assertMockContext() {

	}

	private Transaction from(TransactionInfo txn) throws ParseException {
		Transaction transaction = new Transaction(new TransactionNo(
				txn.getTransactionNo()), PARTNER, MONEY, new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").parse(txn.getWhenRequested()), ORDER_ID,
				MERCHANT, CHANNEL, GATEWAY);
		transaction.setState(state(txn.getState()));
		return transaction;
	}

	private void at(String year, String month, String day, String hour,
			String minute, String second) throws ParseException {
		this.now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(year + "-"
				+ month + "-" + day + " " + hour + ":" + minute + ":" + second);
	}

	private static final OrderIdentity ORDER_ID = null;
	private static final Channel CHANNEL = null;
	private static final Merchant MERCHANT = new MerchantFixture().build();
	private static final Money MONEY = null;
	private static final Partner PARTNER = null;
	private static final String GATEWAY = null;

	private String state(String state) {
		for (Transaction.State candidate : Transaction.State.values()) {
			if (candidate.name().equals(state)) {
				return candidate.getCode();
			}
		}
		throw new IllegalArgumentException("unrecognized transaction state:"
				+ state);
	}

	@Data
	public class TransactionInfo {
		private String transactionNo;
		private String whenRequested;
		private String state;
		private String checkingState;
		private String remark;
	}

	@Data
	public class ActionExpectation {

		private String transactionNo;
		private String action;

		public Transaction getTransactionFrom(List<Transaction> transactions) {
			return toSet(transactions).get(transactionNo);
		}

		public boolean notifyToCheck() {
			return "通知该交易准备核对".equals(action);
		}

		public boolean notifySpottingDead() {
			return "通知该交易准备认定为Dead".equals(action);
		}

		private Map<String, Transaction> toSet(List<Transaction> transactions) {
			Map<String, Transaction> txns = new HashMap<String, Transaction>();
			for (Transaction txn : transactions) {
				txns.put(txn.getTransactionNo().getNumber(), txn);
			}
			return txns;
		}
	}

}
