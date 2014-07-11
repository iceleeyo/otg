/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal;

import com.springtour.otg.integration.AbstractOtgIntegrationTests;
import com.springtour.otg.interfaces.admin.facade.PartnerAdminServiceFacade;
import com.springtour.otg.interfaces.admin.facade.dto.ChannelDto;
import com.springtour.otg.interfaces.admin.facade.dto.PartnerDto;
import com.springtour.otg.interfaces.transacting.facade.dto.RecommendedGatewayDto;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author 006874
 */
public class PartnerAdminServiceFacadeImplSpringContextTests extends AbstractOtgIntegrationTests {

    private PartnerAdminServiceFacade target;

    @Override
    public void onSetUp() {
        target = (PartnerAdminServiceFacade) super.getApplicationContext().getBean("otg.PartnerAdminServiceFacadeImpl");
    }
//
//    @Test
//    public void testListAvailChannels() {
//        List<ChannelDto> dtos = target.listAvailableChannels("1");
//        System.out.println(dtos);
//        Assert.assertTrue(dtos.size() > 0);
//    }
//
//    //  @Test
//    public void testRegister() {
//        //  target.register("-12", "test");
//    }
//
//    @Test
//    public void testListAllPartners() {
//        List<PartnerDto> partners = target.listAllPartners();
//        System.out.println("partners : " + partners);
//        Assert.assertTrue(partners.size() > 0);
//    }
//
//    @Test
//    public void testUpdateAvailableChannels() {
//        List<String> channels = new ArrayList<String>();
//        channels.add("2");
//        channels.add("5");
//        channels.add("7");
//        channels.add("9");
//        channels.add("12");
//        channels.add("15");
//        channels.add("-11");
//        target.updateAvailableChannels("1", channels);
//    }
//
//    @Test
//    public void testListRecommendedGateways() {
//
//        List<RecommendedGatewayDto> dtos = target.listRecommendedGateways("1");
//        System.out.println(dtos);
//    }
//
//    @Test
//    public void testUpdateRecommendedGateways() {
//        List<RecommendedGatewayDto> dtos = new ArrayList<RecommendedGatewayDto>();
//        RecommendedGatewayDto dto1 = new RecommendedGatewayDto();
//
//        dto1.setChannel("12");
//        dto1.setGateway("CMBC");
//        dto1.setPriority(1);
//        dtos.add(dto1);
//        target.updateRecommendedGateways("1", dtos);
//    }
}
