package example.springboot.async.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Permissions implements Serializable
{
	private long id;
	private Map<String, Boolean> permissions = new HashMap<>();

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public void allowPermission(String permission)
	{
		permissions.put(permission, true);
	}

	public void removePermission(String permission)
	{
		permissions.put(permission, false);
	}

	public boolean hasPermission(String permission)
	{
		return permissions.get(permission);
	}
}
