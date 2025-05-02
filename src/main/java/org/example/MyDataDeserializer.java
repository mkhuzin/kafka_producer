package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@Slf4j
public class MyDataDeserializer implements Deserializer<MyData> {

	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule());

	@Override
	public MyData deserialize(
			String s,
			byte[] bytes
	) {

		if (bytes == null)
			return null;

		if (bytes.length == 0)
			return null;

		try {

			return objectMapper.readValue(
					bytes,
					MyData.class
			);

		} catch (IOException exception) {

			log.error(
					exception.getMessage(),
					exception
			);

			throw new RuntimeException(exception);

		}

	}

}
