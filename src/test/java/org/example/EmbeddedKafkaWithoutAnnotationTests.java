package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class EmbeddedKafkaWithoutAnnotationTests {

	private static EmbeddedKafkaBroker embeddedKafkaBroker = new EmbeddedKafkaZKBroker(1,
			true,
			"topic1")
			.kafkaPorts(9094);

	static {

		embeddedKafkaBroker.brokerListProperty("");

		Map<String, String> embeddedKafkaBrokerProperties = new HashMap<>();

		embeddedKafkaBrokerProperties.put("listeners", "PLAINTEXT://localhost:9094,REMOTE://10.0.0.20:9093");
		embeddedKafkaBrokerProperties.put("advertised.listeners", "PLAINTEXT://localhost:9094,REMOTE://10.0.0.20:9093");
		embeddedKafkaBrokerProperties.put("listener.security.protocol.map", "PLAINTEXT:PLAINTEXT,REMOTE:PLAINTEXT");

		embeddedKafkaBroker.brokerProperties(embeddedKafkaBrokerProperties);

		log.info("embeddedKafka brokers = {}", embeddedKafkaBroker.getBrokersAsString());

	}

	private static Consumer<String, MyData> consumer;

	@Value("${app.my-data-producer-topic}")
	private String topic;

	//@Autowired
	//private MyDataProducer myDataProducer;

	@Autowired
	private ProducerFactory<String, MyData> producerFactory;

	@BeforeAll
	void setup() {

		log.info("kafka producer configuration properties = {}", producerFactory.getConfigurationProperties());

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

	/*@AfterAll
	void tearDown() {
		consumer.close();
	}*/

	@Test
	void testKafkaSendAndReceive() {

		//log.info("info = {}", myDataProducer.getInfo());

		/*MyData sentData = MyDataGenerator.getMyData();

		myDataProducer.send1(sentData);

		ConsumerRecord<String, MyData> consumerRecord = KafkaTestUtils.getSingleRecord(
				consumer,
				topic,
				Duration.ofSeconds(10)
		);

		MyData receivedData = consumerRecord.value();

		assertEquals(sentData, receivedData);*/

	}

}
