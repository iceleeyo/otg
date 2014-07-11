/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;


import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import cn.com.infosec.icbc.ReturnValue;

import com.springtour.otg.infrastructure.channel.icbc.object.IcbcPayResponse;

/**
 * @author 005073
 * 
 */
public class DecodeNotifyData2XmlUnitTests {
	private String notifyData = "PD94bWwgIHZlcnNpb249IjEuMCIgZW5jb2Rpbmc9IkdCSyIgc3RhbmRhbG9uZT0ibm8iID8+PEIyQ1Jlcz48aW50ZXJmYWNlTmFtZT5JQ0JDX1BFUkJBTktfQjJDPC9pbnRlcmZhY2VOYW1lPjxpbnRlcmZhY2VWZXJzaW9uPjEuMC4wLjExPC9pbnRlcmZhY2VWZXJzaW9uPjxvcmRlckluZm8+PG9yZGVyRGF0ZT4yMDEwMDkwMTE2MjE0Mjwvb3JkZXJEYXRlPjxjdXJUeXBlPjAwMTwvY3VyVHlwZT48bWVySUQ+MDIwMEVDMjAwMDExMTk8L21lcklEPjxzdWJPcmRlckluZm9MaXN0PjxzdWJPcmRlckluZm8+PG9yZGVyaWQ+MjAxMDA5MDExNjIxNDIwPC9vcmRlcmlkPjxhbW91bnQ+MTAwPC9hbW91bnQ+PGluc3RhbGxtZW50VGltZXM+MTwvaW5zdGFsbG1lbnRUaW1lcz48bWVyQWNjdD4wMjAwMDI2MDA5MDE4MzcyMjEyPC9tZXJBY2N0Pjx0cmFuU2VyaWFsTm8+SEZHMDAwMDAwMDAwMDAwNDIxPC90cmFuU2VyaWFsTm8+PC9zdWJPcmRlckluZm8+PHN1Yk9yZGVySW5mbz48b3JkZXJpZD4yMDEwMDkwMTE2MjE0MjE8L29yZGVyaWQ+PGFtb3VudD4xMjA8L2Ftb3VudD48aW5zdGFsbG1lbnRUaW1lcz4xPC9pbnN0YWxsbWVudFRpbWVzPjxtZXJBY2N0PjAyMDAwMjYwMDkwMTgzNzIyMTI8L21lckFjY3Q+PHRyYW5TZXJpYWxObz5IRkcwMDAwMDAwMDAwMDA0MjI8L3RyYW5TZXJpYWxObz48L3N1Yk9yZGVySW5mbz48L3N1Yk9yZGVySW5mb0xpc3Q+PC9vcmRlckluZm8+PGN1c3RvbT48dmVyaWZ5Sm9pbkZsYWc+MDwvdmVyaWZ5Sm9pbkZsYWc+PEpvaW5GbGFnPjwvSm9pbkZsYWc+PFVzZXJOdW0+PC9Vc2VyTnVtPjwvY3VzdG9tPjxiYW5rPjxUcmFuQmF0Y2hObz4wMDAwMDAwMDAwMDAwOTg8L1RyYW5CYXRjaE5vPjxub3RpZnlEYXRlPjIwMTAwOTEwMTc0MDUwPC9ub3RpZnlEYXRlPjx0cmFuU3RhdD4xPC90cmFuU3RhdD48Y29tbWVudD69u9LXs8m5pqOhPC9jb21tZW50PjwvYmFuaz48L0IyQ1Jlcz4=";
	//private String notifyData =   "PD94bWwgIHZlcnNpb249IjEuMCIgZW5jb2Rpbmc9IkdCSyIgc3RhbmRhbG9uZT0ibm8iID8+PEIyQ1Jlcz48aW50ZXJmYWNlTmFtZT5JQ0JDX1BFUkJBTktfQjJDPC9pbnRlcmZhY2VOYW1lPjxpbnRlcmZhY2VWZXJzaW9uPjEuMC4wLjExPC9pbnRlcmZhY2VWZXJzaW9uPjxvcmRlckluZm8+PG9yZGVyRGF0ZT4yMDEzMDkzMDE2MTMwMzwvb3JkZXJEYXRlPjxjdXJUeXBlPjAwMTwvY3VyVHlwZT48bWVySUQ+MTAwMUVDMjM4Mjg3NjE8L21lcklEPjxzdWJPcmRlckluZm9MaXN0PjxzdWJPcmRlckluZm8+PG9yZGVyaWQ+MjAxMzA3MjIxMjM0MTIyMjwvb3JkZXJpZD48YW1vdW50PjEwMDwvYW1vdW50PjxpbnN0YWxsbWVudFRpbWVzPjE8L2luc3RhbGxtZW50VGltZXM+PG1lckFjY3Q+MTAwMTIyMzYwOTAwNDY0MTA2NjwvbWVyQWNjdD48dHJhblNlcmlhbE5vPkhGRzAwMDAwMzY4NzMyNDUzNzwvdHJhblNlcmlhbE5vPjwvc3ViT3JkZXJJbmZvPjwvc3ViT3JkZXJJbmZvTGlzdD48L29yZGVySW5mbz48Y3VzdG9tPjx2ZXJpZnlKb2luRmxhZz4wPC92ZXJpZnlKb2luRmxhZz48Sm9pbkZsYWc+PC9Kb2luRmxhZz48VXNlck51bT48L1VzZXJOdW0+PC9jdXN0b20+PGJhbms+PFRyYW5CYXRjaE5vPjwvVHJhbkJhdGNoTm8+PG5vdGlmeURhdGU+MjAxMzA5MzAxNjExMjk8L25vdGlmeURhdGU+PHRyYW5TdGF0PjE8L3RyYW5TdGF0Pjxjb21tZW50Pr270tezybmmo6E8L2NvbW1lbnQ+PC9iYW5rPjwvQjJDUmVzPg==";
	private DecodeNotifyData2Xml target;

	@Before
	public void prepare() {
		this.target = new DecodeNotifyData2Xml();
	}

	@Test
	public void transform() throws UnsupportedEncodingException {
		IcbcPayResponse response = this.target.decodeAndTransform(notifyData);
		assertEquals(response.getInterfaceName(), "ICBC_PERBANK_B2C");
		assertEquals(response.getOrderInfo().getMerId(), "0200EC20001119");
		assertEquals(response.getOrderInfo().getSubOrderInfos().get(0).getTransactionId(), "201009011621420");
		assertEquals(response.getOrderInfo().getSubOrderInfos().get(0).getTranSerialNo(), "HFG000000000000421");
		assertEquals(response.getCustom().getVerifyJoinFlag(), "0");
		assertEquals(response.getBank().getTranStat(), "1");
	}
}
