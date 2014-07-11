package com.springtour.otg;

import com.springtour.otg.uat.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ChannelAdminHappyPathUserAcceptanceTests.class, PartnerAdminHappyPathUserAcceptanceTests.class})
public class OtgUatHappyPathTestSuite {
}
