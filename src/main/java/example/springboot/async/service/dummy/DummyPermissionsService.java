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

import example.springboot.async.model.Permissions;
import example.springboot.async.service.PermissionsService;

@Service("dummy-permission-service")
public class DummyPermissionsService implements PermissionsService
{
	private static final long DELAY = 500;

	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private final ListeningScheduledExecutorService listenableExecutor = MoreExecutors.listeningDecorator(executor);

	/**
	 * Wait for 500ms then create dummy permissions
	 * 
	 * @param userId
	 * @return permissions
	 */
	public Permissions lookupPermissionsSync(long userId)
	{
		try
		{
			Thread.sleep(DELAY);
		}
		catch (Exception e){}

		return createPermissions();
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a Future
	 * 
	 * @param userId
	 * @return future
	 */
	@Async
	public Future<Permissions> lookupPermissionsAsync(long userId)
	{
		return executor.schedule(() -> {return createPermissions();}, DELAY, TimeUnit.MILLISECONDS);
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a
	 * ListenableFuture
	 * 
	 * @param userId
	 * @return listenablefuture
	 */
	public ListenableFuture<Permissions> lookupPermissionsListenable(long userId)
	{
		return listenableExecutor.schedule(() -> {return createPermissions();}, DELAY, TimeUnit.MILLISECONDS);
	}

	/**
	 * Wait for 500ms then schedule creation of a dummy channel using a
	 * CompletableFuture
	 * 
	 * @param userId
	 * @return completablefuture
	 */
	@Async
	public CompletableFuture<Permissions> lookupPermissionsCompletable(long userId)
	{
		CompletableFuture<Permissions> future = new CompletableFuture<>();
		executor.schedule(() -> {future.complete(createPermissions());}, DELAY, TimeUnit.MILLISECONDS);
		return future;
	}

	/**
	 * Create dummy permissions
	 * 
	 * @return permissions
	 */
	private Permissions createPermissions()
	{
		Permissions result = new Permissions();
		result.setId(0);
		result.allowPermission("permission1");
		result.removePermission("permission2");

		return result;
	}
}
