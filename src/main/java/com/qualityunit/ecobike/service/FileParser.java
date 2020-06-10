package com.qualityunit.ecobike.service;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.FoldingBike;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.qualityunit.ecobike.model.BikeType.*;
import static java.lang.System.*;

public class FileParser {
	private static final String ERROR_MSG = "Error during input file parsing. Line #%d was ignored:\n'%s'";
	private static final String TYPE_BRAND_REGEX = "(" + FOLDING_BIKE.toString() + "|"
														+ EBIKE.toString() + "|"
														+ SPEEDELEC.toString() + ")"
														+ " (.+$)";
	private String currentLine;
	private long lineCount;

	public void parseFileLinesStream(Stream<String> stream) {
		lineCount = 0;
		final Pattern pattern = Pattern.compile(TYPE_BRAND_REGEX);
//		final Pattern pattern = Pattern.compile("([A-Z \\-]+)([A-Z].*)");
		stream.forEach(ln -> {
			lineCount++;
			currentLine = ln;
			String[] arr = ln.split("; ");

			Stream.of(arr).forEach(out::println);

			Matcher matcher = pattern.matcher(arr[0]);
			if (matcher.find()) {
				String bikeType = matcher.group(1);
				String brandName = matcher.group(2);
				out.println("--> [" + bikeType + "]");
				out.println("--> [" + brandName + "]");
				convertLineToObject(bikeType, brandName, arr);
			} else {
				err.println(String.format(ERROR_MSG, lineCount, currentLine));
			}
		});
	}

//	TODO Implement validation and exception mechanism
	private void convertLineToObject(String bikeTypeId, String brandName, String[] arr) {
		AbstractBike bike = null;
		BikeType bikeType = BikeType.fromString(bikeTypeId);
		try {
			switch (bikeType) {
				case FOLDING_BIKE:
					bike = FoldingBike.getBuilder()
							.withBrand(brandName)
							.withWheelSize(Integer.parseInt(arr[1]))
							.withGearsNum(Integer.parseInt(arr[2]))
							.withWeight(Integer.parseInt(arr[3]))
							.withHasLights(Boolean.parseBoolean(arr[4]))
							.withColor(arr[5])
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
							.withHasLights(Boolean.parseBoolean(arr[3]))
							.withBatteryCapacity(Integer.parseInt(arr[4]))
							.withColor(arr[5])
							.withPrice(Integer.parseInt(arr[6]))
							.build();
					break;
				default:
					err.println(String.format(ERROR_MSG, lineCount, currentLine));
			}
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			err.println(String.format(ERROR_MSG, lineCount, currentLine));
		}

		if (bike != null) {
			out.println("====>NEW BIKE! " + bike.toString() + "\n");
		}



	}

}
