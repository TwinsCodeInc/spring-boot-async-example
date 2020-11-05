package example.springboot.async.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import example.springboot.async.model.Channel;
import example.springboot.async.model.Permissions;
import example.springboot.async.model.User;
import example.springboot.async.service.ChannelService;
import example.springboot.async.service.PermissionsService;
import example.springboot.async.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FutureAsyncTest
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
		Future<Channel> fChannel = channelService.lookupChannelAsync("ABC");

		Future<User> fUser = userService.lookupUserAsync("test");
		User user = fUser.get();

		Future<Permissions> fPermissions = permissionService.lookupPermissionsAsync(user.getId());
		Permissions userPermissions = fPermissions.get();

		Channel channel = fChannel.get();

		assertNotNull(channel);
		assertTrue(userPermissions.hasPermission("permission1"));
		assertNotNull(user);
	}
}
