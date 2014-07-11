package com.springtour.otg.infrastructure.channel;

import static org.junit.Assert.*;

import java.util.*;

import org.jmock.*;
import org.junit.*;

import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.test.*;

public class ChannelDispatcherUnitTests extends AbstractJMockUnitTests {

	private ChannelDispatcher target = new ChannelDispatcher();

	@Test
	public void fowardsToConcreteChannelAdapterWhenQueryBy() throws Throwable {
		final Transaction transaction = new TransactionFixture().build();

		final String channelOfAdapter1 = "channel doesnt match";
		final String channelOfAdapter2 = transaction.getChannel().getId();
		final String channelOfAdapter3 = "another channel doesnt match";

		final ExternalTransactionQueryResult resultGivenByAdapter2 = context
				.mock(ExternalTransactionQueryResult.class,
						"resultGivenByAdapter2");

		final AbstractChannelExternalTransactionQueryObjectAdapter adapter1 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter1");
		final AbstractChannelExternalTransactionQueryObjectAdapter adapter2 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter2");
		final AbstractChannelExternalTransactionQueryObjectAdapter adapter3 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter3");

		target.setChannelExternalTransactionQueryObjectAdapters(Arrays.asList(
				adapter1, adapter2, adapter3));

		context.checking(new Expectations() {
			{
				allowing(adapter1).getChannel();
				will(returnValue(channelOfAdapter1));

				allowing(adapter2).getChannel();
				will(returnValue(channelOfAdapter2));

				allowing(adapter3).getChannel();
				will(returnValue(channelOfAdapter3));

				allowing(adapter2).queryBy(transaction);
				will(returnValue(resultGivenByAdapter2));
			}
		});

		ExternalTransactionQueryResult actual = target.queryBy(transaction);

		assertSame(resultGivenByAdapter2, actual);
	}

	@Test(expected = UnavailableChannelException.class)
	public void throwsWhenQueryByButNoneAdapterMatches() throws Throwable {
		final Transaction transaction = new TransactionFixture().build();

		final String channelOfAdapter1 = "channel doesnt match";
		final String channelOfAdapter2 = "another channel doesnt match";
		final String channelOfAdapter3 = "again another channel doesnt match";

		final AbstractChannelExternalTransactionQueryObjectAdapter adapter1 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter1");
		final AbstractChannelExternalTransactionQueryObjectAdapter adapter2 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter2");
		final AbstractChannelExternalTransactionQueryObjectAdapter adapter3 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter3");

		target.setChannelExternalTransactionQueryObjectAdapters(Arrays.asList(
				adapter1, adapter2, adapter3));

		context.checking(new Expectations() {
			{
				allowing(adapter1).getChannel();
				will(returnValue(channelOfAdapter1));

				allowing(adapter2).getChannel();
				will(returnValue(channelOfAdapter2));

				allowing(adapter3).getChannel();
				will(returnValue(channelOfAdapter3));
			}
		});

		target.queryBy(transaction);
	}

	@Test
	public void collectsSupportedChannelsGivenByAdapters() throws Throwable {

		final Set<String> supportedChannelsOfAdapter1 = new HashSet<String>();
		supportedChannelsOfAdapter1.add("channel1");
		final Set<String> supportedChannelsOfAdapter2 = new HashSet<String>();
		supportedChannelsOfAdapter1.add("channel1");// duplicate
		supportedChannelsOfAdapter1.add("channel2");
		final Set<String> supportedChannelsOfAdapter3 = new HashSet<String>();
		supportedChannelsOfAdapter1.add("channel3");

		final AbstractChannelExternalTransactionQueryObjectAdapter adapter1 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter1");
		final AbstractChannelExternalTransactionQueryObjectAdapter adapter2 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter2");
		final AbstractChannelExternalTransactionQueryObjectAdapter adapter3 = context
				.mock(AbstractChannelExternalTransactionQueryObjectAdapter.class,
						"adapter3");

		target.setChannelExternalTransactionQueryObjectAdapters(Arrays.asList(
				adapter1, adapter2, adapter3));

		context.checking(new Expectations() {
			{
				allowing(adapter1).supportedChannels();
				will(returnValue(supportedChannelsOfAdapter1));

				allowing(adapter2).supportedChannels();
				will(returnValue(supportedChannelsOfAdapter2));

				allowing(adapter3).supportedChannels();
				will(returnValue(supportedChannelsOfAdapter3));
			}
		});

		final Set<String> expect = new HashSet<String>();
		expect.add("channel1");
		expect.add("channel2");
		expect.add("channel3");

		Set<String> actual = target.supportedChannels();
		assertEquals(expect, actual);
	}

}
