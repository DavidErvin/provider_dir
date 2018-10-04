package us.ervin.providerdir.providerdirectory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import us.ervin.providerdir.providerdirectory.domain.Provider;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Integer> {

}
