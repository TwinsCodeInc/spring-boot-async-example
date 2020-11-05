package example.springboot.async.async;

import static com.google.common.util.concurrent.Futures.transformAsync;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import example.springboot.async.model.Channel;
import example.springboot.async.model.Permissions;
import example.springboot.async.model.Result;
import example.springboot.async.model.User;
import example.springboot.async.service.ChannelService;
import example.springboot.async.service.PermissionsService;
import example.springboot.async.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListenableFutureAsyncTest
{
	@Autowired
	private UserService userService;

	@Autowired
	private PermissionsService permissionService;

	@Autowired
	private ChannelService channelService;

	@Test(timeout = 1500)
	public void testHasChannel() throws Exception
	{
		ListenableFuture<Channel> lChannel = channelService.lookupChannelListenable("ABC");
		ListenableFuture<User> lUser = userService.lookupUserListenable("test");
		ListenableFuture<Permissions> lPermissions = transformAsync(lUser, user -> permissionService.lookupPermissionsListenable(user.getId()));

		ListenableFuture<List<Object>> data = Futures.allAsList(lChannel, lPermissions);
		ListenableFuture<Result> resultListenable = Futures.transform(data, new Function<List<Object>, Result>()
		{
			@Override
			public Result apply(List<Object> list)
			{
				return new Result((Channel) list.get(0), (Permissions) list.get(1));
			}
		});

		Result result = resultListenable.get();
		assertNotNull(result);
		assertNotNull(result.getChannel());
		assertNotNull(result.getChannel());
		assertTrue(result.getPermissions().hasPermission("permission1"));
		assertNotNull(lUser);
	}
}
