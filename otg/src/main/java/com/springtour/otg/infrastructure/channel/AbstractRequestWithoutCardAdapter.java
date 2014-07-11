/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.domain.service.RequestWithoutCardInfoService;


/**
 *
 * @author 006874
 */
public abstract class AbstractRequestWithoutCardAdapter implements  RequestWithoutCardInfoService {

    private String channel;
    public AbstractRequestWithoutCardAdapter(String channel){
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    
}
