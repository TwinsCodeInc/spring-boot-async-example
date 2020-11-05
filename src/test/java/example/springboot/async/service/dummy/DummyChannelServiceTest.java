package example.springboot.async.service.dummy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.util.concurrent.ListenableFuture;

import example.springboot.async.model.Channel;
import example.springboot.async.service.ChannelService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DummyChannelServiceTest
{
	private static final String CHANNEL_NAME = null;

	@Autowired
	@Qualifier("dummy-channel-service")
	private ChannelService channelService;

	@Test
	public void testLookupChannelSync()
	{
		Channel channel = channelService.lookupChannelSync(CHANNEL_NAME);
		verifyChannel(channel);
	}

	@Test
	public void testLookupChannelAsync() throws InterruptedException, ExecutionException
	{
		Future<Channel> future = channelService.lookupChannelAsync(CHANNEL_NAME);
		Channel channel = future.get();
		verifyChannel(channel);
	}

	@Test
	public void testLookupChannelListenable() throws InterruptedException, ExecutionException
	{
		ListenableFuture<Channel> listenable = channelService.lookupChannelListenable(CHANNEL_NAME);
		Channel channel = listenable.get();
		verifyChannel(channel);
	}

	@Test
	public void testLookupChannelCompletable() throws InterruptedException, ExecutionException
	{
		CompletableFuture<Channel> completable = channelService.lookupChannelCompletable(CHANNEL_NAME);
		Channel channel = completable.get();
		verifyChannel(channel);
	}

	private void verifyChannel(Channel channel)
	{
		assertNotNull(channel);
		assertEquals(0, channel.getId());
		assertEquals(CHANNEL_NAME, channel.getName());
	}
}
