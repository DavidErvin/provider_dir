package us.ervin.providerdir.providerdirectory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("us.ervin.providerdir.providerdirectory")
public class ProviderDirectoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderDirectoryApplication.class, args);
	}
	
	
	// CommandLineRunner gets invoked as soon as the app starts
	@Bean
	public CommandLineRunner loadIntialData(InitialDataLoader loader) {
		return args -> loader.loadData();
	}
}
