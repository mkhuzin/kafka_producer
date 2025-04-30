package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MyDataProducer {

	private final KafkaTemplate<String, MyData> kafkaTemplate;

	@Value("${app.kafka.producer.topic}")
	private String topic;

	public CompletableFuture<SendResult<String, MyData>> send1(MyData myData) {
		return kafkaTemplate.send(
				topic,
				myData
		);
	}

	public CompletableFuture<SendResult<String, MyData>> send2(MyData myData) {

		Message<MyData> myDataMessage = MessageBuilder
				.withPayload(myData)
				.setHeader(KafkaHeaders.TOPIC, topic)
				.build();

		return kafkaTemplate.send(myDataMessage);

	}

}
