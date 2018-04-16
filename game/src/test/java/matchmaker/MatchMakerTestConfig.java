package matchmaker;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class MatchMakerTestConfig {

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        final String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        final String username = "fibersell";
        final String password = "201998";
        return new JdbcTemplate(DataSourceBuilder.create().url(jdbcUrl).username(username).password(password).build());
    }

}
