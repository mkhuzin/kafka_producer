package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Objects;

@Slf4j
public class MyDataSerializer implements Serializer<MyData> {

	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule());

	@Override
	public byte[] serialize(String s, MyData data) {

		if (Objects.isNull(data)) {

			log.error("Пустой объект MyData передан в DataSerializer");

			return null;

		}

		try {

			return objectMapper.writeValueAsBytes(data);

		} catch (JsonProcessingException exception) {

			log.error(
					exception.getMessage(),
					exception
			);

			throw new SerializationException(exception);

		}

	}

	@Override
	public byte[] serialize(String topic, Headers headers, MyData data) {
		return Serializer.super.serialize(topic, headers, data);
	}

}
