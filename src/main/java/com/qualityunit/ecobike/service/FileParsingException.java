package com.qualityunit.ecobike.service;

public class FileParsingException extends RuntimeException {
	private static final String MSG = "Error during input file parsing (line %d). This line was ignored.";

	public FileParsingException(long lineCount) {
		super(String.format(MSG, lineCount));
	}
}
