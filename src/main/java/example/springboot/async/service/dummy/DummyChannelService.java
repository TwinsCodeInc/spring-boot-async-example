package example.springboot.async.service.dummy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import example.springboot.async.model.Channel;
import example.springboot.async.service.ChannelService;

@Service("dummy-channel-service")
public class DummyChannelService implements ChannelService
{
	private static final long DELAY = 500;

	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private final ListeningScheduledExecutorService listenableExecutor = MoreExecutors.listeningDecorator(executor);

	/**
	 * Wait for 500ms then create a dummy channel
	 * 
	 * @param userId
	 * @return channel
	 */
	public Channel lookupChannelSync(String channelName)
	{
		try
		{
			Thread.sleep(DELAY);
		}
		catch (Exception e){}

		return createChannel(channelName);
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a Future
	 * 
	 * @param userId
	 * @return future
	 */
	@Async
	public Future<Channel> lookupChannelAsync(String channelName)
	{
		return executor.schedule(() -> {return createChannel(channelName);}, DELAY, TimeUnit.MILLISECONDS);
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a
	 * ListenableFuture
	 * 
	 * @param userId
	 * @return listenablefuture
	 */
	public ListenableFuture<Channel> lookupChannelListenable(String channelName)
	{
		return listenableExecutor.schedule(() -> {return createChannel(channelName);}, DELAY, TimeUnit.MILLISECONDS);
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a
	 * CompletableFuture
	 * 
	 * @param userId
	 * @return completableFuture
	 */
	@Async
	public CompletableFuture<Channel> lookupChannelCompletable(String channelName)
	{
		CompletableFuture<Channel> future = new CompletableFuture<>();
		executor.schedule(() -> {future.complete(createChannel(channelName));}, DELAY, TimeUnit.MILLISECONDS);
		return future;
	}

	/**
	 * Create a dummy channel
	 * 
	 * @return channel
	 */
	private Channel createChannel(String channelName)
	{
		Channel channel = new Channel();
		channel.setName(channelName);
		return channel;
	}
}
