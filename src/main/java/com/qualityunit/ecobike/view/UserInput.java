package com.qualityunit.ecobike.view;

import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

public class UserInput {
	private static final Scanner SCAN = new Scanner(in);

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
				err.println("Invalid input");
			}
		} while (num == null);
		return num;
	}

}
