package com.jimwhere.api.config;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import java.nio.charset.StandardCharsets;

@Component
public class DevDataConditionalRunner implements CommandLineRunner {

    private final JdbcTemplate jdbc;
    public DevDataConditionalRunner(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) throws Exception {
        Integer cnt = jdbc.queryForObject("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'room'", Integer.class);
        if (cnt == null || cnt == 0) {
            String sql = StreamUtils.copyToString(new ClassPathResource("sql/dummy_data_v1.sql").getInputStream(), StandardCharsets.UTF_8);
            jdbc.execute(sql);
        }
    }
}