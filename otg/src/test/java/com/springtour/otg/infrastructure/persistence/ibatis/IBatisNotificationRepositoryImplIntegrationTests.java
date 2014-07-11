/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.FixtureClearer;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationFixture;
import com.springtour.otg.infrastructure.persistence.ibatis.*;
import com.springtour.otg.infrastructure.time.Clock;
import com.springtour.otg.integration.*;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IBatisNotificationRepositoryImplIntegrationTests extends AbstractOtgApplicationContextIntegrationTests {

    @Resource(name = "otg.IBatisNotificationRepositoryImpl")
    private IBatisNotificationRepositoryImpl target;
    @Resource(name = "otg.IBatisClockImpl")
    private Clock clock;
    @Resource(name = "jdbcFixtureClearer")
    private FixtureClearer fixtureClearer;
    private static Notification EXPECT;
    
    @Before
    public void resetFixture() throws Exception {
        String sequence = target.nextSequence();
        Notification expect = new NotificationFixture(clock).specifySequence(sequence).build();
        fixtureClearer.clear("t_otg_notification",
                new String[]{"sequence"},
                new String[]{sequence});
        target.store(expect);
        EXPECT = expect;
    }

    @Test
    public void save() throws Exception {
        Notification reconsititute = assertResetFixture();
    }

    private Notification assertResetFixture() {
        Notification reconsititute = target.find(EXPECT.getSequence());
        //Assert.assertTrue(new NotificationMatcher().matchesSafely(EXPECT, reconsititute));
        return reconsititute;
    }
}
