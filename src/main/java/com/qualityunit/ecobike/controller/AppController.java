package com.qualityunit.ecobike.controller;

import com.qualityunit.ecobike.view.Menu;
import com.qualityunit.ecobike.view.UserInput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.System.err;

public class AppController {
	private final UserInput userInput = new UserInput();
	private Path inputFilePath;
	private static final String FILE_PATH_PROMPT = "Please enter the input file path:";
	private static final String OPEN_ERROR_MSG = "Can't open a file at this path: '%s'";

	public Stream<String> getStreamFromFilePath(String[] args) {
		String pathStr = (args.length > 0) ? args[0] : userInput.getLine(FILE_PATH_PROMPT);
		Stream<String> stream = null;
		do {
			try {
				inputFilePath = Paths.get(pathStr).toRealPath();
				stream = Files.lines(inputFilePath);
			} catch (InvalidPathException | IOException | SecurityException e) {
				err.println(format(OPEN_ERROR_MSG, pathStr));
				pathStr = userInput.getLine(FILE_PATH_PROMPT);
			}
		} while (stream == null);
		return stream;
	}

	public void runMenu() {
		Menu menu = new Menu(inputFilePath, userInput);
		while (true) {
			menu.getCommandFromUser().execute();
		}
	}
}
