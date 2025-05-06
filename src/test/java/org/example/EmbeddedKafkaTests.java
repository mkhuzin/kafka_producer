package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EmbeddedKafka(
		partitions = 1,
		topics = "topic1",
		controlledShutdown = true
)
@TestPropertySource(properties = {
		"app.kafka.producer.bootstrap=${spring.embedded.kafka.brokers}",
		"app.kafka.producer.topic=topic1"
})
@SpringBootTest
class EmbeddedKafkaTests {

	@Autowired
	private ProducerFactory<String, MyData> producerFactory;

	@Autowired
	EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	MyDataProducer myDataProducer;

	@Value("${app.kafka.producer.topic}")
	String topic;

	static Consumer<String, MyData> consumer;

	@BeforeAll
	void setup() {

		log.info("kafka producer configuration properties = {}", producerFactory.getConfigurationProperties());

		log.info("embeddedKafka brokers = {}", embeddedKafkaBroker.getBrokersAsString());

		Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
				"consumerGroup1",
				"true",
				embeddedKafkaBroker
		);

		consumer = new KafkaConsumer<>(
				consumerProperties,
				new StringDeserializer(),
				new MyDataDeserializer()
		);

		embeddedKafkaBroker.consumeFromAnEmbeddedTopic(
				consumer,
				topic
		);

	}

	@AfterAll
	void tearDown() {
		consumer.close();
	}

	@Test
	void testKafkaSendAndReceive() {

		log.info("info = {}", myDataProducer.getInfo());

		log.info("embeddedKafka broker = {}", embeddedKafkaBroker.getBrokersAsString());

		MyData sentData = new MyData(
				1,
				"str1",
				LocalDate.now()
		);

		var result = myDataProducer.send1(sentData).join();
		log.info("record metadata {}", result.getProducerRecord().topic());
		ConsumerRecord<String, MyData> consumerRecord = KafkaTestUtils.getSingleRecord(
				consumer,
				topic,
				Duration.ofSeconds(10)
		);

		MyData receivedData = consumerRecord.value();

		assertEquals(sentData, receivedData);

	}

}