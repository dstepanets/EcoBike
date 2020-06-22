package com.qualityunit.ecobike.controller;

import com.qualityunit.ecobike.view.UserInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppControllerTest {
	@Mock
	private UserInput userInput;
	@InjectMocks
	private AppController controller;
	private File inputFile;

	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private final ByteArrayOutputStream testErr = new ByteArrayOutputStream();
	private final PrintStream backupOut = System.out;
	private final PrintStream backupErr = System.err;

	@Rule
	public TemporaryFolder folder = TemporaryFolder.builder().build();

	@Before
	public void setUp() {
		try {
			System.setOut(new PrintStream(testOut));
			System.setErr(new PrintStream(testErr));
			inputFile = folder.newFile("testInput.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(new PrintStream(backupOut));
		System.setErr(new PrintStream(backupErr));
	}

	@Test
	public void getStreamFromFilePath_Args0IsValidPath_ReturnsStream() {
		String[] args = {inputFile.getPath(), "ignored args"};
		Stream<String> stream = controller.getStreamFromFilePath(args);

		assertNotNull(stream);
		assertThat(testOut.toString(), containsString("Reading the file: "));
	}

	@Test
	public void getStreamFromFilePath_NoArgsButValidPathFromInput_ReturnsStream() {
		String[] args = new String[]{};
		when(userInput.getLine(anyString())).thenReturn(inputFile.getPath());

		Stream<String> stream = controller.getStreamFromFilePath(args);
		assertNotNull(stream);
	}

	@Test
	public void getStreamFromFilePath_InvalidPath_ForcesUserToEnterNewOne() {
		String badPath = "";
		String[] args = {badPath};
		when(userInput.getLine(anyString())).thenReturn(badPath, inputFile.getPath());

		Stream<String> stream = controller.getStreamFromFilePath(args);

		assertNotNull(stream);
		verify(userInput, times(2)).getLine(anyString());
		assertThat(testErr.toString(), containsString("Can't open a file at this path: ''"));
	}
}