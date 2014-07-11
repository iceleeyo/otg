/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.dbop;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Future
 */
public class DataReader {

    public List<Map<String, String>> readerXmlFile(InputStream in) throws DocumentException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        Element root = document.getRootElement();
        List<Element> elements = root.selectNodes("/root/data");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            Map<String, String> map = new HashMap<String, String>();
            for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
                Element ele = it.next();
                map.put(ele.getName(), ele.getTextTrim());
            }
            if ( map.size() > 0 ) {
                list.add(map);
            }
        }
        return list;
    }
}
