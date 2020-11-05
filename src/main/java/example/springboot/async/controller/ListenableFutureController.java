package example.springboot.async.controller;

import static com.google.common.util.concurrent.Futures.transformAsync;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import example.springboot.async.model.Channel;
import example.springboot.async.model.Permissions;
import example.springboot.async.model.Result;
import example.springboot.async.model.User;
import example.springboot.async.service.ChannelService;
import example.springboot.async.service.PermissionsService;
import example.springboot.async.service.UserService;

@RestController("/springlistenablefuture")
public class ListenableFutureController
{
	@Autowired
	private UserService userService;

	@Autowired
	private PermissionsService permissionService;

	@Autowired
	private ChannelService channelService;

	@RequestMapping(value = "/springlistenablefuture/user/{username}", method = RequestMethod.GET)
	public DeferredResult<String> getUser(@PathVariable("username") String userName)
	{
		final DeferredResult<String> deferredResult = new DeferredResult<>();

		ListenableFuture<User> lUser = userService.lookupUserListenable("test");

		Futures.addCallback(lUser, new FutureCallback<User>()
		{
			@Override
			public void onSuccess(User user)
			{
				deferredResult.setResult(user.getFirstName() + " " + user.getLastName());
			}

			@Override
			public void onFailure(Throwable t)
			{
				deferredResult.setErrorResult(t);
			}
		});

		return deferredResult;
	}

	@RequestMapping(value = "/springlistenablefuture/user/{username}/{permission}", method = RequestMethod.GET)
	public DeferredResult<Boolean> getPermission(@PathVariable("username") String userName, @PathVariable("permission") String permission)
	{
		final DeferredResult<Boolean> deferredResult = new DeferredResult<>();

		ListenableFuture<User> lUser = userService.lookupUserListenable("test");
		ListenableFuture<Permissions> lPermissions = transformAsync(lUser, user -> permissionService.lookupPermissionsListenable(user.getId()));

		Futures.addCallback(lPermissions, new FutureCallback<Permissions>()
		{
			@Override
			public void onSuccess(Permissions permissions)
			{
				deferredResult.setResult(permissions.hasPermission(permission));
			}

			@Override
			public void onFailure(Throwable t)
			{
				deferredResult.setErrorResult(t);
			}
		});

		return deferredResult;
	}

	@RequestMapping(value = "/springlistenablefuture/watch/{username}/{permission}/{channel}", method = RequestMethod.GET)
	public DeferredResult<Boolean> watchChannel(@PathVariable("username") String userName, @PathVariable("permission") String permission, @PathVariable("channel") String channel) throws InterruptedException, ExecutionException
	{
		final DeferredResult<Boolean> deferredResult = new DeferredResult<>();

		ListenableFuture<Channel> lChannel = channelService.lookupChannelListenable("ABC");
		ListenableFuture<User> lUser = userService.lookupUserListenable("test");
		ListenableFuture<Permissions> lPermissions = transformAsync(lUser, user -> permissionService.lookupPermissionsListenable(user.getId()));

		ListenableFuture<List<Object>> data = Futures.allAsList(lChannel, lPermissions);
		ListenableFuture<Result> lResult = Futures.transform(data, new Function<List<Object>, Result>()
		{
			@Override
			public Result apply(List<Object> list)
			{
				return new Result((Channel) list.get(0), (Permissions) list.get(1));
			}
		});

		Futures.addCallback(lResult, new FutureCallback<Result>()
		{
			@Override
			public void onSuccess(Result result)
			{
				deferredResult
						.setResult(result.getChannel() != null && result.getPermissions().hasPermission(permission));
			}

			@Override
			public void onFailure(Throwable t)
			{
				deferredResult.setErrorResult(t);
			}
		});

		return deferredResult;
	}
}
