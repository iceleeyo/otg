/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.dbop;

import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 *
 * @author Future
 */
public class DataPrepareOperationDao extends SqlMapClientDaoSupport {

    private String nameSpace;

    public void prepareData(List<Map<String, String>> list) {
       this.createData(list);
    }
    
    public void appendData(List<Map<String, String>> list) {
        this.clear(list);
        this.createData(list);
    }

    public void clear() {
        this.getSqlMapClientTemplate().delete(nameSpace + ".clear");
    }
    
    public void clear(List<Map<String, String>> list) {
         for (int i = 0; i < list.size(); i++) {
            this.getSqlMapClientTemplate().insert(nameSpace + ".clearData", list.get(i));          
        }
    }

    private void createData(List<Map<String, String>> list) {
        for (int i = 0; i < list.size(); i++) {
            this.getSqlMapClientTemplate().insert(nameSpace + ".createData", list.get(i));          
        }
        
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
}
