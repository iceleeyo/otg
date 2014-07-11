package com.springtour.otg.infrastructure.channel.icbc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.channel.icbc.object.IcbcQueryResponse;

public class ThrowableExternalTransactionQueryResultConverterUnitTests {

	private ThrowableExternalTransactionQueryResultConverter target;

	@Before
	public void init() {
		target = new ThrowableExternalTransactionQueryResultConverter();
	}

	@Test
	public void xmlToData() {
		String xmlData = "<ICBCAPI><pub><APIName>EAPI</APIName>"
				+ "<APIVersion>001.001.002.001</APIVersion>"
				+ "</pub><in><orderNum>20130826349822112</orderNum>"
				+ "<tranDate>20130828</tranDate>"
				+ "<ShopCode>1001EC23742710</ShopCode>"
				+ "<ShopAccount>1001223619006890378</ShopAccount>" + "</in>"
				+ "<out>"
				+ "	<tranSerialNum>HFG000003937748809</tranSerialNum>"
				+ "<tranStat>1</tranStat>" + "	<bankRem></bankRem>"
				+ "	<amount>100</amount>" + "	<currType>001</currType>"
				+ "	<tranTime>144340</tranTime>"
				+ "	<ShopAccount>1001223619006890378</ShopAccount>"
				+ "	<PayeeName>朽屠杉拉肉变凹逊德屿拱儿缠德屿</PayeeName>"
				+ "		<JoinFlag>0</JoinFlag>" + "	<MerJoinFlag></MerJoinFlag>"
				+ "	<CustJoinFlag>0</CustJoinFlag>"
				+ "	<CustJoinNum></CustJoinNum>"
				+ "	<CertID>cq20130828.e.1001</CertID>" + "		</out>"
				+ "	</ICBCAPI>";

		IcbcQueryResponse response = target.xmlToData(xmlData);
		assertEquals("EAPI", response.getPub().getApiName());
		assertEquals("001.001.002.001", response.getPub().getApiVersion());
		assertEquals("20130826349822112", response.getIn().getOrderNum());
		assertEquals("20130828", response.getIn().getTranDate());
		assertEquals("1001223619006890378", response.getIn().getShopAccount());
		assertEquals("1001EC23742710", response.getIn().getShopCode());

		assertEquals("100", response.getOut().getAmount());
		assertEquals("", response.getOut().getBankRem());
		assertEquals("cq20130828.e.1001", response.getOut().getCertID());
		assertEquals("001", response.getOut().getCurrType());
		assertEquals("0", response.getOut().getCustJoinFlag());
		assertEquals("", response.getOut().getCustJoinNum());
		assertEquals("0", response.getOut().getJoinFlag());
		assertEquals("朽屠杉拉肉变凹逊德屿拱儿缠德屿", response.getOut().getPayeeName());
		assertEquals("1001223619006890378", response.getOut().getShopAccount());
		assertEquals("HFG000003937748809", response.getOut().getTranSerialNum());
		assertEquals("1", response.getOut().getTranStat());
		assertEquals("144340", response.getOut().getTranTime());

	}

	@Test
	public void convert() {
		String xmlData = "<ICBCAPI><pub><APIName>EAPI</APIName>"
				+ "<APIVersion>001.001.002.001</APIVersion>"
				+ "</pub><in><orderNum>20130826349822112</orderNum>"
				+ "<tranDate>20130828</tranDate>"
				+ "<ShopCode>1001EC23742710</ShopCode>"
				+ "<ShopAccount>1001223619006890378</ShopAccount>" + "</in>"
				+ "<out>"
				+ "	<tranSerialNum>HFG000003937748809</tranSerialNum>"
				+ "<tranStat>1</tranStat>" + "	<bankRem></bankRem>"
				+ "	<amount>100</amount>" + "	<currType>001</currType>"
				+ "	<tranTime>144340</tranTime>"
				+ "	<ShopAccount>1001223619006890378</ShopAccount>"
				+ "	<PayeeName>朽屠杉拉肉变凹逊德屿拱儿缠德屿</PayeeName>"
				+ "		<JoinFlag>0</JoinFlag>" + "	<MerJoinFlag></MerJoinFlag>"
				+ "	<CustJoinFlag>0</CustJoinFlag>"
				+ "	<CustJoinNum></CustJoinNum>"
				+ "	<CertID>cq20130828.e.1001</CertID>" + "		</out>"
				+ "	</ICBCAPI>";
		ExternalTransactionQueryResult result = target
				.convertToExternalTransactionQueryResult(xmlData);
		assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
		assertEquals("HFG000003937748809", result.extTxnNo());
		assertThat(result.amountFromSupplier().getAmount(),
				Matchers.comparesEqualTo(new BigDecimal(1)));
	}
}
