package com.springtour.otg.infrastructure.messaging;

import static com.jayway.awaitility.Awaitility.*;

import static java.util.concurrent.TimeUnit.*;
import static org.hamcrest.core.IsEqual.*;

import java.util.concurrent.*;

import lombok.extern.slf4j.*;

import org.junit.*;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.infrastructure.stub.*;
import com.springtour.test.*;

@Slf4j
public class JmsTransactionCheckingEventsImplIntegrationTests extends
		AbstractJMockUnitTests {

	private JmsTransactionCheckingEventsImpl target;
	private TestCheckingConsumerStub checkingConsumer;
	private TestSpottingDeadConsumerStub spottingDeadConsumer;
	private TestSpottingInvalidConsumerStub spottingInvalidConsumer;

	@Before
	public void setUp() throws Exception {

		TestDoubleBeanPostProcessor.clearMocks();
		//为保证只有一个TestCheckingConsumerStub实例,采用单例模式
		TestDoubleBeanPostProcessor.addStub("otg.CheckingConsumer",
				TestCheckingConsumerStub.getInstance());
		
		//为保证只有一个TestSpottingDeadConsumerStub实例
		TestDoubleBeanPostProcessor.addStub("otg.SpottingDeadConsumer",
				TestSpottingDeadConsumerStub.getInstance());
		
		TestDoubleBeanPostProcessor.addStub("otg.SpottingInvalidConsumer",
				TestSpottingInvalidConsumerStub.getInstance());

		final ApplicationContextGateway applicationContext = ApplicationContextGateway
				.newInstanceAdding("messaging.xml");

		target = (JmsTransactionCheckingEventsImpl) applicationContext
				.get("otg.JmsCheckingEventsImpl");

		checkingConsumer = (TestCheckingConsumerStub) applicationContext
				.get("otg.CheckingConsumer");
		spottingDeadConsumer = (TestSpottingDeadConsumerStub) applicationContext
				.get("otg.SpottingDeadConsumer");
		spottingInvalidConsumer = (TestSpottingInvalidConsumerStub) applicationContext
				.get("otg.SpottingInvalidConsumer");
	}

	@Test
	public void sendsMessageWhenNotifyToCheck() throws Exception {

		log.info("start sendingMessage");

		final Transaction transaction = new TransactionFixture().build();
		target.notifyToCheck(transaction);

		log.info("end sendingMessage");
		await().atMost(2, SECONDS).until(
				checkTransactionWasConsumed(checkingConsumer),
				equalTo(transaction.getTransactionNo()));

		log.info("end receivingMessage");
	}

	@Test
	public void sendsMessageWhenNotifySpottingDead() throws Exception {
		log.info("start sendingMessage");

		final Transaction transaction = new TransactionFixture().specify("11")
				.build();
		target.notifySpottingDead(transaction);

		log.info("end sendingMessage");
		await().atMost(2, SECONDS).until(
				spottingDeadTransactionWasConsumed(spottingDeadConsumer),
				equalTo(transaction.getTransactionNo()));

		log.info("end receivingMessage");
	}
	
	@Test
	public void sendsMessageWhenNotifySpottingInvalid() throws Exception {
		log.info("start sendingMessage");

		final Transaction transaction = new TransactionFixture().specify("16")
				.build();
		target.notifySpottingInvalid(transaction);

		log.info("end sendingMessage");
		await().atMost(2, SECONDS).until(
				spottingInvalidTransactionWasConsumed(spottingInvalidConsumer),
				equalTo(transaction.getTransactionNo()));

		log.info("end receivingMessage");
	}

	private Callable<TransactionNo> checkTransactionWasConsumed(
			final TestCheckingConsumerStub checkingConsumer) {
		return new Callable<TransactionNo>() {

			@Override
			public TransactionNo call() throws Exception {
				return checkingConsumer.getTransactionNo();
			}

		};
	}

	private Callable<TransactionNo> spottingDeadTransactionWasConsumed(
			final TestSpottingDeadConsumerStub spottingDeadConsumer) {
		return new Callable<TransactionNo>() {

			@Override
			public TransactionNo call() throws Exception {
				return spottingDeadConsumer.getTransactionNo();
			}

		};
	}
	
	private Callable<TransactionNo> spottingInvalidTransactionWasConsumed(
			final TestSpottingInvalidConsumerStub spottingInvalidConsumer) {
		return new Callable<TransactionNo>() {

			@Override
			public TransactionNo call() throws Exception {
				return spottingInvalidConsumer.getTransactionNo();
			}

		};
	}
}
