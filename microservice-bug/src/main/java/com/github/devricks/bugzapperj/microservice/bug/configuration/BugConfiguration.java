package com.github.devricks.bugzapperj.microservice.bug.configuration;

import com.github.devricks.bugzapperj.microservice.bug.service.BugServiceImpl;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BugConfiguration {
    @Bean
    public BugServiceImpl bugServiceBean() {
        return new BugServiceImpl();
    }

    @Bean
    public ModelMapper modelMapperBean() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }
}
