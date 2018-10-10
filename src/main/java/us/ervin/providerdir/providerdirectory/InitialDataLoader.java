package us.ervin.providerdir.providerdirectory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.ervin.providerdir.providerdirectory.domain.Provider;
import us.ervin.providerdir.providerdirectory.repository.ProviderRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class InitialDataLoader {
	
	private static final String INITIAL_DATA_FILE = "/data/initial.json";

	@Autowired
	private ProviderRepository providerRepo;
	
	public void loadData() {
		log.debug("Loading initial data");
		ObjectMapper jsonMapper = new ObjectMapper();
		List<Provider> initialProviders = new ArrayList<>();
		try (InputStream dataIn = getClass().getResourceAsStream(INITIAL_DATA_FILE)) {
			Iterator<Provider> providers = jsonMapper.reader()
					.forType(Provider.class).readValues(dataIn);
			providers.forEachRemaining(p -> initialProviders.add(p));
		} catch (Exception ex) {
			log.error("Unable to read initial data: " + ex.getMessage(), ex);
		}
		
		log.debug("Persisting initial providers");
		providerRepo.saveAll(initialProviders);
		providerRepo.flush();
	}
}
