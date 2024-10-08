package com.github.devricks.bugzapperj.microservice.bug.configuration;

import com.github.devricks.bugzapperj.microservice.bug.repository.BugRepository;
import com.github.devricks.bugzapperj.microservice.bug.service.BugService;
import com.github.devricks.bugzapperj.microservice.bug.service.BugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.github.devricks.bugzapperj.microservice.bug.repository")
@ConfigurationProperties(prefix = "spring")
@PropertySource("classpath:application.properties")
public class BugServiceConfiguration {

    BugRepository bugRepository;

    @Autowired
    public BugServiceConfiguration(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    @Bean(name = "bugService")
    public BugService bugService() {
        return new BugServiceImpl(bugRepository);
    }
}
