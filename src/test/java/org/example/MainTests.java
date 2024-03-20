package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class MainTests {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Test
	void sendMessageTest() {
		kafkaTemplate.send("topic1", "message1");
	}

}