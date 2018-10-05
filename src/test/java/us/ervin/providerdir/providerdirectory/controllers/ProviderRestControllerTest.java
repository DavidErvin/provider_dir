package us.ervin.providerdir.providerdirectory.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import us.ervin.providerdir.providerdirectory.ProviderDirectoryApplication;
import us.ervin.providerdir.providerdirectory.domain.Provider;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = WebEnvironment.RANDOM_PORT,
		classes = ProviderDirectoryApplication.class)
public class ProviderRestControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
	@Test
	public void testGetProviders() throws Exception {
		String content = mockMvc.perform(get("/provider"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$..id").isArray())
			.andReturn().getResponse().getContentAsString();
		
		assertThat(content, containsString("Juday Family Practice"));
	}
	
	
	@Test
	public void testGetSingleProvider() throws Exception {
		mockMvc.perform(get("/provider/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.specialty", is("Pediatrics")));
	}
	
	
	@Test
	public void testGetSingleProviderDoesntExist() throws Exception {
		mockMvc.perform(get("/provider/100000"))
			.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void testAddProvider() throws Exception {
		Provider p = new Provider();
		p.setEmail("somebody@example.com");
		p.setFirstName("Some");
		p.setLastName("Body");
		p.setSpecialty("Phlebotomy");
		p.setPracticeName("Example bloodletters");
		
		mockMvc.perform(post("/provider", p))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", containsString("provider/7")))
			.andReturn().getResponse().getHeader("Location");
	}
	
	
	@Test
	public void testDeleteProvider() throws Exception {
		mockMvc.perform(delete("/provider/2"))
			.andExpect(status().isOk());
		
		mockMvc.perform(get("/provider"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$..id", not(containsString("2"))));
	}
	
	
	@Test
	public void testDeleteProviderDoesntExist() throws Exception {
		mockMvc.perform(delete("/provider/100000"))
			.andExpect(status().isOk());
	}
}
