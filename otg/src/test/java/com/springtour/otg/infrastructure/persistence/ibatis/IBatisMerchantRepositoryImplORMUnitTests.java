package com.springtour.otg.infrastructure.persistence.ibatis;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.spring.tour.ormunit.PersistenceTestsStrategy;
import com.spring.tour.ormunit.TxnCallback;
import com.spring.tour.ormunit.ibatis.IBatisPersistenceTests;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.application.exception.DuplicateMerchantException;
import com.springtour.otg.domain.model.merchant.Merchant;

public class IBatisMerchantRepositoryImplORMUnitTests extends
		IBatisPersistenceTests {
	private static String MERCHANT_ID;
	private static String CHANNEL_ID = "88";
	private static String ORG_ID = "77";

	@Override
	protected String[] getConfigLocations() {
		return null;

	}

	@Override
	protected String getParentFactoryKey() {
		return "ormunit.IBatisOrmunitContext.xml";
	}

	@Override
	protected String getParentSelector() {
		return "classpath:com/springtour/otg/ibatis-ormunit-context.xml";
	}

	private IBatisMerchantRepositoryImpl target;

	public IBatisMerchantRepositoryImplORMUnitTests() {
		super();
		setAutowireMode(AUTOWIRE_NO);
		ApplicationContext context = getParentApplicationContext();
		setStrategy((PersistenceTestsStrategy) context
				.getBean("ormunit.DataSourceSimplePersistenceTestsStrategyImpl"));
		setSqlMapClientTemplate((SqlMapClientTemplate) context
				.getBean("ormunit.SqlMapClientTemplate"));
	}

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		target = new IBatisMerchantRepositoryImpl();
		target.setSqlMapClientTemplate(getSqlMapClientTemplate());
	}

	public void testClearScenario() throws Exception {
		System.out.println("---------------------------");
		System.out.println("testClearScenario begins:");
		System.out.println("***************************");

		final Merchant merchant = target.find(CHANNEL_ID, ORG_ID);
		doWithTransaction(new TxnCallback() {
			@Override
			public void execute() throws Throwable {
				getSqlMapClientTemplate().delete(
						"IBatisMerchantRepositoryImplORMUnitTests.remove",
						merchant);
			}
		});
		System.out.println("---------------------");
		System.out.println("testClearScenario ends:");
		System.out.println("*********************");
		System.out.println();
	}

	public void testSave() throws Exception {
		System.out.println("---------------------------");
		System.out.println("testSave begins:");
		System.out.println("***************************");

		final String name = "TEST MERCHANT NAME";
		final String channelId = CHANNEL_ID;
		final String orgId = ORG_ID;
		final String code = "870437";
		final String key = "830234";

		doWithTransaction(new TxnCallback() {
			@Override
			public void execute() throws Throwable {
				Merchant merchant = new Merchant(name, new Channel(channelId),
						orgId, code, key);

				target.store(merchant);
			}
		});

		Merchant after = target.find(channelId, orgId);
		MERCHANT_ID = after.getId().toString();
		assertTrue("doesnt get surrogate key!", after.getId() != null);
		assertEquals("merchant name", name, after.getName());
		assertEquals("channel", channelId, after.getChannel().getId()
				.toString());
		assertEquals("orgId", orgId, after.getOrgId());
		assertEquals("code", code, after.getCode());
		assertEquals("key", key, after.getKey());

		System.out.println("---------------------");
		System.out.println("testSave ends:");
		System.out.println("*********************");
		System.out.println();
	}

	public void testThrowsExceptionWhenSavesDuplicateMerchant()
			throws Exception {
		System.out.println("---------------------------");
		System.out
				.println("testThrowsExceptionWhenSavesDuplicateMerchant begins:");
		System.out.println("***************************");

		final String name = "TEST MERCHANT NAME";
		final String channelId = CHANNEL_ID;
		final String orgId = ORG_ID;
		final String code = "870437";
		final String key = "830234";
		Exception ex = null;
		try {
			doWithTransaction(new TxnCallback() {
				@Override
				public void execute() throws Throwable {
					Merchant merchant = new Merchant(name, new Channel(
							channelId), orgId, code, key);

					target.store(merchant);
				}
			});
		} catch (DuplicateMerchantException e) {
			ex = e;
		}

		assertTrue("duplicate merchant!",
				ex instanceof DuplicateMerchantException);

		System.out.println("---------------------");
		System.out
				.println("testThrowsExceptionWhenSavesDuplicateMerchant ends:");
		System.out.println("*********************");
		System.out.println();
	}

	public void testUpdate() throws Exception {
		System.out.println("---------------------------");
		System.out.println("testUpdate begins:");
		System.out.println("***************************");

		final String name = "TEST MERCHANT NAME";
		final String channelId = CHANNEL_ID;
		final String orgId = ORG_ID;
		final String code = "870437";
		final String key = "830234";

		doWithTransaction(new TxnCallback() {
			@Override
			public void execute() throws Throwable {
				Merchant merchant = target.find(MERCHANT_ID);
				merchant.updateCode(code);
				merchant.updateKey(key);
				merchant.updateName(name);
				merchant.switchState(false);
				target.store(merchant);
			}
		});

		Merchant after = target.find(channelId, orgId);

		assertEquals("id differs", MERCHANT_ID, after.getId().toString());
		assertEquals("merchant name", name, after.getName());
		assertEquals("channel", channelId, after.getChannel().getId()
				.toString());
		assertEquals("orgId", orgId, after.getOrgId());
		assertEquals("code", code, after.getCode());
		assertEquals("key", key, after.getKey());
		assertEquals("state", Merchant.State.DISABLED.getCode(), after
				.getState());

		System.out.println("---------------------");
		System.out.println("testUpdate ends:");
		System.out.println("*********************");
		System.out.println();
	}

	public void testFindEnabledByChannelAndCode() throws Exception {
		System.out.println("---------------------------");
		System.out.println("testFindEnabledByChannelAndCode begins:");
		System.out.println("***************************");

		final String channelId = "12";
		final String merchantCode = "870437";

		Merchant merchant = target.findEnabledByChannelAndCode(channelId,
				merchantCode);

		assertTrue(merchant != null);
		System.out.println(merchant.toString());

		System.out.println("---------------------");
		System.out.println("testFindEnabledByChannelAndCode ends:");
		System.out.println("*********************");
		System.out.println();
	}

	public void testCount() throws Exception {
		System.out.println("---------------------------");
		System.out.println("testCount begins:");
		System.out.println("***************************");

		final String channelId = "5";
		final String orgId = "";

		long counts = target.count(channelId, orgId);

		assertTrue(counts != 0);

		System.out.println("---------------------");
		System.out.println("testCount ends:");
		System.out.println("*********************");
		System.out.println();
	}

	public void testList() throws Exception {
		System.out.println("---------------------------");
		System.out.println("testList begins:");
		System.out.println("***************************");

		final String channelId = "5";
		final String orgId = "";

		List<Merchant> list = target.list(channelId, orgId, 1l, 50);

		assertTrue(list.size() != 0);

		System.out.println("---------------------");
		System.out.println("testList ends:");
		System.out.println("*********************");
		System.out.println();
	}

}