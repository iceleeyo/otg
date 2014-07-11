package com.springtour.otg.domain.model.partner;

import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.ChannelRepository;
import com.springtour.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Partner {

    private String id;
    private int version = 1;
    private String name;
    private List<RecommendedGateway> recommendedGateways;
    private String availableChannels;
    private static final String AVAILABLE_CHANNEL_SPERATOR = ",";
    private boolean availableChannelsUpdated = false;
    private boolean recommendedGatewaysUpdated = false;
    private boolean persistent = true;

    public Partner(String partnerId, String name) {
        this.id = partnerId;
        this.name = name;
        this.persistent = false;
    }

    public boolean available(Channel channel) {
        if (channel == null) {
            return false;
        }
        for (String channelId : availableChannelIds()) {
            if (channelId.equals(channel.getId())) {
                return true;
            }
        }
        return false;
    }

    public String availableChannels(List<String> availableChannelIds) {
        StringBuilder StringBuilder = new StringBuilder();
        for (int i = 0; i < ObjectUtils.nullSafe(availableChannelIds, Collections.EMPTY_LIST).size(); i++) {
            StringBuilder.append(availableChannelIds.get(i).trim());//去除空格
            if (i < availableChannelIds.size() - 1) {
                StringBuilder.append(AVAILABLE_CHANNEL_SPERATOR);
            }
        }
        return StringBuilder.toString();
    }

    public List<String> availableChannelIds() {
        if (availableChannels == null) {
            return Collections.EMPTY_LIST;
        } else {
            final String[] channelIds = availableChannels.split(AVAILABLE_CHANNEL_SPERATOR);
            return Arrays.asList(channelIds);
        }
    }

    public List<Channel> availableChannels(ChannelRepository channelRepository) {
        if (availableChannels == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<Channel> result = new ArrayList<Channel>();
            List<Channel> allChannels = channelRepository.listAll();
            for (String channelId : this.availableChannelIds()) {
                for (Channel channel : allChannels) {
                    if (channelId.equals(channel.getId())) {
                        result.add(channel);
                        break;
                    }
                }
            }
            return result;
        }
    }

    public void updateAvailableChannels(String availableChannelIds) {
        this.setAvailableChannels(availableChannelIds);
        this.availableChannelsUpdated = true;
    }

    public void updateRecommendedGateways(List<RecommendedGateway> recommendedGateways) {
        this.setRecommendedGateways(recommendedGateways);
        this.recommendedGatewaysUpdated = true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, StandardToStringStyle.SHORT_PREFIX_STYLE).append(id).toString();
    }

    public Partner() {
    }

    public List<RecommendedGateway> getRecommendedGateways() {
        return recommendedGateways;
    }

    public void setRecommendedGateways(List<RecommendedGateway> recommendedGateways) {
        this.recommendedGateways = recommendedGateways;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailableChannels() {
        return availableChannels;
    }

    public void setAvailableChannels(String availableChannels) {
        this.availableChannels = availableChannels;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void resetUpdated() {
        this.availableChannelsUpdated = false;
        this.recommendedGatewaysUpdated = false;
    }

    public boolean isUpdated() {
        return isAvailableChannelsUpdated() || isRecommendedGatewaysUpdated();
    }

    public boolean isAvailableChannelsUpdated() {
        return availableChannelsUpdated;
    }

    public boolean isRecommendedGatewaysUpdated() {
        return recommendedGatewaysUpdated;
    }

    public boolean isPersistent() {
        return persistent;
    }
    
    public void resetPersistent() {
        this.persistent = true;
    }
}
