package com.qualityunit.ecobike.view;

import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

/*
* Scanner util methods that loop forcing user to provide valid input.
* If flag isOptional is set 'true' empty line is a valid input. This is used
* to build the search query with omitted parameters. Undefined string is empty,
* undefined int param is 0, and if a boolean is skipped the corresponding method returns null.
*/
public class UserInput {
	private final Scanner scanner = new Scanner(in);
	public static final String INVALID_INPUT = "Invalid input";

	public void closeScanner() {
		scanner.close();
	}

	public String getLine(String prompt) {
		String ln;
		do {
			if (prompt != null && !prompt.trim().isEmpty()) {
				out.println(prompt);
			}
			ln = scanner.nextLine().trim();
		} while (ln.length() < 1);
		return ln;
	}

	public String getLineAllowEmpty(String prompt) {
		if (prompt != null && !prompt.trim().isEmpty()) {
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
