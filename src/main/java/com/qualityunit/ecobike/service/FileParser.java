package com.qualityunit.ecobike.service;

import java.util.stream.Stream;

public class FileParser {


	public void parseFileLinesStream(Stream<String> stream) {
		stream.limit(5).forEach(s -> {
			String[] arr = s.split(";");

		});

	}

}
