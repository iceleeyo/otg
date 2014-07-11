package com.springtour.otg.infrastructure.channel.bocomm;

import java.io.File;
import java.io.FileWriter;

import lombok.Setter;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class BocommUtils {
    Logger log = LoggerFactory.getLogger();

    @Setter
    private Configurations configurations;
    
    public void init(){
        String path = this.getClass().getResource(configurations.getBocommXmlFileResourceName()).getPath();
        String pfxFilePath = this.getClass().getResource(configurations.getBocommPfxFileName()).getPath().replaceAll("\\\\", "\\\\\\\\");
        String certFilePath = this.getClass().getResource(configurations.getBocommCertFileName()).getPath().replaceAll("\\\\", "\\\\\\\\");
        log.info("xmlPath:" + path);
        log.info("pfxFilePath:" + pfxFilePath);
        log.info("certFilePath:" + certFilePath);
        File file = new File(path);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(file);
            Element root = document.getRootElement();
            root.selectSingleNode("MerchantCertFile").setText(pfxFilePath);
            root.selectSingleNode("RootCertFile").setText(certFilePath);
            FileWriter fw = new FileWriter(path);
            document.write(fw);
            fw.close();
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
        }
    }
}
