package com.xevgnov.UserManagementApplication.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {
  @Bean
  public ModelMapper getModelMapper() {
    return new ModelMapper();
  }
}
