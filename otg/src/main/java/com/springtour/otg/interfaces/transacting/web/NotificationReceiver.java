package com.springtour.otg.interfaces.transacting.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.Controller;

public interface NotificationReceiver extends Controller {

    void receive(HttpServletRequest request, HttpServletResponse response)
            throws Exception;

    String getChannel();
}
