package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.CatalogPage;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;

import java.util.List;

import static com.qualityunit.ecobike.model.CatalogPage.*;
import static java.lang.System.*;

public class ShowCatalogCommand extends MenuCommand {
	private final List<AbstractBike> catalog;
	private int totalItems;
	private int totalPages;
	private CatalogPage page;

	public ShowCatalogCommand(String description, Menu menu) {
		super(description, menu);
		catalog = Storage.getInstance().getCatalog();
	}

	@Override
	public void execute() {
		totalItems = catalog.size();
		if (totalItems == 0) {
			out.println("Catalog is empty");
			return;
		}
		totalPages = totalItems / ITEMS_PER_PAGE;
		if (totalItems % ITEMS_PER_PAGE > 0) {
			totalPages++;
		}
		loadPage(1);
	}

	private void loadPage(int pageNum) {
		if (page == null || pageNum != page.getCurrentPage()) {
			pageNum = pageNum < 1 ? 1 : Math.min(pageNum, totalPages);
			int firstIdx = (pageNum - 1) * ITEMS_PER_PAGE;
			int lastItemNum = Math.min(firstIdx + ITEMS_PER_PAGE, totalItems);
			page = CatalogPage.getBuilder()
					.withCurrentPage(pageNum)
					.withTotalPages(totalPages)
					.withFirstItemNum(firstIdx + 1)
					.withLastItemNum(lastItemNum)
					.withTotalItems(totalItems)
					.withItems(catalog.subList(firstIdx, lastItemNum))
					.build();
		}
		sendPageToViewAndProcessControls(page);
	}

	private void sendPageToViewAndProcessControls(CatalogPage page) {
		Integer pageNum = null;
		while (pageNum == null) {
			String command = getMenu().displayCatalogPage(page);
			switch (command.toLowerCase().charAt(0)) {
				case 'n':
					pageNum = page.getCurrentPage() + 1;
					break;
				case 'p':
					pageNum = page.getCurrentPage() - 1;
					break;
				case 'm':
					return;
				default:
					try {
						pageNum = Integer.parseInt(command);
					} catch (NumberFormatException e) {
						err.println("Invalid command");
					}
					break;
			}
		}
		loadPage(pageNum);
	}
}
