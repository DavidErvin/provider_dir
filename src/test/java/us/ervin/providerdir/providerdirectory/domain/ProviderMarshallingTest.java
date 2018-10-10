package us.ervin.providerdir.providerdirectory.domain;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProviderMarshallingTest {
	
	private ObjectMapper mapper;
	
	@Before
	public void setUp() {
		mapper = new ObjectMapper();
	}
	
	
	private String toJson(Provider p) {
		String json = null;
		try {
			json = mapper.writer().withFeatures(SerializationFeature.INDENT_OUTPUT).writeValueAsString(p);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Error marshalling to JSON: " + ex.getMessage());
		}
		return json;
	}
	
	
	private Provider fromJson(String json) {
		Provider p = null;
		try {
			p = mapper.reader().forType(Provider.class).readValue(json);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Error parsing JSON: " + ex.getMessage());
		}
		return p;
	}
	
	
	@Test
	public void testMarshallProvider() {
		Provider p = new Provider();
		p.setEmail("email");
		p.setFirstName("first");
		p.setLastName("last");
		p.setSpecialty("specialty");
		p.setPracticeName("practice");
		
		String json = toJson(p);
		System.out.println(json);
		
		assertThat(json, containsString("email_address"));
		assertThat(json, containsString("specialty"));
		assertThat(json, containsString("last_name"));
		assertThat(json, containsString("first_name"));
		assertThat(json, containsString("practice_name"));
		
		Provider back = fromJson(json);
		assertThat(back, is(equalTo(p)));
	}
}
