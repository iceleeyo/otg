/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rs;

import com.springtour.otg.interfaces.admin.facade.dto.ChannelDto;
import java.util.List;

/**
 *
 * @author 006874
 */
public class ListAvailableChannelsRs extends GenericRs {
    
    private  List<ChannelDto> channels;

    public List<ChannelDto> getChannels() {
        return channels;
    }

      public ListAvailableChannelsRs(String statusCode, List<ChannelDto> channels) {
        super(statusCode);
        this.channels = channels;
    }

    public ListAvailableChannelsRs(String statusCode, String message) {
        super(statusCode, message);
    }@Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
