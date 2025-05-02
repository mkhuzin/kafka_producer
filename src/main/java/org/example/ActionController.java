package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ActionController {

	private final MyDataProducer myDataProducer;

	@GetMapping("/send")
	public ResponseEntity<String> send() {

		log.info("call send");

		MyData myData = new MyData(
				1,
				"str1",
				LocalDate.now()
		);

		try {

			myDataProducer.send1(myData);

			return ResponseEntity.ok("OK");

		} catch (Exception exception) {

			log.error(exception.getMessage(), exception);

			return ResponseEntity
					.internalServerError()
					.body(ExceptionUtils.getStackTrace(exception));

		}

	}

}
