package example.springboot.async.service.dummy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import example.springboot.async.model.User;
import example.springboot.async.service.UserService;

@Service("dummy-user-service")
public class DummyUserService implements UserService
{
	private static final long DELAY = 500;

	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private final ListeningScheduledExecutorService listenableExecutor = MoreExecutors.listeningDecorator(executor);

	/**
	 * Wait for 500ms then create a dummy user
	 *
	 * @param userName
	 * @return user
	 */
	public User lookupUserSync(String userName)
	{
		try
		{
			Thread.sleep(DELAY);
		}
		catch (Exception e){}

		return createUser(userName);
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a Future
	 * 
	 * @param userName
	 * @return future
	 */
	@Async
	public Future<User> lookupUserAsync(String userName)
	{
		return executor.schedule(() -> {return createUser(userName);}, DELAY, TimeUnit.MILLISECONDS);
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a
	 * ListenableFuture
	 * 
	 * @param userName
	 * @return listenablefuture
	 */
	public ListenableFuture<User> lookupUserListenable(String userName)
	{
		return listenableExecutor.schedule(() -> {return createUser(userName);}, DELAY, TimeUnit.MILLISECONDS);
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a
	 * CompletableFuture
	 * 
	 * @param userName
	 * @return completablefuture
	 */
	@Async
	public CompletableFuture<User> lookupUserCompletable(String userName)
	{
		CompletableFuture<User> future = new CompletableFuture<>();
		executor.schedule(() -> {future.complete(createUser(userName));}, DELAY, TimeUnit.MILLISECONDS);
		return future;
	}

	/**
	 * Create a dummy user
	 * 
	 * @return user
	 */
	private User createUser(String userName)
	{
		User result = new User();
		result.setId(0);
		result.setFirstName("tester");
		result.setLastName("test");
		result.setUserName(userName);

		return result;
	}
}
