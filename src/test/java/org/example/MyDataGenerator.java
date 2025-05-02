package org.example;

import java.time.LocalDate;

public class MyDataGenerator {

	public static MyData getMyData() {
		return new MyData(
				1,
				"str1",
				LocalDate.now()
		);
	}

}
