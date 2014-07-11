/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.FixtureClearer;
import com.springtour.otg.domain.model.partner.*;
import com.springtour.otg.infrastructure.persistence.ibatis.*;
import com.springtour.otg.integration.*;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IBatisPartnerRepositoryImplIntegrationTests extends AbstractOtgApplicationContextIntegrationTests {

    @Resource(name = "otg.IBatisPartnerRepositoryImpl")
    private IBatisPartnerRepositoryImpl target;
    @Resource(name = "jdbcFixtureClearer")
    private FixtureClearer fixtureClearer;
    private static Partner EXPECT;

    @Before
    public void resetFixture() throws Exception {
        Partner expect = new PartnerFixture().build();
        fixtureClearer.clear("t_otg_partner",
                new String[]{"id"},
                new String[]{expect.getId()});
        target.store(expect);
        EXPECT = expect;
    }

    @Test
    public void updateAvailableCurrencies() throws Exception {
        Partner reconsititute = assertResetFixture();

        final String availableChannels = "1,2";
        reconsititute.updateAvailableChannels(availableChannels);
        update(reconsititute);

        Partner actual = target.find(EXPECT.getId());
        Assert.assertTrue(new PartnerMatcher().matchesSafely(reconsititute, actual));
    }
    
     @Test
    public void updateRecommendedGateways() throws Exception {
        Partner reconsititute = assertResetFixture();

        List<RecommendedGateway> recommendedGateways = Arrays.asList(new RecommendedGateway(0, "12", "CMBC"));
        reconsititute.updateRecommendedGateways(recommendedGateways);
        update(reconsititute);

        Partner actual = target.find(EXPECT.getId());
        Assert.assertTrue(new PartnerMatcher().matchesSafely(reconsititute, actual));
    }
    
    private Partner assertResetFixture() {
        Partner reconsititute = target.find(EXPECT.getId());
        //Assert.assertTrue(new PartnerMatcher().matchesSafely(EXPECT, reconsititute));
        return reconsititute;
    }
    
    private void update(Partner partner) throws Exception {
        target.store(partner);
        addVersion(partner);
    }

    private void addVersion(Partner partner) {
        partner.setVersion(partner.getVersion() + 1);
    }
}
