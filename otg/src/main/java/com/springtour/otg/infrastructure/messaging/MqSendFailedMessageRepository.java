/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.messaging;

import java.util.List;

/**
 *
 * @author Future
 */
public interface MqSendFailedMessageRepository {

    Object store(FailedMessage message);

    List<FailedMessage> loadAll();
    
    int delete(Long id);
}
