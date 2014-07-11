/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.FixtureClearer;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.ChannelFixture;
import com.springtour.otg.domain.model.channel.Gateway;
import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.infrastructure.persistence.ibatis.IBatisChannelRepositoryImpl;
import com.springtour.otg.integration.*;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IBatisChannelRepositoryImplIntegrationTests extends AbstractOtgApplicationContextIntegrationTests {

    @Resource(name = "otg.IBatisChannelRepositoryImpl")
    private IBatisChannelRepositoryImpl target;
    @Resource(name = "jdbcFixtureClearer")
    private FixtureClearer fixtureClearer;
    private static Channel EXPECT;

    @Before
    public void resetFixture() throws Exception {
        Channel expect = new ChannelFixture().build();
        fixtureClearer.clear("t_otg_channel",
                new String[]{"id"},
                new String[]{expect.getId()});
        target.store(expect);
        EXPECT = expect;
    }

    @Test
    //@Rollback(true)
    public void updateAvailableCurrencies() throws Exception {
        Channel reconsititute = assertResetFixture();

        final String currencies = "CNY";
        reconsititute.updateAvailableCurrencies(currencies);
        update(reconsititute);

        Channel actual = target.find(EXPECT.getId());
        Assert.assertTrue(new ChannelMatcher().matchesSafely(reconsititute, actual));
    }
    
    @Test
    public void updateGateways() throws Exception {
        Channel reconsititute = assertResetFixture();

        final List<Gateway> gateways = Arrays.asList(new Gateway(Gateways.ABC, "YX", 0), new Gateway(Gateways.BOC, "XX", 1));
        reconsititute.updateGateways(gateways);
        update(reconsititute);

        Channel actual = target.find(EXPECT.getId());
        Assert.assertTrue(new ChannelMatcher().matchesSafely(reconsititute, actual));
    }

    private Channel assertResetFixture() {
        Channel reconsititute = target.find(EXPECT.getId());
        Assert.assertTrue(new ChannelMatcher().matchesSafely(EXPECT, reconsititute));
        return reconsititute;
    }

    private void update(Channel channel) throws Exception {
        target.store(channel);
        addVersion(channel);
    }

    private void addVersion(Channel channel) {
        channel.setVersion(channel.getVersion() + 1);
    }
}
