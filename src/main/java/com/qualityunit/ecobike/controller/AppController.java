package com.qualityunit.ecobike.controller;

import com.qualityunit.ecobike.service.FileParser;
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
	private final Menu menu = new Menu();
	private final FileParser fileParser = new FileParser();
	private Path inputFilePath;

	public void processInputFile(String[] args) {
		String pathStr = (args.length > 0) ? args[0] : UserInput.getLine(Menu.FILE_PATH_PROMPT);
		Stream<String> stream = null;
		do {
			try {
				inputFilePath = Paths.get(pathStr).toRealPath();
				stream = Files.lines(inputFilePath);
			} catch (InvalidPathException | IOException | SecurityException e) {
				err.println(format(Menu.OPEN_ERROR_MSG, pathStr));
				pathStr = UserInput.getLine(Menu.FILE_PATH_PROMPT);
			}
		} while (stream == null);

		fileParser.parseFileLinesStream(stream);

		stream.close();


	}

}
