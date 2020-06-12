package com.qualityunit.ecobike.view;

import java.util.Scanner;

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
			ln = SCAN.nextLine();
		} while (ln.trim().length() < 1);
		return ln;
	}

//	TODO fix this)
	public static int getInt(String prompt) {
		do {
			if (prompt != null) {
				out.println(prompt);
			}
			SCAN.nextLine();
		} while (!SCAN.hasNextInt());
		return SCAN.nextInt();
	}

}
