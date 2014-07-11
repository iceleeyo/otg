/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.bosh;

import java.util.Map;

import lombok.Setter;

import org.apache.log4j.Logger;

import com.koalii.svs.SvsVerify;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

/**
 * 此处为类说明
 * @author 010586
 * @date 2014-4-8
 */
public class BoshNotificationValidatorImpl implements NotificationValidator {
    
    Logger log = LoggerFactory.getLogger();
    
    @Setter
    private Configurations configurations;

    @Override
    public boolean validate(Map<String, String> originalNotification) throws CannotLaunchSecurityProcedureException {
        boolean isVaild = false;
        String szData = originalNotification.get("signDataStr");
        String szSignedData = originalNotification.get("signData");
        try {
            final String keyfilePath = this.getClass().getResource(configurations.getBoshPublicKeyPath()).getPath();
            int nRet = SvsVerify.verify (szData.getBytes(), szSignedData, keyfilePath);
            if (0 == nRet)
            {
                isVaild =  true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CannotLaunchSecurityProcedureException("bosh verifySign failed", e);
        }
        return isVaild;
    }

}
