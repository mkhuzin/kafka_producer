package org.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Slf4j
@SpringBootTest
class MainTests {

	@Autowired
	MyDataProducer myDataProducer;

	static MyData myData = new MyData(
			1,
			"str1",
			LocalDate.now()
	);

	@Test
	void send1Test() {

		CompletableFuture<SendResult<String, MyData>> sendResult = myDataProducer.send1(myData);

		log.info("send1 result = {}", sendResult);

	}

	@Test
	void send2Test() {

		CompletableFuture<SendResult<String, MyData>> sendResult = myDataProducer.send2(myData);

		log.info("send2 result = {}", sendResult);

	}

}