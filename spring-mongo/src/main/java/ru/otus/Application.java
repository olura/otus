package ru.otus;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories (basePackages = "ru.otus.dao")
@EnableMongock
public class Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);

	}
}

