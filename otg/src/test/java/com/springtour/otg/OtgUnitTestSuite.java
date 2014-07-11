package com.springtour.otg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.springtour.otg.application.ApplicationUnitTestSuite;
import com.springtour.otg.domain.DomainUnitTestSuite;
import com.springtour.otg.infrastructure.InfrastructureUnitTestSuite;
import com.springtour.otg.interfaces.InterfacesUnitTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { //InterfacesUnitTestSuite.class,
		ApplicationUnitTestSuite.class, DomainUnitTestSuite.class,
		InfrastructureUnitTestSuite.class })
public class OtgUnitTestSuite {
}
