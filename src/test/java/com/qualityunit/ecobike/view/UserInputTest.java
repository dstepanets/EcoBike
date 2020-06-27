package com.qualityunit.ecobike.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class UserInputTest {
	private static final String PROMPT = "Give me a line!";
	private static final String OR_BLANK = " (or leave blank)";
	private static final String NEWLINE = System.lineSeparator();
	private static final String BLANK_LINES = " \t " + NEWLINE + " \t " + NEWLINE + "    \t";

	private UserInput userInput;

	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private final ByteArrayOutputStream testErr = new ByteArrayOutputStream();
	private final InputStream backupIn = System.in;
	private final PrintStream backupOut = System.out;
	private final PrintStream backupErr = System.err;

	@Before
	public void setUp() {
		System.setOut(new PrintStream(testOut));
		System.setErr(new PrintStream(testErr));
	}

	@After
	public void tearDown() {
		System.setIn(backupIn);
		System.setOut(new PrintStream(backupOut));
		System.setErr(new PrintStream(backupErr));
	}

	@Test
	public void getLine_ValidInput_ReturnsString() {
		String input = "Here you have!";

		mockStdInput(input);
		String result = userInput.getLine(PROMPT);

		assertThat(result, equalTo(input));
		assertThat(testOut.toString(), equalTo(PROMPT + NEWLINE));
	}

	@Test
	public void getLine_BlankLinesOnly_ThrowsException() {
		mockStdInput(BLANK_LINES);
		Exception exception = assertThrows(NoSuchElementException.class, () -> {
			String result = userInput.getLine(PROMPT);
		});

		assertThat(exception.getMessage(), containsString("No line found"));
		assertThat(testOut.toString(), containsString(PROMPT + NEWLINE));
		//	loop breaks on the 4th iteration when the stream is empty
		assertThat(testOut.toString().length(), equalTo((PROMPT + NEWLINE).length() * 4));
	}

	@Test
	public void getLine_BlankLinesThenValid_ReturnsString() {
		String expected = "Valid string!";
		String input = " \t " + NEWLINE + " \t " + NEWLINE + expected + NEWLINE;

		mockStdInput(input);
		String result = userInput.getLine(PROMPT);

		assertThat(result, equalTo(expected));
		assertThat(testOut.toString(), containsString(PROMPT + NEWLINE));
		assertThat(testOut.toString().length(), equalTo((PROMPT + NEWLINE).length() * 3));
	}

	@Test
	public void getLineAllowEmpty_ValidInput_ReturnsString() {
		String input = "Here you have!";

		mockStdInput(input);

		String result = userInput.getLineAllowEmpty(PROMPT);
		assertThat(result, equalTo(input));
		assertThat(testOut.toString(), equalTo(PROMPT + OR_BLANK + NEWLINE));
	}

	@Test
	public void getLineAllowEmpty_BlankLines_ReturnsEmptyStr() {
		mockStdInput(BLANK_LINES);

		String result = userInput.getLineAllowEmpty(PROMPT);
		assertThat(result, equalTo(""));
		assertThat(testOut.toString(), equalTo(PROMPT + OR_BLANK + NEWLINE));
	}

	@Test
	public void getInt_ValidInputWithWhitespaces_ReturnsInt() {
		int expected = -25;
		String input = "\t  " + expected + " ";

		mockStdInput(input);

		int res = userInput.getInt(PROMPT, false);
		assertThat(res, equalTo(expected));
		assertThat(testOut.toString(), equalTo(PROMPT + NEWLINE));
	}

	@Test
	public void getInt_InputNotAValidNumber_ThrowsException() {
		String input = "not a-25number";

		mockStdInput(input);
		Exception exception = assertThrows(NoSuchElementException.class, () -> {
			int res = userInput.getInt(PROMPT, false);
		});

		assertThat(exception.getMessage(), containsString("No line found"));
		assertThat(testOut.toString(), containsString(PROMPT + NEWLINE));
		//	now stream is empty on the second iteration, so the prompt should be printed twice
		assertThat(testOut.toString().length(), equalTo((PROMPT + NEWLINE).length() * 2));
	}

	@Test
	public void getInt_NotValidLineThenValid_ReturnsValidNum() {
		int expected = 42;
		String input = "not a-25number" + NEWLINE + "\t " + expected;

		mockStdInput(input);
		int res = userInput.getInt(PROMPT, false);

		assertThat(res, equalTo(expected));
		assertThat(testOut.toString(), containsString(PROMPT + NEWLINE));
		assertThat(testOut.toString().length(), equalTo((PROMPT + NEWLINE).length() * 2));
	}

	@Test
	public void getInt_OptionalAndBlank_ReturnsZero() {
		int expected = 0;
		String input = "  \t" + NEWLINE;

		mockStdInput(input);
		int res = userInput.getInt(PROMPT, true);

		assertThat(res, equalTo(expected));
		assertThat(testOut.toString(), containsString(PROMPT + OR_BLANK + NEWLINE));
	}

	@Test
	public void getInt_OptionalAndValid_ReturnsNum() {
		int expected = 42;
		String input = "  \t42" + NEWLINE;

		mockStdInput(input);
		int res = userInput.getInt(PROMPT, true);

		assertThat(res, equalTo(expected));
		assertThat(testOut.toString(), containsString(PROMPT + OR_BLANK + NEWLINE));
	}

	@Test
	public void getIntInRange_ValidInput_ReturnsNum() {
		int expected = 7;
		String input = "7" + NEWLINE;

		mockStdInput(input);
		int res = userInput.getIntInRange(PROMPT, 1, 7, false);

		assertThat(res, equalTo(expected));
		assertThat(testOut.toString(), containsString(PROMPT + NEWLINE));
	}

	@Test
	public void getIntInRange_InvalidInputThenValid_ReturnsValid() {
		int expected = 1;
		String input = "0" + NEWLINE + "100" + NEWLINE + " asd " + NEWLINE + " 1" + NEWLINE;

		mockStdInput(input);
		int res = userInput.getIntInRange(PROMPT, 1, 7, false);

		assertThat(res, equalTo(expected));
		assertThat(testOut.toString(), containsString(PROMPT + NEWLINE));
		assertThat(testErr.toString(), containsString("Invalid number"));
	}

	@Test
	public void getIntInRange_OptionalAndBlank_ReturnsZero() {
		int expected = 0;
		String input = "  \t" + NEWLINE;

		mockStdInput(input);
		int res = userInput.getIntInRange(PROMPT, 1, 7, true);

		assertThat(res, equalTo(expected));
	}

	@Test
	public void getBoolean_ValidInput_ReturnsBoolean() {
		String input = " YES(or maybe not?)";    // Takes only 1st not whitespace char

		mockStdInput(input);
		boolean res = userInput.getBoolean(PROMPT, false);
		assertThat(res, equalTo(true));

		assertThat(testOut.toString(), containsString(PROMPT + " (y/n)"));
	}

	@Test
	public void getBoolean_InvalidThenValid_ReturnsValid() {
		String input = "999" + NEWLINE + "ohYes!" + NEWLINE + "n" + NEWLINE;

		mockStdInput(input);
		boolean res = userInput.getBoolean(PROMPT, false);
		assertThat(res, equalTo(false));
	}

	@Test
	public void getBoolean_OptionalAndBlank_ReturnsNull() {
		mockStdInput(NEWLINE);

		Boolean res = userInput.getBoolean(PROMPT, true);
		assertNull(res);
	}

	private void mockStdInput(String inputLine) {
		System.setIn(new ByteArrayInputStream(inputLine.getBytes()));
		userInput = new UserInput();
	}
}