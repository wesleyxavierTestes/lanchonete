package com.lanchonete.infra.data;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class DataSourceConfiguration {
    private String ambiente = System.getenv("ambiente");
    /**
     * Configura data base port ambiente
     * Ambiente Vazio é de Test usando o h2
     * Com ambiente dev ou prod usando o Postgresql
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        return (Objects.nonNull(ambiente) && !ambiente.isEmpty())
                ? getDataSourceAmbiente()
                : getDataSourceImMemory();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        ambiente = System.getenv("ambiente");
        System.out.println("AMBIENTE ATUAL"+ambiente);
        final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        if (Objects.nonNull(ambiente) && !ambiente.isEmpty()) {
            adapter.setDatabase(Database.POSTGRESQL);
            adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        } else {
            adapter.setDatabase(Database.H2);
            adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
        }
        adapter.setGenerateDdl(true);
        adapter.setPrepareConnection(false);
        adapter.setShowSql(false);
        return adapter;
    }

    private DataSource getDataSourceAmbiente() {
        final DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName("org.postgresql.Driver");
        builder.url(System.getenv("data_base_url"));
        builder.username(System.getenv("data_base_user"));
        builder.password(System.getenv("data_base_password"));

        System.out.println("Postgresql On");
        return builder.build();
    }

    private DataSource getDataSourceImMemory() {
        final DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName("org.h2.Driver");
        builder.url("jdbc:h2:mem:db");
        builder.username("sa");
        builder.password("sa");
        System.out.println("H2 On");
        return builder.build();
    }
}
