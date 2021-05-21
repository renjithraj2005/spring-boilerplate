package com.boilerplate.demo;

import com.boilerplate.demo.config.ConfigProperties;
import com.boilerplate.demo.helper.SeedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
public class ApplicationRunner {
	
	@Autowired
	private ApplicationContext context;

	@Autowired
	private SeedDataService seedService;
  
	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);    
	}
 
	@EventListener
	public void seed(ContextRefreshedEvent event) {
		ConfigProperties config = context.getBean("appConfigProperties", ConfigProperties.class);
		seedService.insertRoles();
		seedService.createOAuth2Client(config.getDefaultOAuth2Client(), config.getDefaultOAuth2ClientSecret());
		seedService.createUser("ROLE_ADMIN");
	}
}
