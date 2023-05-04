package com.skillset.userapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@ContextConfiguration(initializers = UserapiApplicationTests.DataSourceInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserapiApplicationTests {

	@Container
	private static PostgreSQLContainer<SharedPostgresTestContainer> database =
			SharedPostgresTestContainer.getInstance();

	public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					applicationContext,
					"spring.test.database.replace=none",
					"spring.datasource.url=" + database.getJdbcUrl(),
					"spring.datasource.username=" + database.getUsername(),
					"spring.datasource.password=" + database.getPassword()
			);
		}
	}

}
