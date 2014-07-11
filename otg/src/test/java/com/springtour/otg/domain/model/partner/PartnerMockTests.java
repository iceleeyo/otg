package com.springtour.otg.domain.model.partner;

import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.jmock.lib.legacy.*;
import org.junit.*;
import org.junit.runner.*;

import com.springtour.otg.domain.model.channel.*;

@RunWith(JMock.class)
public class PartnerMockTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private Partner target;
    private ChannelRepository channelRepository = context.mock(ChannelRepository.class);
    private static final String SPRINGCARD_ID = "2";
    private static final String SHFFT_ID = "4";
    private static final String CHINAPNR_ID = "12";//id中包含2和springcard重复
    private static final String BILL99_ID = "15";

    @Before
    public void setUp() {
        target = new Partner("1", "hehe");
        target.setRecommendedGateways(Arrays.asList(new RecommendedGateway(1, "cmb", CHINAPNR_ID), new RecommendedGateway(2, "ccb", CHINAPNR_ID)));
        target.setAvailableChannels(SPRINGCARD_ID + "," + SHFFT_ID + "," + CHINAPNR_ID);
    }

    /**
     * 5
     * 
     * @throws Exception
     */
    @Test
    public void returnsAvailableChannelIds() throws Exception {
        List<String> channelIds = target.availableChannelIds();

        Assert.assertTrue(channelIds.size() == 3);
        Assert.assertEquals(SPRINGCARD_ID, channelIds.get(0));
        Assert.assertEquals(SHFFT_ID, channelIds.get(1));
        Assert.assertEquals(CHINAPNR_ID, channelIds.get(2));
        Assert.assertTrue(target.available(new Channel(SHFFT_ID)));
        Assert.assertTrue(!target.available(new Channel(BILL99_ID)));
    }

    /**
     * 5
     * 
     * @throws Exception
     */
    @Test
    public void returnsAnEmptyAvailableChannelIdList() throws Exception {
        target.setAvailableChannels(null);
        List<String> channelIds = target.availableChannelIds();

        Assert.assertTrue(channelIds.isEmpty());
        Assert.assertTrue(!target.available(new Channel(BILL99_ID)));
    }

    /**
     * 5
     * 
     * @throws Exception
     */
    @Test
    public void returnsAListContainsOnlyOneChannelId() throws Exception {
        target.setAvailableChannels(SHFFT_ID);
        List<String> channelIds = target.availableChannelIds();

        Assert.assertTrue(channelIds.size() == 1);
        Assert.assertEquals(SHFFT_ID, channelIds.get(0));
        Assert.assertTrue(target.available(new Channel(SHFFT_ID)));
        Assert.assertTrue(!target.available(new Channel(BILL99_ID)));
    }

    @Test
    public void returnsAvailableChannels() throws Exception {
        final List<Channel> allChannels = Arrays.asList(new Channel(SPRINGCARD_ID), new Channel(SHFFT_ID), new Channel(CHINAPNR_ID), new Channel(BILL99_ID));

        context.checking(new Expectations() {

            {
                {
                    oneOf(channelRepository).listAll();
                    will(returnValue(allChannels));

                }
            }
        });
        List<Channel> channels = target.availableChannels(channelRepository);
        Assert.assertTrue(channels.size() == 3);
        Assert.assertTrue(channels.get(0).sameIdentityAs(SPRINGCARD_ID));
        Assert.assertTrue(channels.get(1).sameIdentityAs(SHFFT_ID));
        Assert.assertTrue(channels.get(2).sameIdentityAs(CHINAPNR_ID));
    }
    
    @Test
    public void returnsPartOfAvailableChannelsGivenFewerChannels() throws Exception {
        final List<Channel> allChannels = Arrays.asList(new Channel(SPRINGCARD_ID), new Channel(CHINAPNR_ID));

        context.checking(new Expectations() {

            {
                {
                    oneOf(channelRepository).listAll();
                    will(returnValue(allChannels));

                }
            }
        });
        target.setAvailableChannels(CHINAPNR_ID+ "," + SPRINGCARD_ID + "," + SHFFT_ID );//渠道顺序并不以id大写排列
        List<Channel> channels = target.availableChannels(channelRepository);
        Assert.assertTrue(channels.size() == 2);
        
        Assert.assertTrue(channels.get(0).sameIdentityAs(CHINAPNR_ID));
        Assert.assertTrue(channels.get(1).sameIdentityAs(SPRINGCARD_ID));
    }

    @Test
    public void updateAvailableChannels() throws Exception {
        final String availableChannels = target.availableChannels(Arrays.asList("1", "12"));
        target.updateAvailableChannels(availableChannels);

        Assert.assertEquals("1,12", target.getAvailableChannels());
    }

    @Test
    public void clearAvailableChannels() throws Exception {
        final String availableChannels = target.availableChannels(Collections.EMPTY_LIST);
        target.updateAvailableChannels(availableChannels);

        Assert.assertEquals("", target.getAvailableChannels());
    }
}
