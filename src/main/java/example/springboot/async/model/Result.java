package example.springboot.async.model;

import java.io.Serializable;

public class Result implements Serializable
{
	private Channel channel;
	private Permissions permissions;

	public Result(Channel channel, Permissions permissions)
	{
		this.channel = channel;
		this.permissions = permissions;
	}

	public Channel getChannel()
	{
		return channel;
	}

	public void setChannel(Channel channel)
	{
		this.channel = channel;
	}

	public Permissions getPermissions()
	{
		return permissions;
	}

	public void setPermissions(Permissions permissions)
	{
		this.permissions = permissions;
	}
}
