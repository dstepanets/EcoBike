package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.Storage;

public class ShowCatalogCommand extends MenuCommand {
	public ShowCatalogCommand(String description) {
		super(description);
	}

	@Override
	public void execute() {
		Storage.getInstance().getCatalog().forEach(i -> System.out.println(i.toDisplayFormatString()));
	}
}
