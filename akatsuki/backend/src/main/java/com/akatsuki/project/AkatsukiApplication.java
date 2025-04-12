package com.akatsuki.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.akatsuki.project.repository")
@ComponentScan(basePackages = "com.akatsuki.project")
public class AkatsukiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkatsukiApplication.class, args);
	}

}
