package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.controller.AppController;
import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;
import com.qualityunit.ecobike.view.UserInput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.System.err;
import static java.lang.System.out;

public class WriteToFileCommand extends MenuCommand {
	private static final String PATH_PROMPT =
					"Enter the path to save the catalog file to:\n" +
					"(Can be absolute or relative to the app root (where pom.xml is located).\n" +
					"Should include the file name and extension)";
	private boolean isSaved = false;

	public WriteToFileCommand(String description) {
		super(description);
	}

	@Override
	public void execute() {
		if (!Storage.getInstance().isUpdated()) {
			out.println("No new data to save");
			return;
		}

		isSaved = false;
		while (!isSaved) {
			int option = Menu.getInstance().writeToFileOptions();
			String pathStr = null;
			switch (option) {
				case 1:
					pathStr = UserInput.getLine(PATH_PROMPT);
					break;
				case 2:
					pathStr = AppController.getInputFilePath().toString();
					break;
				case 3:
					return;
			}
			writeToFile(pathStr);
		}

		Storage.getInstance().setUpdated(false);
	}

	private void writeToFile(String pathStr) {
		File file = new File(pathStr);
		out.println("--> Writing to: " + file.getAbsolutePath());
		if (file.isFile()) {
			String prompt = String.format("Sure you want to overwrite the file '%s'?", file.getAbsolutePath());
			boolean confirmed = UserInput.getBoolean(prompt, false);
			if (!confirmed) return;
		}

		List<AbstractBike> catalog = Storage.getInstance().getCatalog();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for (AbstractBike b : catalog) {
				bw.write(b.toString());
			}
			isSaved = true;
			out.println("Catalog was saved to file");
		} catch (IOException | UnsupportedOperationException e) {
			err.println(String.format("Error on writing to file '%s'", file.getAbsolutePath()));
			err.println(String.format("Error message: '%s'", e.getMessage()));
		}
	}
}
