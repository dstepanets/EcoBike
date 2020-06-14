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

	public static int getInt(String prompt) {
		Integer num = null;
		do {
			if (prompt != null) {
				out.println(prompt);
			}
			try {
				num = Integer.parseInt(SCAN.nextLine());
			} catch (NumberFormatException e) {
				err.println(INVALID_INPUT);
			}
		} while (num == null);
		return num;
	}

	public static int getNonNegativeInt(String prompt) {
		int num;
		do {
			num = getInt(prompt);
			if (num < 0) {
				err.println("Number can't be negative");
			}
		} while (num < 0);
		return num;
	}

	public static boolean getBoolean(String prompt) {
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
