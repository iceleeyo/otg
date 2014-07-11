package com.springtour.otg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    OtgUnitTestSuite.class, OtgIntegrationTestSuite.class})
public class OtgPrecommitTestSuite {
}
