package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static java.lang.System.err;
import static java.lang.System.out;

/*
 * Gives an option to overwrite the file used for data input or to specify the path to a new file.
 * Loops until data is either written successfully or cancel command is received.
 */
public class WriteToFileCommand extends MenuCommand {
	private final Storage storage;
	private static final String PATH_PROMPT =
			"Enter the path to save the catalog file to:\n" +
			"(Can be absolute or relative to the app root (where pom.xml is located).\n" +
			"Should include the file name and extension)";
	private boolean isSaved = false;
	private final Path inputFilePath;

	public WriteToFileCommand(String description, Path inputFilePath, Menu menu, Storage storage) {
		super(description, menu);
		this.inputFilePath = inputFilePath;
		this.storage = storage;
	}

	@Override
	public void execute() {
		if (!storage.isUpdated()) {
			out.println("No new data to save");
			return;
		}

		isSaved = false;
		while (!isSaved) {
			int option = getMenu().writeToFileOptions();
			String pathStr = null;
			switch (option) {
				case 1:
					pathStr = getMenu().getUserInput().getLine(PATH_PROMPT);
					break;
				case 2:
					pathStr = inputFilePath.toString();
					break;
				case 3:
					return;
				default:
					err.println("Invalid option");
					break;
			}
			writeToFile(pathStr);
		}

		storage.setUpdated(false);
	}

	private void writeToFile(String pathStr) {
		File file = new File(pathStr);
		out.println("--> Writing to: " + file.getAbsolutePath());
		if (file.isFile()) {
			String prompt = String.format("Sure you want to overwrite the file '%s'?", file.getAbsolutePath());
			boolean confirmed = getMenu().getUserInput().getBoolean(prompt, false);
			if (!confirmed) return;
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for (AbstractBike b : storage.getCatalog()) {
				bw.append(b.toString());
				bw.newLine();
			}
			isSaved = true;
			out.println("Catalog was saved to file");
		} catch (IOException | UnsupportedOperationException e) {
			err.println(String.format("Error on writing to file '%s'", file.getAbsolutePath()));
			err.println(String.format("Error message: '%s'", e.getMessage()));
		}
	}
}
