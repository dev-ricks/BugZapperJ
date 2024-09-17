//package com.github.devricks.bugzapperj.microservice.bug.configuration;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@EnableConfigurationProperties(value = {BugServiceH2DBConfiguration.class, BugServiceConfiguration.class})
//@ContextConfiguration(classes = {TestConfigurationFactory.class})
//@TestPropertySource("classpath:application.test.properties")
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureTestEntityManager
//class BugServiceConfigurationTest {
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    @Qualifier("configurationToTest")
//    private BugServiceConfiguration bugServiceConfiguration;
//
//    @Test
//    void bugService_None_ShouldHaveBugServiceContext() {
//
//
//
//    }
//}

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

}