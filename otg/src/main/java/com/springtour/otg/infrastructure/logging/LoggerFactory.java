/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.logging;

import org.apache.log4j.Logger;

/**
 *
 * @author Future
 */
public class LoggerFactory {

    private static Logger logger = Logger.getLogger("OTG_FILE");

    public static Logger getLogger() {
        return logger;
    }
}
