package com.springtour.otg.interfaces.transacting.facade.internal;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.springtour.otg.application.TransactingService;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.merchant.MerchantRepository;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.interfaces.transacting.facade.arg.FindMerchantArgument;
import com.springtour.otg.interfaces.transacting.facade.arg.NextTransactionNoArgument;
import com.springtour.otg.interfaces.transacting.facade.result.FindMerchantResult;
import com.springtour.otg.interfaces.transacting.facade.result.NextTransactionNoResult;
import com.springtour.otg.application.exception.StatusCode;

@RunWith(JMock.class)
public class TransactingServiceFacadeImplMockTests /* extends JUnit4Mockery */{
	// {
	// setImposteriser(ClassImposteriser.INSTANCE);
	// }

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private TransactingServiceFacadeImpl target;

	private MerchantRepository merchantRepository = context
			.mock(MerchantRepository.class);

	private TransactingService transactingService = context
			.mock(TransactingService.class);

	private String defaultOrgId = "61";

	@Before
	public void setUp() {
		target = new TransactingServiceFacadeImpl();
		target.setTransactingService(transactingService);
	}

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void findMerchant_success() throws Exception {
		final String channelId = "5";
		final String orgId = "61";

		final Channel channel = new Channel();
		channel.setId("5");

		final String code = "SpringTour";
		final Long merchantId = 1L;
		final String key = "w3c";
		final String name = " ";
		final String state = Merchant.State.ENABLED.getCode();

		final Merchant merchant = new Merchant();
		merchant.setChannel(channel);
		merchant.setCode(code);
		merchant.setId(merchantId);
		merchant.setKey(key);
		merchant.setName(name);
		merchant.setOrgId(orgId);
		merchant.setState(state);

		final FindMerchantArgument arg = new FindMerchantArgument();
		arg.setChannelId(channelId);
		arg.setOrgId(orgId);

		context.checking(new Expectations() {
			{
				oneOf(transactingService).findMerchant(arg.getChannelId(),
						arg.getOrgId());
				will(returnValue(merchant));
			}
		});

		FindMerchantResult result = target.findMerchant(arg);

		Assert.assertEquals(result.getStatusCode(), StatusCode.SUCCESS);
	}

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	// @Test
	// public void findMerchant_disabled() throws Exception {
	// final String channelId = "5";
	// final String orgId = "61";
	// final Channel channel = new Channel();
	// channel.setId(5L);
	//
	// final String code = "SpringTour";
	// final Long merchantId = 1L;
	// final String key = "w3c";
	// final String name = " ";
	// final String state = Merchant.State.DISABLED.getCode();
	//
	// final Merchant merchant = new Merchant();
	// merchant.setChannel(channel);
	// merchant.setCode(code);
	// merchant.setId(merchantId);
	// merchant.setKey(key);
	// merchant.setName(name);
	// merchant.setOrgId(orgId);
	// merchant.setState(state);
	//
	// final FindMerchantArgument arg = new FindMerchantArgument();
	// arg.setChannelId(channelId);
	// arg.setOrgId(orgId);
	//
	// context.checking(new Expectations() {
	// {
	// oneOf(transactingService).findMerchant(arg.getChannelId(),
	// arg.getOrgId());
	// will(returnValue(merchant));
	// }
	// });
	//
	// FindMerchantResult result = target.findMerchant(arg);
	//
	// Assert.assertEquals(result.getStatusCode(), StatusCode.NO_SUCH_MERCHANT);
	// }
	/**
	 * 
	 * @throws Exception
	 */
//	@Test
//	public void findMerchant_notFound() throws Exception {
//		final String channelId = "5";
//		final String orgId = "2061";
//
//		final Channel channel = new Channel();
//		channel.setId(5L);
//
//		final String code = "SpringTour";
//		final Long merchantId = 1L;
//		final String key = "w3c";
//		final String name = " ";
//		final String state = Merchant.State.ENABLED.getCode();
//
//		final Merchant merchant = new Merchant();
//		merchant.setChannel(channel);
//		merchant.setCode(code);
//		merchant.setId(merchantId);
//		merchant.setKey(key);
//		merchant.setName(name);
//		merchant.setOrgId(orgId);
//		merchant.setState(state);
//
//		final FindMerchantArgument arg = new FindMerchantArgument();
//		arg.setChannelId(channelId);
//		arg.setOrgId(orgId);
//
//		context.checking(new Expectations() {
//			{
//				oneOf(transactingService).findMerchant(arg.getChannelId(),
//						arg.getOrgId());
//				will(throwException(new NoSuchMerchantException(arg.getChannelId(),
//						arg.getOrgId())));
//
//				// oneOf(merchantRepository)
//				// .find(arg.getChannelId(), defaultOrgId);
//				// will(returnValue(merchant));
//			}
//		});
//
//		FindMerchantResult result = target.findMerchant(arg);
//
//		Assert.assertEquals(result.getStatusCode(), StatusCode.NO_SUCH_MERCHANT);
//}
//
//	/**
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void findMerchant_defaultOrgNotFound() throws Exception {
//		final String channelId = "5";
//		final String orgId = "2061";
//
//		final Channel channel = new Channel();
//		channel.setId(5L);
//
//		final String code = "SpringTour";
//		final Long merchantId = 1L;
//		final String key = "w3c";
//		final String name = " ";
//		final String state = Merchant.State.ENABLED.getCode();
//
//		final Merchant merchant = new Merchant();
//		merchant.setChannel(channel);
//		merchant.setCode(code);
//		merchant.setId(merchantId);
//		merchant.setKey(key);
//		merchant.setName(name);
//		merchant.setOrgId(orgId);
//		merchant.setState(state);
//
//		final FindMerchantArgument arg = new FindMerchantArgument();
//		arg.setChannelId(channelId);
//		arg.setOrgId(orgId);
//
//		context.checking(new Expectations() {
//			{
//				oneOf(transactingService).findMerchant(arg.getChannelId(),
//						arg.getOrgId());
//				will(returnValue(null));
//
//				// oneOf(merchantRepository)
//				// .find(arg.getChannelId(), defaultOrgId);
//				// will(returnValue(null));
//			}
//		});
//
//		FindMerchantResult result = target.findMerchant(arg);
//
//		Assert
//				.assertEquals(result.getStatusCode(),
//						StatusCode.NO_SUCH_MERCHANT);
//	}
//
//	/**
//	 * 
//	 * @throws Exception
//	 */
	@Test
	public void findMerchant_defaultOrgFoundButDisabled() throws Exception {
		final String channelId = "5";
		final String orgId = "2061";

		final Channel channel = new Channel();
		channel.setId("5");

		final String code = "SpringTour";
		final Long merchantId = 1L;
		final String key = "w3c";
		final String name = " ";
		final String state = Merchant.State.DISABLED.getCode();

		final Merchant merchant = new Merchant();
		merchant.setChannel(channel);
		merchant.setCode(code);
		merchant.setId(merchantId);
		merchant.setKey(key);
		merchant.setName(name);
		merchant.setOrgId(orgId);
		merchant.setState(state);

		final FindMerchantArgument arg = new FindMerchantArgument();
		arg.setChannelId(channelId);
		arg.setOrgId(orgId);

		context.checking(new Expectations() {
			{
				oneOf(transactingService).findMerchant(arg.getChannelId(),
						arg.getOrgId());
				will(throwException(new Exception()));

				// oneOf(merchantRepository)
				// .find(arg.getChannelId(), defaultOrgId);
				// will(returnValue(null));
			}
		});

		FindMerchantResult result = target.findMerchant(arg);

		Assert
				.assertEquals(result.getStatusCode(),
						StatusCode.UNKNOWN_ERROR);
	}
}
