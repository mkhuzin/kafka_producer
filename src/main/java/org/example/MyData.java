package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record MyData(
		int intField,
		String strField,
		LocalDate dateField
) {

	@JsonCreator
	public MyData(
			@JsonProperty("intField") int intField,
			@JsonProperty("strField") String strField,
			@JsonProperty("dateField") LocalDate dateField
	) {
		this.intField = intField;
		this.strField = strField;
		this.dateField = dateField;
	}

}
