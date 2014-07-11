package com.springtour.otg.infrastructure.channel.cmbc;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.time.Clock;
import com.springtour.test.AbstractJMockUnitTests;

public class CmbcNotificationFactoryImplUnitTests extends
		AbstractJMockUnitTests {

	String channelId = "17";
	private CmbcNotificationFactoryImpl target = new CmbcNotificationFactoryImpl(
			channelId);

	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);

	private NotificationRepository notificationRepository = context
			.mock(NotificationRepository.class);
	private Clock clock = context.mock(Clock.class);

	private Encryption encryption = context.mock(Encryption.class);

	@Before
	public void onSetUp() {
		target.setTransactionRepository(transactionRepository);
		target.setClock(clock);
		target.setEncryption(encryption);
		target.setNotificationRepository(notificationRepository);
	}

	@Test
	public void testMake() {
		Map<String, String> originalNotification = new HashMap<String, String>();
		final String encryptedStr = "Uzdqbnqtb8UNl+5U3P26YcDBX1umv4lJutu9Eb5SgPFegy3IkcXYW3+DHkAj4X/ubhqKRBKorlVeL56HzVTjuW70kUP/f74YlQ1BjCiOyodpz+5wZ/YtQnJaKsP5kWz+ApSTKXnAFrTEbt0pIC+vze/N8WYLMSrDY/olxdu1+upfCGa/czJrDalKpho+5peQNDX8tng8qM2BaZXvn+5CALgWkCnXXOyDhgF/f1Jg+nMn+xv5vKfXv8rMbWibdWzU7rwrNTocajlkU0uCotaD9wGh5loTDhwaF0pvvODlaa3EZEfcInIbxlkKnZJy1sYLhPoKAPS9Dsdb6OLuuDLKaA==,eR3F0BTN2y/iFhYVp7FyIFTzHgr0ykO6aTCEOTUFz+EezHicItb2dhD3aSqWJXWtQDpren4MakMjAy+DAk/ZaKBGF95ao97+Osp33tpVZbialO2S9JXnFPcmkPEUi+JwutOV4S3ccQT4PAJ+iA6oZ73xRPjxXXcZqZQEDF5T9kA=";
		originalNotification.put("reqStr", encryptedStr);
		final String decryptedStr = "90507201304261105001|305010601390507|100.0|20130426|110830|0|||0|0|0.0|0.0";
		final TransactionNo txnNo = new TransactionNo("90507201304261105001");
		final Transaction txn = new TransactionFixture().specify(txnNo.getNumber()).build();
		context.checking(new Expectations() {
			{

				oneOf(encryption).decrypt(encryptedStr);
				will(returnValue(decryptedStr));

				allowing(transactionRepository).find(txnNo);
				will(returnValue(txn));

				allowing(notificationRepository).nextSequence();

				allowing(clock).now();
			}
		});
		Notification notification = target.make(channelId, originalNotification);
		assertEquals(Charge.SUCCESS.getCode(), notification.getCharged());
		assertEquals(Money.valueOf(new BigDecimal(100.0)), notification.getAmount());

	}
}
