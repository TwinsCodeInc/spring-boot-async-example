package example.springboot.async.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.ListenableFuture;

import example.springboot.async.model.Channel;

public interface ChannelService
{
	/**
	 * Find a channel using the channel name as the key
	 * 
	 * @param channelName
	 * @return channel
	 */
	Channel lookupChannelSync(String channelName);

	/**
	 * Find a channel using Java's asynchronous Future and the channel name as
	 * the key
	 * 
	 * @param channelName
	 * @return channel
	 */
	Future<Channel> lookupChannelAsync(String channelName);

	/**
	 * Find a channel using Guava asynchronous ListenableFuture and the channel
	 * name as the key
	 * 
	 * @param channelName
	 * @return channel
	 */
	ListenableFuture<Channel> lookupChannelListenable(String channelName);

	/**
	 * Find a channel using Java 8 asynchronous CompletableFuture and the
	 * channel name as the key
	 * 
	 * @param channelName
	 * @return channel
	 */
	CompletableFuture<Channel> lookupChannelCompletable(String channelName);
}
