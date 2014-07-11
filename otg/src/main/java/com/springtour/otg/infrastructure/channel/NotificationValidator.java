package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import java.util.Map;

public interface NotificationValidator {

    boolean validate(Map<String, String> originalNotification) throws CannotLaunchSecurityProcedureException;
}
