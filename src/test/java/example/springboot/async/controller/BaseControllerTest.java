package example.springboot.async.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class BaseControllerTest
{
	protected static final String USER_PATH = "/user/test";
	protected static final String PERMISSIONS_PATH = "/user/test/";
	protected static final String CHANNEL_PATH = "/watch/test/permission1/abc";

	protected static final String SUCCESS_PERMISSION = "permission1";
	protected static final String FAIL_PERMISSION = "permission2";

	@Autowired
	protected MockMvc mockMvc;

	public void testGetUser() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders
			.get(getBasePath() + USER_PATH)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("tester test"));
	}

	public void testHasPermission(String permissionName, String expectedResult) throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders
			.get(getBasePath() + PERMISSIONS_PATH + permissionName)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(expectedResult));
	}

	public void testChannelAvailable() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders
			.get(getBasePath() + CHANNEL_PATH)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	protected abstract String getBasePath();
}
