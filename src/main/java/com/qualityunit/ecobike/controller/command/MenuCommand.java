package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.view.Menu;

public abstract class MenuCommand implements Executable {
	private final String description;
	private final Menu menu;

	public MenuCommand(String description, Menu menu) {
		this.description = description;
		this.menu = menu;
	}

	public String getDescription() {
		return description;
	}

	public Menu getMenu() {
		return menu;
	}
}
