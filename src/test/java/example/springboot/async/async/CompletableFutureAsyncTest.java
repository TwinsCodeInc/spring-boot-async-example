package example.springboot.async.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import example.springboot.async.model.Channel;
import example.springboot.async.model.Permissions;
import example.springboot.async.model.Result;
import example.springboot.async.model.User;
import example.springboot.async.service.ChannelService;
import example.springboot.async.service.PermissionsService;
import example.springboot.async.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompletableFutureAsyncTest
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
		CompletableFuture<Channel> cChannel = channelService.lookupChannelCompletable("ABC");
		CompletableFuture<User> cUser = userService.lookupUserCompletable("test");
		CompletableFuture<Permissions> cPermissions = cUser.thenCompose(u -> permissionService.lookupPermissionsCompletable(u.getId()));
		CompletableFuture<Result> cResult = cPermissions.thenCombine(cChannel, (permissions, channel) -> new Result(channel, permissions));

		Result result = cResult.get();
		assertNotNull(result);
		assertNotNull(result.getChannel());
		assertNotNull(result.getPermissions());
		assertTrue(result.getPermissions().hasPermission("permission1"));
	}
}
