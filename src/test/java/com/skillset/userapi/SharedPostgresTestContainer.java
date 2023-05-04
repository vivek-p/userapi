package com.skillset.userapi;

import org.testcontainers.containers.PostgreSQLContainer;

public class SharedPostgresTestContainer extends PostgreSQLContainer<SharedPostgresTestContainer> {

    private static final String DB_NAME = "postgres";
    private SharedPostgresTestContainer() {}

    public static SharedPostgresTestContainer getInstance() {
        return SharedPostgresTestContainerInstance.SHARED_POSTGRES_TEST_CONTAINER;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {}

    private static class SharedPostgresTestContainerInstance {
        public static final SharedPostgresTestContainer SHARED_POSTGRES_TEST_CONTAINER =
                new SharedPostgresTestContainer();
    }

}
