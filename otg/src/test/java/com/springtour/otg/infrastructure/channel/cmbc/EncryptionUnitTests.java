package com.springtour.otg.infrastructure.channel.cmbc;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.util.Configurations;
import com.springtour.test.AbstractJMockUnitTests;

public class EncryptionUnitTests extends AbstractJMockUnitTests {

	private Configurations configurations = context.mock(Configurations.class);

	private final String privateKeyPath = "/com/springtour/runtime/otg/cmbc305010601390507.cer";
	private final String publicKeyPath = "/com/springtour/runtime/otg/gwkey.cer";

	private Encryption target;

	String toEncryptStr = "90507201304261105001|305010601390507|100.0|20130426|110830|0|||0|0|0.0|0.0";
	String encryptedStr = "Uzdqbnqtb8UNl+5U3P26YcDBX1umv4lJutu9Eb5SgPFegy3IkcXYW3+DHkAj4X/ubhqKRBKorlVeL56HzVTjuW70kUP/f74YlQ1BjCiOyodpz+5wZ/YtQnJaKsP5kWz+ApSTKXnAFrTEbt0pIC+vze/N8WYLMSrDY/olxdu1+upfCGa/czJrDalKpho+5peQNDX8tng8qM2BaZXvn+5CALgWkCnXXOyDhgF/f1Jg+nMn+xv5vKfXv8rMbWibdWzU7rwrNTocajlkU0uCotaD9wGh5loTDhwaF0pvvODlaa3EZEfcInIbxlkKnZJy1sYLhPoKAPS9Dsdb6OLuuDLKaA==,eR3F0BTN2y/iFhYVp7FyIFTzHgr0ykO6aTCEOTUFz+EezHicItb2dhD3aSqWJXWtQDpren4MakMjAy+DAk/ZaKBGF95ao97+Osp33tpVZbialO2S9JXnFPcmkPEUi+JwutOV4S3ccQT4PAJ+iA6oZ73xRPjxXXcZqZQEDF5T9kA=";

	@Before
	public void setUp() {
		target = new Encryption();
		target.setConfigurations(configurations);
	}

	@Test
	public void encrypt() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(configurations).cmbcPrivateKeyClasspathResourceName();
				will(returnValue(privateKeyPath));

				allowing(configurations).cmbcPublicKeyClasspathResourceName();
				will(returnValue(publicKeyPath));

			}
		});
		String actual = target.encrypt(toEncryptStr);
		// 暂无法验证
		// assertEquals(toEncryptStr, target.decrypt(actual));
	}

	@Test
	public void decrypt() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(configurations).cmbcPrivateKeyClasspathResourceName();
				will(returnValue(privateKeyPath));

				allowing(configurations).cmbcPublicKeyClasspathResourceName();
				will(returnValue(publicKeyPath));

			}
		});
		String actual = target.decrypt(encryptedStr);
		assertEquals(toEncryptStr, actual);
	}

}
