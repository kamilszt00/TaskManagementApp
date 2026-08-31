package com.kamil.TaskManagement.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.kamil.TaskManagement.controller","com.kamil.TaskManagement.repository","com.kamil.TaskManagement.service"})
public class ApplicationConfiguration {


}
