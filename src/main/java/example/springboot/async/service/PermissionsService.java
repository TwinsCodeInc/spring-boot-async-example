package example.springboot.async.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.ListenableFuture;

import example.springboot.async.model.Permissions;

public interface PermissionsService
{
	/**
	 * Find a permissions using the permissions name as the key
	 * 
	 * @param userId
	 * @return permissions
	 */
	Permissions lookupPermissionsSync(long userId);

	/**
	 * Find a permissions using Java's asynchronous Future and the permissions
	 * name as the key
	 * 
	 * @param userId
	 * @return permissions
	 */
	Future<Permissions> lookupPermissionsAsync(long userId);

	/**
	 * Find a permissions using Guava asynchronous ListenableFuture and the
	 * permissions name as the key
	 * 
	 * @param userId
	 * @return permissions
	 */
	ListenableFuture<Permissions> lookupPermissionsListenable(long userId);

	/**
	 * Find a permissions using Java 8 asynchronous CompletableFuture and the
	 * permissions name as the key
	 * 
	 * @param userId
	 * @return permissions
	 */
	CompletableFuture<Permissions> lookupPermissionsCompletable(long userId);
}
