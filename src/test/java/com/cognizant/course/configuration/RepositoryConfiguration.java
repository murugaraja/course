package com.cognizant.course.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.cognizant.course.domain"})
@EnableJpaRepositories(basePackages = {"com.cognizant.course.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
