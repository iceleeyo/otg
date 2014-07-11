/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.dbop;

import java.util.List;
import java.util.Map;
import org.dom4j.DocumentException;

/**
 *
 * @author Future
 */
public class DataPrepareOperation {

    private List<String> dataFilePath;
    private List<DataPrepareOperationDao> dao;
    private List<String> appendDataFilePath;
    private List<DataPrepareOperationDao> appendDao;
    private DataReader dataReader;

    public void prepareData() throws DocumentException {
        for (int i = 0; i < dataFilePath.size(); i++) {
            String filePath = dataFilePath.get(i);
            List<Map<String, String>> data = dataReader.readerXmlFile(this.getClass().getResourceAsStream(filePath));
            DataPrepareOperationDao prepare = dao.get(i);
            prepare.clear();
            prepare.prepareData(data);
        }
        //appendData();
    }

    public void appendData() throws DocumentException {
        if (appendDataFilePath != null && appendDao != null) {
            for (int i = 0; i < appendDataFilePath.size(); i++) {
                String filePath = appendDataFilePath.get(i);
                List<Map<String, String>> data = dataReader.readerXmlFile(this.getClass().getResourceAsStream(filePath));
                appendDao.get(i).appendData(data);
            }
        }
    }
    
    public void clearSpecifiedData() throws DocumentException {
        if (appendDataFilePath != null && appendDao != null) {
            for (int i = 0; i < appendDataFilePath.size(); i++) {
                String filePath = appendDataFilePath.get(i);
                List<Map<String, String>> data = dataReader.readerXmlFile(this.getClass().getResourceAsStream(filePath));
                appendDao.get(i).clear(data);
            }
        }
    }

    public void setDao(List<DataPrepareOperationDao> dao) {
        this.dao = dao;
    }

    public void setDataFilePath(List<String> dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public void setAppendDao(List<DataPrepareOperationDao> appendDao) {
        this.appendDao = appendDao;
    }

    public void setAppendDataFilePath(List<String> appendDataFilePath) {
        this.appendDataFilePath = appendDataFilePath;
    }
}
