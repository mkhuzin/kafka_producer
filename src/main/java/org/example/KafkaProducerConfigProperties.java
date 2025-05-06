package org.example;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app.kafka.producer")
public class KafkaProducerConfigProperties {

	private String serverHost;

	private String serverPort;

	private Class<?> valueSerializer;

	private boolean enableSsl;

	private String truststoreType;

	private String truststoreLocation;

	private String truststorePassword;

	private String keystoreType;

	private String keystoreLocation;

	private String keystorePassword;

	private String topic;

	private String bootstrap;

}
