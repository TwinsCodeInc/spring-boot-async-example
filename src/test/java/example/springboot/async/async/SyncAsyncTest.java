package example.springboot.async.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import example.springboot.async.controller.SyncController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SyncAsyncTest
{
	@Autowired
	private SyncController controller;

	@Test(timeout = 2000)
	public void testHasChannel()
	{
		boolean result = controller.watchChannel("tester", "permission1", "ABC");
		assertNotNull(result);
		assertTrue(result);
	}
}
