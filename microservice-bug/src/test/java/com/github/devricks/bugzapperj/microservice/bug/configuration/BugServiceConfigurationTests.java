package com.github.devricks.bugzapperj.microservice.bug.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BugServiceConfigurationTests {

    @Autowired
    private BugServiceConfiguration bugServiceConfiguration;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads_ByConfiguration_ShouldReturnApplicationContext() {
        assertNotNull(applicationContext, "ApplicationContext should be loaded in the context");
        assertNotNull(applicationContext.getBean(BugServiceConfiguration.class), "BugServiceConfiguration should be loaded in the context");
    }

    @Test
    void bugServiceConfiguration_ByConfiguration_ShouldReturnConfigurationBean() {
        BugServiceConfiguration bugServiceConfigurationForTesting = bugServiceConfiguration;
        assertNotNull(bugServiceConfiguration, "BugServiceConfiguration should be loaded in the context");
        assertEquals(bugServiceConfigurationForTesting, applicationContext.getBean(BugServiceConfiguration.class), "BugServiceConfiguration should be loaded in the context");
    }

    @Test
    void bugServiceConfiguration_ByConfiguration_ShouldReturnBugRepositoryBean() {
        assertNotNull(bugServiceConfiguration.bugRepository, "BugRepository should be loaded in the context");
        assertNotNull(applicationContext.getBean("bugRepository"), "BugRepository should be loaded in the context");
    }

}