package example.springboot.async.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ListenableFutureControllerTest extends BaseAsyncControllerTest
{
	@Test
	public void testGetUser() throws Exception
	{
		super.testGetUser();
	}

	@Test
	public void testHasPermissionSuccess() throws Exception
	{
		super.testHasPermission(SUCCESS_PERMISSION, "true");
	}

	@Test
	public void testHasPermissionFailure() throws Exception
	{
		super.testHasPermission(FAIL_PERMISSION, "false");
	}

	@Test
	public void testChannelAvailable() throws Exception
	{
		super.testChannelAvailable();
	}

	@Override
	protected String getBasePath()
	{
		return "/springlistenablefuture";
	}
}
