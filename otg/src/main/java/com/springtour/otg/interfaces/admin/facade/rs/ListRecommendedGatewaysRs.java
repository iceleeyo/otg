/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rs;

import com.springtour.otg.interfaces.transacting.facade.dto.RecommendedGatewayDto;
import java.util.List;

/**
 *
 * @author 006874
 */
public class ListRecommendedGatewaysRs extends GenericRs {

    private List<RecommendedGatewayDto> recommendedGateways;

    public List<RecommendedGatewayDto> getRecommendedGateways() {
        return recommendedGateways;
    }

    public ListRecommendedGatewaysRs(String statusCode, List<RecommendedGatewayDto> recommendedGateways) {
        super(statusCode);
        this.recommendedGateways = recommendedGateways;
    }

    public ListRecommendedGatewaysRs(String statusCode, String message) {
        super(statusCode, message);
    }@Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
