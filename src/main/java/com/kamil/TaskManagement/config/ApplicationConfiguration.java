package com.kamil.TaskManagement.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.kamil.TaskManagement.controller","com.kamil.TaskManagement.repository","com.kamil.TaskManagement.service"})
public class ApplicationConfiguration {


}
