package us.ervin.providerdir.providerdirectory.repository;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import us.ervin.providerdir.providerdirectory.domain.Provider;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderRepositoryTest {

	@Autowired
	private ProviderRepository providerRepo;
	
	@Test
	public void testInitialProvidersLoaded() {
		Iterable<Provider> all = providerRepo.findAll();
		assertThat(all, is(notNullValue()));
		List<Provider> materialized = new ArrayList<>();
		all.forEach(p -> materialized.add(p));
		assertThat(materialized.size(), is(6));
	}
}
