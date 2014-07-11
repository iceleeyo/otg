/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.FixtureClearer;

import com.springtour.otg.domain.model.merchant.*;
import com.springtour.otg.infrastructure.persistence.ibatis.*;
import com.springtour.otg.integration.*;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IBatisMerchantRepositoryImplIntegrationTests extends AbstractOtgApplicationContextIntegrationTests {

    @Resource(name = "otg.IBatisMerchantRepositoryImpl")
    private IBatisMerchantRepositoryImpl target;
    @Resource(name = "jdbcFixtureClearer")
    private FixtureClearer fixtureClearer;
    private static Merchant EXPECT;

    @Before
    public void resetFixture() throws Exception {
        Merchant expect = new MerchantFixture().build();
        fixtureClearer.clear("t_merchant",
                new String[]{"merchant_branch_id", "merchant_channel"},
                new String[]{expect.getOrgId(), expect.getChannel().getId()});
        target.store(expect);
        EXPECT = expect;
    }

    @Test
    public void updateAll() throws Exception {
        Merchant reconsititute = assertResetFixture();

        final String name = "ORMupdate";
        final String key = "1,2";
        
        reconsititute.updateName(name);
        reconsititute.updateKey(key);
        update(reconsititute);

        Merchant actual = target.find(EXPECT.getChannel().getId(), EXPECT.getOrgId());
        Assert.assertTrue(new MerchantMatcher().matchesSafely(reconsititute, actual));
    }
    
    private Merchant assertResetFixture() {
        Merchant reconsititute = target.find(EXPECT.getChannel().getId(), EXPECT.getOrgId());
        Assert.assertTrue(new MerchantMatcher().matchesSafely(EXPECT, reconsititute));
        return reconsititute;
    }
    
    private void update(Merchant aggreate) throws Exception {
        target.store(aggreate);
        addVersion(aggreate);
    }

    private void addVersion(Merchant aggreate) {
        aggreate.setVersion(aggreate.getVersion() + 1);
    }
}
