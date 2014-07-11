package com.springtour.otg.interfaces.admin.web;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.*;

import javax.servlet.http.*;

import jxl.*;
import jxl.write.*;

import org.apache.commons.beanutils.*;
import org.apache.commons.lang.*;
import org.apache.log4j.*;

import com.springtour.otg.interfaces.admin.facade.dto.NotificationDto;
import com.springtour.otg.interfaces.admin.facade.dto.TransactionDto;

public class ExcelWriter<T> {

	private static final Logger logger = Logger
			.getLogger(ExcelWriter.class);

	public void readAndWrite(HttpServletResponse response, String reportPath,
			String fileName, List<T> dtoList) {
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileName + ".xls");
		WritableWorkbook workbook = null;
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(response.getOutputStream());
			URL url = this.getClass().getClassLoader().getResource(reportPath);
			InputStream is = url.openStream();

			Workbook inputWb = Workbook.getWorkbook(is); 
			Sheet inputWbSheet = inputWb.getSheet(0);
			List<String> propertiesList = new ArrayList<String>();
			for (int i = 0; i < inputWbSheet.getColumns(); i++) {
				Cell cell = inputWbSheet.getCell(i, 2);
				propertiesList.add(cell.getContents());
			}
			workbook = Workbook.createWorkbook(out, inputWb); 
			WritableSheet outputSheet = workbook.getSheet(0);
			int row = 2;
			for (Object record : dtoList) {
				int col = 0;
				for (String prop : propertiesList) {
					if (StringUtils.isNotBlank(prop)) {
						String value = BeanUtils.getProperty(record, prop);
						Label label = new Label(col, row, value);
						outputSheet.addCell(label);
					}
					col++;
				}
				row++;
			}
			workbook.write();
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			try {
				workbook.close();
			} catch (WriteException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
	}
	
}
