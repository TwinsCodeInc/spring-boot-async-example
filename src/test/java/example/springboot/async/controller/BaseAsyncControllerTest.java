package example.springboot.async.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class BaseAsyncControllerTest extends BaseControllerTest
{
	@Override
	public void testGetUser() throws Exception
	{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
			.get(getBasePath() + USER_PATH)
			.param("testGetUser", "true"))
			.andExpect(request().asyncStarted())
			.andReturn();

		mockMvc.perform(
			asyncDispatch(mvcResult))
			.andExpect(status().isOk())
			.andExpect(content().string("tester test"));
	}

	@Override
	public void testHasPermission(String permissionName, String expectedResult) throws Exception
	{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
			.get(getBasePath() + PERMISSIONS_PATH + permissionName)
			.param("testHasPermission", "true"))
			.andExpect(request().asyncStarted())
			.andReturn();

		mockMvc.perform(asyncDispatch(mvcResult))
			.andExpect(status().isOk())
			.andExpect(content().string(expectedResult));
	}

	@Override
	public void testChannelAvailable() throws Exception
	{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
			.get(getBasePath() + CHANNEL_PATH)
			.param("testChannelAvailable", "true"))
			.andExpect(request().asyncStarted())
			.andReturn();

		mockMvc.perform(
			asyncDispatch(mvcResult))
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}
}
