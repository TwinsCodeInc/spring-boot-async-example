package example.springboot.async.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.ListenableFuture;

import example.springboot.async.model.User;

public interface UserService
{
	/**
	 * Find a user using the username as the key
	 * 
	 * @param userName
	 * @return user
	 */
	User lookupUserSync(String userName);

	/**
	 * Find a user using Java's asynchronous Future and the user name as the key
	 * 
	 * @param userName
	 * @return user
	 */
	Future<User> lookupUserAsync(String userName);

	/**
	 * Find a user using Guava asynchronous ListenableFuture and the user name
	 * as the key
	 * 
	 * @param userName
	 * @return user
	 */
	ListenableFuture<User> lookupUserListenable(String userName);

	/**
	 * Find a user using Java 8 asynchronous CompletableFuture and the user name
	 * as the key
	 * 
	 * @param userName
	 * @return user
	 */
	CompletableFuture<User> lookupUserCompletable(String userName);
}
