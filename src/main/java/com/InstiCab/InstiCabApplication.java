package com.InstiCab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class InstiCabApplication implements CommandLineRunner {
	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(InstiCabApplication.class, args);
	}

	@Override
	public void run(String... args) {
		loadSchemaIfRequired();
	}

	void loadSchemaIfRequired() {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("schema.sql"));
        resourceDatabasePopulator.execute(dataSource);
	}
}
