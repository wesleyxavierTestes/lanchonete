package com.lanchonete;

import com.lanchonete.apllication.validations.Validations;
import com.lanchonete.utils.HttpBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan({ "com.lanchonete.apllication.configurations", "com.lanchonete.infra.data", "com.lanchonete.controllers", "com.lanchonete.domain.services" })
@EntityScan("com.lanchonete.domain.entities")
@EnableJpaRepositories("com.lanchonete.infra.repositorys")
@SpringBootApplication
public class LanchoneteApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanchoneteApplication.class, args);
	}

	@Bean
	public Validations validations() {
		return new Validations();
	}

	@Bean
	public HttpBase httpBase() {
		return new HttpBase();
	}
}
