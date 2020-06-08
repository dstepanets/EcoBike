package com.qualityunit.ecobike.view;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class UserInput {
	private static final Scanner scan = new Scanner(in);

	private UserInput() {}

	public static String getLine(String prompt) {
		String ln;
		out.println(prompt);
		do {
			ln = scan.nextLine();
		} while (ln.trim().length() < 1);
		return ln;
	}

	public static int getInt(String prompt) {
		out.println(prompt);
		while (!scan.hasNextInt()){
			out.print(":> ");
			scan.nextLine();
		}
		return scan.nextInt();
	}

	public static double getDouble(String prompt) {
		out.println(prompt);
		while (!scan.hasNextDouble()){
			out.print(":> ");
			scan.nextLine();
		}
		return scan.nextDouble();
	}
}
