package com.springtour.otg.infrastructure.persistence.ibatis;

import java.util.*;

import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.operation.*;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.service.*;
import com.springtour.test.*;

public class IBatisTransactionRepositoryImplPersistenceTests extends
		AbstractPersistenceTestsUsingDbunitTemplate<Transaction, TransactionNo> {

	private static final TransactionNo SAVE_PROTOTYPE_TXN_NO = new TransactionNo(
			"20190101");
	private static final TransactionNo UPDATE_PROTOTYPE_TXN_NO = new TransactionNo(
			"20190103");
	private static final String TXN_NO = "20190102";
	private static final String T_OTG_TRANSACTION = "t_otg_transaction";

	private IBatisTransactionRepositoryImpl target;

	private TransactionChecker checker;

	@Override
	public void onSetUp() throws Exception {
		super.onSetUp();
		this.target = (IBatisTransactionRepositoryImpl) super
				.getApplicationContext().getBean(
						"otg.IBatisTransactionRepositoryImpl");

		this.checker = (TransactionChecker) super.getApplicationContext()
				.getBean("otg.TransactionCheckerImpl");
	}

	private void preparePersistentLifeCycleFixture() throws Exception {

		DatabaseOperation.DELETE.execute(getConnection(),
				super.getPrototypeOfSaved());
		DatabaseOperation.INSERT.execute(getConnection(),
				super.getPrototypeOfSaved());

		DatabaseOperation.DELETE.execute(getConnection(),
				super.getPrototypeOfUpdated());
		DatabaseOperation.INSERT.execute(getConnection(),
				super.getPrototypeOfUpdated());

		DatabaseOperation.DELETE.execute(getConnection(),
				super.getActualSaved());
	}

	public void testPersistentLifeCycle() throws Exception {
		preparePersistentLifeCycleFixture();
		super.doTestSaving();
		super.doTestUpdating(target.find(new TransactionNo(TXN_NO)));
	}

	public void testSelectToBeCheckedTxns() throws Exception {
		Set<String> channels = new HashSet<String>();
		channels.add("95");
		channels.add("98"); // 注意，这里准备的数据中不要和其他冲突，否则容易造成有些交易由于关联不上查不出来

		// 准备的数据中含有 channel 97、96 以及 channel=99或98 但状态不为unchecked的

		System.out.println(checker.aToBeCheckedSpec());

		clearSelectToBeCheckedFixture();

		List<Transaction> before = target.findBy(new ToBeCheckedSpecification(
				channels));

		prepareSelectToBeCheckedFixture();

		// 由于该测试可能运行在不能完全清空数据的环境下，所以应用 适应性测试模式

		List<Transaction> after = target.findBy(new ToBeCheckedSpecification(
				channels));

		assertEquals(before.size() + 2, after.size());
	}

	private void clearSelectToBeCheckedFixture() throws Exception {
		DatabaseOperation.DELETE.execute(getConnection(),
				selectToBeCheckedFixtures());
	}

	public void testAutoIncrementCycleOfNextTransactionNoSequence()
			throws Exception {
		String before = target.nextTransactionNoSequence();
		String after = target.nextTransactionNoSequence();

		// 由于该测试可能运行在不能完全清空数据的环境下，所以应用 适应性测试模式

		// 由于是cycle增长，所以可能after=1来验证重置
		assertTrue(equals(after, oneAdd(before)) || (Long.valueOf(after) == 1));
	}

	private String oneAdd(String before) {
		return "" + (Long.valueOf(before) + 1);
	}

	private boolean equals(String after, String before) {
		return after.equals(before);
	}

	private void prepareSelectToBeCheckedFixture() throws Exception {
		DatabaseOperation.INSERT.execute(getConnection(),
				selectToBeCheckedFixtures());
	}

	private IDataSet selectToBeCheckedFixtures() throws Exception {
		return getXlsDataSetFrom("com/springtour/otg/infrastructure/persistence/transaction/to_be_checked_transaction_fixture.xls");
	}

	@Override
	protected String getXlsOfExpectForSaved() {
		return "com/springtour/otg/infrastructure/persistence/transaction/expect_saved_transaction.xls";
	}

	@Override
	protected String getXlsOfExpectForUpdated() {
		return "com/springtour/otg/infrastructure/persistence/transaction/expect_updated_transaction.xls";
	}

	@Override
	protected String getXlsOfPrototypeForSaved() {
		return "com/springtour/otg/infrastructure/persistence/transaction/prototype_saved_transaction.xls";
	}

	@Override
	protected String getXlsOfPrototypeForUpdated() {
		return "com/springtour/otg/infrastructure/persistence/transaction/prototype_updated_transaction.xls";
	}

	@Override
	protected com.springtour.test.AbstractPersistenceTestsUsingDbunitTemplate.QueryDataSetCriteria[] getCriteriasForSaved() {
		return new QueryDataSetCriteria[] { QueryDataSetCriteria.as(
				T_OTG_TRANSACTION, SELECT_ALL_FROM + T_OTG_TRANSACTION
						+ " where transaction_no=" + TXN_NO) };
	}

	@Override
	protected IDataSet getExpectedSaved() throws Exception {
		return new DefaultDataSet(super.getExpectedSaved().getTable(
				T_OTG_TRANSACTION));
	}

	@Override
	protected com.springtour.test.AbstractPersistenceTestsUsingDbunitTemplate.QueryDataSetCriteria[] getCriteriasForUpdated() {
		return getCriteriasForSaved();
	}

	@Override
	protected Transaction findPrototypeForSaved() {
		return target.find(SAVE_PROTOTYPE_TXN_NO);
	}

	@Override
	protected Transaction copyForSaving(Transaction prototype) {
		// 验证撤销交易
		Transaction transaction = new Transaction(new TransactionNo(TXN_NO),
				prototype.getPartner(), prototype.getAmount(),
				prototype.getWhenRequested(), prototype.getOrderId(),
				prototype.getMerchant(), prototype.getChannel(),
				prototype.getGateway(), prototype.transactionType(),
				prototype.getReferenceTransactionNumber());
		transaction.setChargeFor(prototype.getChargeFor());
		// 以上验证了 getPartner()、getChannel(),getMerchant()的延迟加载

		return transaction;
	}

	@Override
	protected void assertSaved() throws DatabaseUnitException, Exception {
		Assertion.assertEqualsIgnoreCols(getExpectedSaved(), getActualSaved(),
				T_OTG_TRANSACTION, new String[] { "ID" });
	}

	@Override
	protected void assertUpdated() throws DatabaseUnitException, Exception {
		Assertion.assertEqualsIgnoreCols(getExpectedUpdated(),
				getActualUpdated(), T_OTG_TRANSACTION, new String[] { "ID" });
	}

	@Override
	protected void doSave(Transaction saved) {
		target.store(saved);
	}

	@Override
	protected Transaction findPrototypeForUpdated() {
		return target.find(UPDATE_PROTOTYPE_TXN_NO);
	}

	@Override
	protected void copyForUpdating(Transaction target, Transaction prototype) {
		final HandlingActivity handlingActivity = prototype
				.getHandlingActivity();

		target.handle(handlingActivity.getNotification(),
				handlingActivity.getWhenHandled());

		target.regardedAsValidAt(prototype.getUpdateTime());
	}

	@Override
	protected void doUpdate(Transaction updated) {
		target.store(updated);
	}

}
