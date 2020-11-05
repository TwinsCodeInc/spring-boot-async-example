package example.springboot.async.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import example.springboot.async.model.Channel;
import example.springboot.async.model.Permissions;
import example.springboot.async.model.User;
import example.springboot.async.service.ChannelService;
import example.springboot.async.service.PermissionsService;
import example.springboot.async.service.UserService;

@RestController("/springsync")
public class SyncController
{
	@Autowired
	private UserService userService;

	@Autowired
	private PermissionsService permissionService;

	@Autowired
	private ChannelService channelService;

	@RequestMapping(value = "/springsync/user/{username}", method = RequestMethod.GET)
	public String getUser(@PathVariable("username") String userName)
	{
		User user = userService.lookupUserSync(userName);
		return user.getFirstName() + " " + user.getLastName();
	}

	@RequestMapping(value = "/springsync/user/{username}/{permission}", method = RequestMethod.GET)
	public boolean getPermission(@PathVariable("username") String userName, @PathVariable("permission") String permission)
	{
		User user = userService.lookupUserSync(userName);
		Permissions permissions = permissionService.lookupPermissionsSync(user.getId());
		return permissions.hasPermission(permission);
	}

	@RequestMapping(value = "/springsync/watch/{username}/{permission}/{channel}", method = RequestMethod.GET)
	public boolean watchChannel(@PathVariable("username") String userName, @PathVariable("permission") String permission, @PathVariable("channel") String channel)
	{
		User user = userService.lookupUserSync(userName);
		Permissions permissions = permissionService.lookupPermissionsSync(user.getId());
		Channel result = channelService.lookupChannelSync(channel);
		return result != null && permissions.hasPermission(permission);
	}
}
