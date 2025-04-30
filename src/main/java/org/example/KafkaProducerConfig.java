package org.example;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

	private final KafkaProducerConfigProperties configProperties;

	@Bean
	public KafkaTemplate<String, MyData> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ProducerFactory<String, MyData> producerFactory() {
		return new DefaultKafkaProducerFactory<>(configMap());
	}

	private Map<String, Object> configMap() {

		Map<String, Object> configMap = new HashMap<>();

		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configProperties.getServerHost() + ":" + configProperties.getServerPort());
		configMap.put(ProducerConfig.ACKS_CONFIG, "all");
		configMap.put(ProducerConfig.RETRIES_CONFIG, 0);
		configMap.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		configMap.put(ProducerConfig.LINGER_MS_CONFIG, 10000);
		configMap.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MyDataSerializer.class);

		if (configProperties.isEnableSsl())
			configMap.putAll(sslConfigMap());

		return configMap;

	}

	private Map<String, Object> sslConfigMap() {

		Map<String, Object> configMap = new HashMap<>();

		configMap.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SSL.name());
		configMap.put(SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG, SslConfigs.DEFAULT_SSL_ENABLED_PROTOCOLS);
		configMap.put(SslConfigs.SSL_PROTOCOL_CONFIG, SslConfigs.DEFAULT_SSL_PROTOCOL);
		configMap.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
		configMap.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, configProperties.getTruststoreType());
		configMap.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, configProperties.getTruststoreLocation());
		configMap.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, configProperties.getTruststorePassword());
		configMap.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, configProperties.getKeystoreType());
		configMap.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, configProperties.getKeystoreLocation());
		configMap.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, configProperties.getKeystorePassword());

		return configMap;

	}

}
