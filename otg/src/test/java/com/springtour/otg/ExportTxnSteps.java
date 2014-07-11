package com.springtour.otg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.springtour.otg.interfaces.admin.web.TransactionExportController;
import com.springtour.otg.interfaces.admin.web.command.ListTransactionsCommand;
import com.springtour.test.AbstractSpringContextDbunitTests;
import com.springtour.test.ApplicationContextGateway;
import com.springtour.test.TestDoubleBeanPostProcessor;

import cucumber.api.java.*;
import cucumber.api.java.en.*;

public class ExportTxnSteps extends AbstractSpringContextDbunitTests {

	private TransactionExportController target;
	private MockHttpServletRequest request = new MockHttpServletRequest();
	public MockHttpServletResponse response = new MockHttpServletResponse();
	private String transactionNoEq = "201012120001";

	@Before(value = "@ExportTxnSteps")
	public void before() throws DatabaseUnitException, SQLException, Exception {
		TestDoubleBeanPostProcessor.resetContext();
		final ApplicationContextGateway applicationContext = ApplicationContextGateway
				.newInstance();
		target = (TransactionExportController) applicationContext
				.get("otg.TransactionExportController");
	
	}
	@Given("^一笔交易，流水号为201012120001$")
	public void prepareData() throws DatabaseUnitException, SQLException,
			Exception {
		DatabaseOperation.DELETE.execute(getConnection(),
				getXlsDataSetFrom(getXlsOfExpectForSaved()));
		DatabaseOperation.INSERT.execute(getConnection(),
				getXlsDataSetFrom(getXlsOfExpectForSaved()));
	}

	protected String getXlsOfExpectForSaved() {
		return "com/springtour/test/data/prepare_for_exportTxn.xls";
	}

	protected IDataSet getXlsDataSetFrom(String fileName) throws Exception {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(fileName);
		return new XlsDataSet(inputStream);
	}


	@When("^导出交易$")
	public void 导出交易() throws Throwable {
		ListTransactionsCommand command = new ListTransactionsCommand();
		command.setApplicationEq("");
		command.setChannelIdEq("");
		command.setMerchantCodeEq("");
		command.setOrderIdEq("");
		command.setOrderNoEq("");
		command.setStateEq("");
		command.setWhenRequestedGt("");
		command.setWhenRequestedLt("");
		command.setTransactionNoEq(transactionNoEq);
		command.setPartnerId("");
		target.exportTransactions(request, response, command);
	}

	@Then("^excel中有且仅有一条数据,如下$")
	public void assertData(List<Map<String, String>> expected) throws Throwable {
		HSSFRow row = getSheet(response.getContentAsByteArray()).getRow(2);
		Map<String, String> expectedRow = expected.get(0);
		assertEquals(expectedRow.get("订单号"),  row.getCell(0).toString());
		assertEquals(expectedRow.get("对接伙伴"),  row.getCell(1).toString());
		assertEquals(expectedRow.get("交易流水号"),  row.getCell(2).toString());
		assertEquals(expectedRow.get("金额"),  row.getCell(3).toString());
		assertEquals(expectedRow.get("状态"),  row.getCell(4).toString());
		//因hsqldb会对时间默认加上时区+8,故时间不做比较
		assertEquals(expectedRow.get("请求时间"),  row.getCell(5).toString().substring(0, 10));
		assertEquals(expectedRow.get("完成时间"),  row.getCell(6).toString().substring(0, 10));
		assertEquals(expectedRow.get("渠道"),  row.getCell(7).toString());
		assertEquals(expectedRow.get("核对状态"),  row.getCell(8).toString());
	}

	public HSSFSheet getSheet(final byte[] bytes) throws IOException{
		final PipedInputStream pin = new PipedInputStream();
		final PipedOutputStream pout = new PipedOutputStream(pin);
		
		final CountDownLatch latch = new CountDownLatch(1);
		
		InputStream in = new InputStream(){

			@Override
			public int read() throws IOException {
				try{
					return pin.read();
				}catch (Exception e) {
					return -1;
				}
			}
			
			@Override
			public void close() throws IOException{
				super.close();
				latch.countDown();
			}
			
		};
		
		final OutputStream out = new OutputStream(){

			@Override
			public void write(int b) throws IOException {
				pout.write(b);
			}
			
			@Override
			public void close() throws IOException{
				while(latch.getCount()!=0){
					try{
						latch.await();
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				super.close();
			}
			
		};
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					out.write(bytes);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		return sheet;
	}

}
