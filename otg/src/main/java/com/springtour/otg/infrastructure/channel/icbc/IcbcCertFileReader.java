/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import java.io.IOException;
import java.io.InputStream;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;

/**
 * @author 005073
 *
 */
public class IcbcCertFileReader {
	public byte[] fileReader(String filePath) {
		InputStream inputStream = this.getClass().getResourceAsStream(filePath);
		byte[] bs = null;
		try {
			bs = new byte[inputStream.available()];
			inputStream.read(bs);
		} catch (IOException e) {
			throw new CannotLaunchSecurityProcedureException(
					"icbc cert file read failed!", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}

			} catch (IOException e) {
			}
		}
		return bs;
	}
}