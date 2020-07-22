package com.lanchonete.infra.data;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class DataSourceConfiguration {
    private final String ambiente = System.getenv("ambiente");

    @Bean
    public DataSource dataSource() {
        return
        (Objects.nonNull(ambiente) && !ambiente.isEmpty())
        ? getDataSourceAmbiente()
        : getDataSourceImMemory();
    }

    private DataSource getDataSourceAmbiente() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.driverClassName("org.postgresql.Driver");
        builder.url(System.getenv("data_base"));
        builder.username(System.getenv("data_base_user"));
        builder.password("data_base_password");

        System.out.println("Postgresql On");
        return builder.build();
    }

    private DataSource getDataSourceImMemory() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.driverClassName("org.h2.Driver");
        builder.url(System.getenv("jdbc:h2:mem:"));
        builder.username(System.getenv("sa"));
        builder.password("");

        System.out.println("H2 On");
        return builder.build();
    }
}
