package us.ervin.providerdir.providerdirectory.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import us.ervin.providerdir.providerdirectory.domain.Provider;
import us.ervin.providerdir.providerdirectory.repository.ProviderRepository;

@RestController
@Slf4j
public class ProviderRestController {

	@Autowired
	private ProviderRepository providerRepo;
	
	@GetMapping("/provider")
	public List<Provider> getProviders() {
		log.debug("Getting all providers");
		List<Provider> providers = new ArrayList<>();
		providerRepo.findAll().forEach(p -> providers.add(p));
		return providers;
	}
	
	
	@PostMapping("/provider")
	public ResponseEntity<Provider> addProvider(Provider provider) {
		log.debug("Creating a new provider");
		provider = providerRepo.save(provider);
		URI providerLocation = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(provider.getId()).toUri();
		log.debug("\tnew provider has id " + provider.getId());
		return ResponseEntity.created(providerLocation).build();
	}
	
	
	@GetMapping("/provider/{id}")
	public Provider getSingleProvider(@PathVariable("id") Integer id) {
		log.debug("Getting provider " + id);
		Optional<Provider> provider = providerRepo.findById(id);
		if (provider.isPresent()) {
			return provider.get();
		}
		String message = "No provider with id " + id + " found";
		log.debug(message);
		throw new ProviderNotFoundException(message);
	}
	
	
	@DeleteMapping("/provider/{id}")
	public void removeSingleProvider(@PathVariable("id") Integer id) {
		log.debug("Removing provider " + id);
		if (providerRepo.existsById(id)) {
			providerRepo.deleteById(id);
		}
	}
}