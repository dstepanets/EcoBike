package com.qualityunit.ecobike.view;

import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

public class UserInput {
	private static final Scanner SCAN = new Scanner(in);
	private static final String INVALID_INPUT = "Invalid input";

	private UserInput() {}

	public static void closeScanner() {
		SCAN.close();
	}

	public static String getLine(String prompt) {
		String ln;
		do {
			if (prompt != null) {
				out.println(prompt);
			}
			ln = SCAN.nextLine().trim();
		} while (ln.length() < 1);
		return ln;
	}

	public static String getLineAllowEmpty(String prompt) {
		if (prompt != null) {
			out.println(prompt + " (or leave blank)");
		}
		return SCAN.nextLine().trim();
	}

	public static int getInt(String prompt, boolean isOptional) {
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

	public static int getIntInRange(String prompt, int min, int max, boolean isOptional) {
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

	public static Boolean getBoolean(String prompt, boolean isOptional) {
		if (isOptional && getLineAllowEmpty(prompt).isEmpty()) {
			return null;
		}
		String ln;
		do {
			ln = getLine(prompt + " (y/n):");
			if (ln.toLowerCase().charAt(0) == 'y') {
				return true;
			} else if (ln.toLowerCase().charAt(0) == 'n') {
				return false;
			} else {
				err.println(INVALID_INPUT);
			}
		} while (true);
	}

}
