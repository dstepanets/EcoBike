package com.qualityunit.ecobike.service;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeBuildingException;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.FoldingBike;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.qualityunit.ecobike.model.BikeType.EBIKE;
import static com.qualityunit.ecobike.model.BikeType.FOLDING_BIKE;
import static com.qualityunit.ecobike.model.BikeType.SPEEDELEC;
import static java.lang.System.err;
import static java.lang.System.out;

public class FileParser {
	public static final String LINE_ERROR = "Error during input file parsing. Line #%d was ignored:\n'%s'";
	public static final String INVALID_TYPE_OR_BRAND = "Invalid bike type or brand name";
	private static final String ERROR_MSG = "Error message: \"%s\"";
	private static final String TYPE_BRAND_REGEX = "(" + FOLDING_BIKE.toString() + "|"
			+ EBIKE.toString() + "|"
			+ SPEEDELEC.toString() + ")"
			+ " (.+$)";
	private String currentLine;
	private long lineCount;

	public long parseLinesStreamToStorage(Stream<String> stream, List<AbstractBike> catalog) {
		lineCount = 0;
		final Pattern pattern = Pattern.compile(TYPE_BRAND_REGEX);

		stream.forEach(ln -> {
			lineCount++;
			currentLine = ln;
			String[] arr = ln.split("; ");

			Stream.of(arr).forEach(out::println);

			Matcher matcher = pattern.matcher(arr[0]);
			if (matcher.find()) {
				String bikeType = matcher.group(1);
				String brandName = matcher.group(2);
				out.println("--> [" + bikeType + "]");    //
				out.println("--> [" + brandName + "]");
				Optional<AbstractBike> bike = buildBikeFromLine(bikeType, brandName, arr);
				bike.ifPresent(catalog::add);
			} else {
				printParsingError(INVALID_TYPE_OR_BRAND);
			}
		});
		out.println("Lines processed: " + lineCount + " | Items added: " + catalog.size());
		return lineCount;
	}

	//	TODO Implement validation and exception mechanism
	private Optional<AbstractBike> buildBikeFromLine(String bikeTypeId, String brandName, String[] arr) {
		AbstractBike bike = null;
		try {
			BikeType bikeType = BikeType.fromString(bikeTypeId);
			switch (bikeType) {
				case FOLDING_BIKE:
					bike = FoldingBike.getBuilder()
							.withBikeType(bikeType)
							.withBrand(brandName)
							.withWheelSize(Integer.parseInt(arr[1]))
							.withGearsNum(Integer.parseInt(arr[2]))
							.withWeight(Integer.parseInt(arr[3]))
							.withHasLights(parseBooleanWithException(arr[4]))
							.withColor(checkForEmptyColor(arr[5]))
							.withPrice(Integer.parseInt(arr[6]))
							.build();
					break;
				case EBIKE:
				case SPEEDELEC:
					bike = ElectricBike.getBuilder()
							.withBikeType(bikeType)
							.withBrand(brandName)
							.withMaxSpeed(Integer.parseInt(arr[1]))
							.withWeight(Integer.parseInt(arr[2]))
							.withHasLights(parseBooleanWithException(arr[3]))
							.withBatteryCapacity(Integer.parseInt(arr[4]))
							.withColor(checkForEmptyColor(arr[5]))
							.withPrice(Integer.parseInt(arr[6]))
							.build();
					break;
			}
		} catch (NumberFormatException e) {
			printParsingError("Invalid number: " + e.getMessage());
		} catch (BikeBuildingException | IllegalArgumentException | IndexOutOfBoundsException e) {
			printParsingError(e.getMessage());
		}

		if (bike != null) {
			out.println("====>NEW BIKE! " + bike.toString() + "\n");
		}

		return Optional.ofNullable(bike);
	}

	private Boolean parseBooleanWithException(String s) {
		if (Boolean.parseBoolean(s)) {
			return true;
		} else if (s.equalsIgnoreCase("false")) {
			return false;
		}
		throw new IllegalArgumentException("Boolean is expected in place of '" + s + "'");
	}

	private String checkForEmptyColor(String s) {
		if (s == null || s.trim().isEmpty()) {
			throw new IllegalArgumentException("Color field can't be empty");
		}
		return s;
	}

	private void printParsingError(String message) {
		err.println(String.format(LINE_ERROR, lineCount, currentLine));
		err.println(String.format(ERROR_MSG, message));
	}
}
