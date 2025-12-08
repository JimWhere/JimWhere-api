package com.jimwhere.api.global.config.sql;

import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
public class DevDataSqlRunner implements CommandLineRunner {
    private final DataSource dataSource;

    public DevDataSqlRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        var resource = new ClassPathResource("sql/dummy_data_v1.sql");
        var populator = new ResourceDatabasePopulator(resource);
        // Fail fast during development so SQL errors are visible in logs
        populator.setContinueOnError(false);
        populator.execute(dataSource);
    }
}
