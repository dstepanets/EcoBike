package com.qualityunit.ecobike.view;

import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

public class UserInput {
	private final Scanner scanner = new Scanner(in);
	private static final String INVALID_INPUT = "Invalid input";
	//		InputStream in = new ByteArrayInputStream(inputFile.getPath().getBytes());
	//		System.setIn(in);
	public void closeScanner() {
		scanner.close();
	}

	public String getLine(String prompt) {
		String ln;
		do {
			if (prompt != null) {
				out.println(prompt);
			}
			ln = scanner.nextLine().trim();
		} while (ln.length() < 1);
		return ln;
	}

	public String getLineAllowEmpty(String prompt) {
		if (prompt != null) {
			out.println(prompt + " (or leave blank)");
		}
		return scanner.nextLine().trim();
	}

	public int getInt(String prompt, boolean isOptional) {
		Integer num = null;
		do {
			try {
				String ln = (isOptional) ? getLineAllowEmpty(prompt) : getLine(prompt);
				if (isOptional && ln.isEmpty()) {
					return 0;
				}
				num = Integer.parseInt(ln);
			} catch (NumberFormatException e) {
				err.println(INVALID_INPUT);
			}
		} while (num == null);
		return num;
	}

	public int getIntInRange(String prompt, int min, int max, boolean isOptional) {
		int num;
		do {
			num = getInt(prompt, isOptional);
			if (isOptional && num == 0) {
				return 0;
			}
			if (num < min || num > max) {
				err.println("Invalid number");
			}
		} while (num < min || num > max);
		return num;
	}

	public Boolean getBoolean(String prompt, boolean isOptional) {
		do {
			String ln = isOptional ? getLineAllowEmpty(prompt + " (y/n):") : getLine(prompt + " (y/n):");
			if (isOptional && ln.isEmpty()) {
				return null;
			} else if (ln.toLowerCase().charAt(0) == 'y') {
				return true;
			} else if (ln.toLowerCase().charAt(0) == 'n') {
				return false;
			} else {
				err.println(INVALID_INPUT);
			}
		} while (true);
	}

}
