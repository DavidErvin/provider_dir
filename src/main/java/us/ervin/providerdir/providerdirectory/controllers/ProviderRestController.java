package us.ervin.providerdir.providerdirectory.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.ervin.providerdir.providerdirectory.domain.Provider;
import us.ervin.providerdir.providerdirectory.repository.ProviderRepository;

@RestController
@RequestMapping("/provider")
@Slf4j
public class ProviderRestController {

	@Autowired
	private ProviderRepository providerRepo;
	
	@GetMapping(produces = "application/json")
	public List<Provider> getProviders() {
		log.debug("Getting all providers");
		List<Provider> providers = new ArrayList<>();
		providerRepo.findAll().forEach(p -> providers.add(p));
		return providers;
	}
	
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Provider> addProvider(@RequestBody String json) {
		log.debug("Creating a new provider");
		log.debug("Provider content: " + json);
		Provider provider;
		try {
			provider = new ObjectMapper().reader().forType(Provider.class).readValue(json);
			provider = providerRepo.saveAndFlush(provider);
			URI providerLocation = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(provider.getId()).toUri();
			log.debug("\tnew provider has id " + provider.getId());
			return ResponseEntity.created(providerLocation).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	@GetMapping(path = "/{id}", consumes = "application/json")
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
	
	
	@DeleteMapping(path = "/{id}")
	public void removeSingleProvider(@PathVariable("id") Integer id) {
		log.debug("Removing provider " + id);
		if (providerRepo.existsById(id)) {
			providerRepo.deleteById(id);
		}
	}
}
