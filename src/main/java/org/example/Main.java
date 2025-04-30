package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
@EnableConfigurationProperties
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
