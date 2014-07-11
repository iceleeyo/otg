package com.springtour.otg.interfaces.transacting.facade.rs;

import com.springtour.otg.interfaces.transacting.facade.dto.AvailableChannelDto;
import com.springtour.otg.interfaces.transacting.facade.dto.RecommendedGatewayDto;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class AvailableGatewaysRs extends GenericRs {

    /**
     * 推荐网关列表
     */
    private List<RecommendedGatewayDto> recommendedGateways;
    /**
     * 可用渠道列表
     */
    private List<AvailableChannelDto> availableChannels;
    
    public AvailableGatewaysRs(String success, List<RecommendedGatewayDto> recommendedGateways,List<AvailableChannelDto> availableChannels) {
        super(success);
        this.recommendedGateways = recommendedGateways;
        this.availableChannels = availableChannels;
    }

    public AvailableGatewaysRs(String statusCode) {
        super(statusCode);
    }

    public List<RecommendedGatewayDto> getRecommendedGateways() {
        return recommendedGateways;
    }

    public List<AvailableChannelDto> getAvailableChannels() {
        return availableChannels;
    }

    @Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
