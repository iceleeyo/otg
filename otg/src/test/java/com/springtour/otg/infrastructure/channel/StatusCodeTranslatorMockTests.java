package com.springtour.otg.infrastructure.channel;

import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.jmock.lib.legacy.*;
import org.junit.*;
import org.junit.runner.*;

import com.springtour.otg.application.exception.*;

@RunWith(JMock.class)
public class StatusCodeTranslatorMockTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private StatusCodeTranslator target;

    @Before
    public void initTarget() {
        target = new StatusCodeTranslator();

    }

    /**
     * 5
     * 
     * @throws Exception
     */
    @Test
    public void returnsInvalidCardHolder() throws Exception {
        final String dialect = "5";

        target.setInvalidCardHolderList(Arrays.asList(dialect));

        String expect = target.convertTo(dialect);

        Assert.assertEquals(StatusCode.INVALID_CARD_HOLDER, expect);
    }

    /**
     * 5
     * 
     * @throws Exception
     */
    @Test
    public void returnsInvalidCardInfo() throws Exception {
        final String dialect = "5";

        target.setInvalidCardInfoList(Arrays.asList(dialect));

        String expect = target.convertTo(dialect);

        Assert.assertEquals(StatusCode.INVALID_CARD_INFO, expect);
    }

    @Test
    public void returnsUnsupportedService() throws Exception {
        final String dialect = "5";

        target.setUnsupportedServiceList(Arrays.asList(dialect));

        String expect = target.convertTo(dialect);

        Assert.assertEquals(StatusCode.UNSUPPORTED_SERVICE, expect);
    }

    @Test
    public void returnsUnavailableCredit() throws Exception {
        final String dialect = "5";

        target.setUnavailableCreditList(Arrays.asList(dialect));

        String expect = target.convertTo(dialect);

        Assert.assertEquals(StatusCode.UNAVAILABLE_CREDIT, expect);
    }

    @Test
    public void returnsDialectWhenNonmatches() throws Exception {
        final String dialect = "5";

        String expect = target.convertTo(dialect);

        Assert.assertEquals(dialect, expect);
    }
}
