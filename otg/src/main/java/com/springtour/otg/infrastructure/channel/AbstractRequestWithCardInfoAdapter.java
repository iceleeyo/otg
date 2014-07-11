/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.domain.service.RequestWithCardInfoService;
import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.shared.IdentityType;
import com.springtour.otg.infrastructure.channel.chinapnr.ChinapnrIdentityTypeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 006874
 */
public abstract class AbstractRequestWithCardInfoAdapter implements RequestWithCardInfoService {

    private String channel;

    public AbstractRequestWithCardInfoAdapter(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public List<IdentityType> availableIdentityTypes(String channelId) {
        List<IdentityType> result = new ArrayList<IdentityType>();
        for (IdentityType type : IdentityType.values()) {
            try {
                dialect(type);
            } catch (CannotMapIdentityTypeException e) {
                continue;
            }
            result.add(type);
        }
        return result;
    }

    protected abstract String dialect(IdentityType identityType) throws CannotMapIdentityTypeException;
}
