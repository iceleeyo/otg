package com.springtour.otg.domain.model.transaction;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
import static org.hamcrest.Matchers.*;
import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.jmock.lib.legacy.*;
import org.junit.*;
import org.junit.runner.*;
import com.springtour.otg.application.exception.CannotCancelTransactionException;
import com.springtour.otg.domain.model.channel.ChannelRepository;
import com.springtour.otg.domain.model.merchant.MerchantRepository;
import com.springtour.otg.domain.model.notification.NotificationFixture;
import com.springtour.otg.domain.model.partner.PartnerRepository;
import com.springtour.otg.domain.service.MakePaymentService;
import com.springtour.otg.infrastructure.time.Clock;

@RunWith(JMock.class)
public class TransactionFactoryImplMockTests {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);
	private MerchantRepository merchantRepository = context
			.mock(MerchantRepository.class);
	private ChannelRepository channelRepository = context
			.mock(ChannelRepository.class);
	private PartnerRepository partnerRepository = context
			.mock(PartnerRepository.class);
	private MakePaymentService makePaymentService = context
			.mock(MakePaymentService.class);
	private Clock clock = context.mock(Clock.class);
	private TransactionNoGenerator transactionNoGenerator = context
			.mock(TransactionNoGenerator.class);

	private TransactionFactory target;

	@Before
	public void setUp() {
		target = new TransactionFactory();
		target.setChannelRepository(channelRepository);
		target.setClock(clock);
		target.setMakePaymentService(makePaymentService);
		target.setMerchantRepository(merchantRepository);
		target.setPartnerRepository(partnerRepository);
		target.setTransactionNoGenerator(transactionNoGenerator);
		target.setTransactionRepository(transactionRepository);
	}

	@Test(expected = CannotCancelTransactionException.class)
	public void throwsExceptionCancelTransactionByRequestedTxn() {
		Transaction oldTransaction = new TransactionFixture().build();
		target.cancelTransactionBy(oldTransaction);
	}

	@Test
	public void cancelConcludedTransaction() {
		final Transaction oldTransaction = new TransactionFixture()
				.specify(Transaction.State.CONCLUDED).chargeFor("0,0,1,10,123,0")
				.notification(new NotificationFixture().build()).build();
		final String seq = "1234566778";
		final Date now = new Date();
		final TransactionNo transactionNo = new TransactionNo("2012344777");
		context.checking(new Expectations() {
			{
				allowing(transactionRepository).nextTransactionNoSequence();
				will(returnValue(seq));

				allowing(clock).now();
				will(returnValue(now));

				allowing(transactionNoGenerator).nextTransactionNo(
						oldTransaction.getChannel().getId(),
						oldTransaction.getMerchant(), seq, now);
				will(returnValue(transactionNo));

			}
		});
		Transaction newTxn = target.cancelTransactionBy(oldTransaction);
		assertThat(newTxn.getAmount().getAmount(),
				comparesEqualTo(new BigDecimal(-100)));
		assertEquals(TransactionType.CANCEL.getCode(),
				newTxn.getTransactionType());
		assertEquals(oldTransaction.getTransactionNo().getNumber(),
				newTxn.getReferenceTransactionNumber());
		assertEquals("0,0,1,-10,123,0",newTxn.getChargeFor());
	}
}
