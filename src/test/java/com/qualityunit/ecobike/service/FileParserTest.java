package com.qualityunit.ecobike.service;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(MockitoJUnitRunner.class)
public class FileParserTest {
	private final FileParser fileParser = new FileParser();
	private Stream<String> stream;
	private List<AbstractBike> catalog;
	private static final String BIKE1 = "FOLDING BIKE Dahon; 20; 27; 15900; true; rose; 1589";
	private static final String BIKE2 = "E-BIKE Felt; 25; 25200; false; 30000; white; 669";
	private static final String BIKE3 = "SPEEDELEC Speedway; 35; 19200; true; 10400; brown; 1019";

	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private final ByteArrayOutputStream testErr = new ByteArrayOutputStream();
	private final PrintStream backupOut = System.out;
	private final PrintStream backupErr = System.err;

	@Before
	public void setUp() {
		catalog = new ArrayList<>();
		System.setOut(new PrintStream(testOut));
		System.setErr(new PrintStream(testErr));
	}

	@After
	public void tearDown() {
		stream.close();
		System.setOut(new PrintStream(backupOut));
		System.setErr(new PrintStream(backupErr));
	}

	@Test
	public void parseLines_ValidLines_Add3DifferentRecords() {
		stream = Stream.of(BIKE1, BIKE2, BIKE3);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(3));
		assertThat(lineCount, equalTo(3L));
		assertThat(catalog.get(0).getBikeType(), equalTo(BikeType.FOLDING_BIKE));
		assertThat(catalog.get(0).toString(), equalTo(BIKE1));
		assertThat(catalog.get(1).toString(), equalTo(BIKE2));
		assertThat(catalog.get(2).toString(), equalTo(BIKE3));
	}

	@Test
	public void parseLines_EmptyStream_NothingAdded() {
		stream = Stream.empty();

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(0));
		assertThat(lineCount, equalTo(0L));
	}

	@Test
	public void parseLines_EmptyLine_IsSkipped() {
		stream = Stream.of(BIKE1, "", BIKE3);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 2, "")));
	}

	@Test
	public void parseLines_InvalidBikeType_LineSkipped() {
		String invalid = "FOLDING CAKE Dahon; 20; 27; 15900; true; rose; 1589";
		stream = Stream.of(invalid, BIKE2, BIKE3);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 1, invalid)));
		assertThat(testErr.toString(), containsString(FileParser.INVALID_TYPE_OR_BRAND));
	}

	@Test
	public void parseLines_NoBrandName_LineSkipped() {
		String invalid = "FOLDING BIKE; 20; 27; 15900; true; rose; 1589";
		stream = Stream.of(invalid, BIKE2, BIKE3);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 1, invalid)));
		assertThat(testErr.toString(), containsString(FileParser.INVALID_TYPE_OR_BRAND));
	}

	@Test
	public void parseLines_InvalidBoolean_ShowsErrorSkipsLine() {
		String notBool = "no lights, no boolean";
		String invalid = "E-BIKE Felt; 25; 25200; " + notBool + "; 30000; white; 669";
		stream = Stream.of(invalid, BIKE2, BIKE3);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 1, invalid)));
		assertThat(testErr.toString(), containsString("Boolean is expected in place of '" + notBool + "'"));
	}

	@Test
	public void parseLines_EmptyColumn_ShowsErrorSkipsLine() {
		String invalid = "FOLDING BIKE Dahon; 20; ; 15900; true; rose; 1589";
		stream = Stream.of(invalid, BIKE2, BIKE3);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 1, invalid)));
		assertThat(testErr.toString(), containsString("Invalid number"));
	}

	@Test
	public void parseLines_NotANumber_ShowsErrorSkipsLine() {
		String invalid = "FOLDING BIKE Dahon; 20; 27; NOT A NUMBER; true; rose; 1589";
		stream = Stream.of(invalid, BIKE2, BIKE3);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 1, invalid)));
		assertThat(testErr.toString(), containsString("Invalid number"));
	}

	@Test
	public void parseLines_NegativeSpeed_ShowsErrorSkipsLine() {
		String invalid = "SPEEDELEC Speedway; -35; 19200; true; 10400; brown; 1019";
		stream = Stream.of(BIKE1, BIKE2, invalid);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 3, invalid)));
		assertThat(testErr.toString(), containsString("Maximum speed can't be negative"));
	}

	@Test
	public void parseLines_EmptyColor_ShowsErrorSkipsLine() {
		String invalid = "SPEEDELEC Speedway; 35; 19200; true; 10400; ; 1019";
		stream = Stream.of(BIKE1, BIKE2, invalid);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 3, invalid)));
		assertThat(testErr.toString(), containsString("Color field can't be empty"));
	}

	@Test
	public void parseLines_MissingSpace_ShowsErrorSkipsLine() {
		String invalid = "SPEEDELEC Speedway; 35; 19200;true; 10400; brown; 1019";
		stream = Stream.of(BIKE1, BIKE2, invalid);

		long lineCount = fileParser.parseLinesStreamToStorage(stream, catalog);

		assertThat(catalog.size(), equalTo(2));
		assertThat(lineCount, equalTo(3L));
		assertThat(testErr.toString(), containsString(String.format(FileParser.LINE_ERROR, 3, invalid)));
		assertThat(testErr.toString(), containsString("Invalid number"));
	}
}