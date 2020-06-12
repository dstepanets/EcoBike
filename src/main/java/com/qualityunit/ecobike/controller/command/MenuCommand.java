package com.qualityunit.ecobike.controller.command;

public abstract class MenuCommand implements Executable {
	private final String description;

	protected MenuCommand(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
