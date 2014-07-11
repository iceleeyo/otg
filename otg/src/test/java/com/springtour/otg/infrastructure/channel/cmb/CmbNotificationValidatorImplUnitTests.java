package com.springtour.otg.infrastructure.channel.cmb;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class CmbNotificationValidatorImplUnitTests {
	private CmbNotificationValidatorImpl target;
	
	@Test
	public void testValidator(){
		target = new CmbNotificationValidatorImpl();
		Map<String, String> originalNotification = new HashMap<String, String>();
		originalNotification.put("Succeed", "Y");
		originalNotification.put("CoNo", "000004");
		originalNotification.put("BillNo", "8104700022");
		originalNotification.put("Amount", "60");
		originalNotification.put("Date", "20071213");
		originalNotification.put("MerchantPara", "8120080420080414701013700022");
		originalNotification.put("Msg", "00270000042007121307321387100000002470");
		originalNotification.put("Signature", "177|48|67|121|22|40|125|29|39|162|103|204|103|156|74|196|63|148|45|142|206|139|243|120|224|193|84|46|216|23|42|29|25|64|232|213|114|3|22|51|131|76|169|143|183|229|87|164|138|77|185|198|116|254|224|68|26|169|194|160|94|35|111|150|");
		
		Assert.assertTrue(target.validate(originalNotification));
	}
	
}
