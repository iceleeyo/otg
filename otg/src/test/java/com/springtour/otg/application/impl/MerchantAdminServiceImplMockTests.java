package com.springtour.otg.application.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery; //import org.jmock.integration.junit3.MockObjectTestCase;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.springtour.otg.application.exception.DuplicateMerchantException;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.merchant.MerchantRepository;

@RunWith(JMock.class)
public class MerchantAdminServiceImplMockTests {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private MerchantAdminServiceImpl target;

	private MerchantRepository merchantRepository = context
			.mock(MerchantRepository.class);

	private Merchant merchant = context.mock(Merchant.class);

	@Before
	public void setUp() {
		target = new MerchantAdminServiceImpl();
		target.setMerchantRepository(merchantRepository);
	}

	@Test
	public void registerANewMerchant() throws Exception {
		final String name = "merchant name, probably the org's name";
		final String channelId = "1";
		final String orgId = "61";
		final String code = "your merchantCode";
		final String key = "a private key";

		context.checking(new Expectations() {
			{
				oneOf(merchantRepository).store(with(any(Merchant.class)));
			}
		});

		target.register(name, channelId, orgId, code, key);
	}

	@Test(expected = DuplicateMerchantException.class)
	public void throwsExceptionWhenAttemptsToRegisterDuplicateMerchant()
			throws Exception {
		final String name = "merchant name, probably the org's name";
		final String channelId = "1";
		final String orgId = "61";
		final String code = "your merchantCode";
		final String key = "a private key";

		context.checking(new Expectations() {
			{
				oneOf(merchantRepository).store(with(any(Merchant.class)));
				will(throwException(new DuplicateMerchantException(channelId,
						orgId)));
			}
		});

		target.register(name, channelId, orgId, code, key);
	}

	@Test
	public void updateInfoAndSwitchState() throws Exception {
		final String id = "1";
		final String name = "merchant name, probably the org's name";
		final String code = "your merchantCode";
		final String key = "a private key";
		final boolean enabled = true;

		context.checking(new Expectations() {
			{
				oneOf(merchantRepository).find(id);
				will(returnValue(merchant));

				oneOf(merchant).updateName(name);
				oneOf(merchant).updateCode(code);
				oneOf(merchant).updateKey(key);
				oneOf(merchant).switchState(enabled);

				oneOf(merchantRepository).store(merchant);
			}
		});

		target.updateInfo(id, name, code, key, enabled);
	}
}
