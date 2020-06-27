package com.qualityunit.ecobike.model;

import java.util.List;

/*
* Encapsulates all data to print in Menu class as a catalog page.
*/
public class CatalogPage {
	public static final int ITEMS_PER_PAGE = 20;
	private final int currentPage;
	private final int totalPages;
	private final int firstItemNum;
	private final int lastItemNum;
	private final int totalItems;
	private final List<AbstractBike> items;

	private CatalogPage(PageBuilder builder) {
		this.currentPage = builder.currentPage;
		this.totalPages = builder.totalPages;
		this.firstItemNum = builder.firstItemNum;
		this.lastItemNum = builder.lastItemNum;
		this.totalItems = builder.totalItems;
		this.items = builder.items;
	}

	public static PageBuilder getBuilder() {
		return new PageBuilder();
	}

	public static class PageBuilder {
		private int currentPage;
		private int totalPages;
		private int firstItemNum;
		private int lastItemNum;
		private int totalItems;
		private List<AbstractBike> items;

		public CatalogPage build() {
			return new CatalogPage(this);
		}

		public PageBuilder withCurrentPage(int currentPage) {
			this.currentPage = currentPage;
			return this;
		}

		public PageBuilder withTotalPages(int totalPages) {
			this.totalPages = totalPages;
			return this;
		}

		public PageBuilder withFirstItemNum(int firstItemNum) {
			this.firstItemNum = firstItemNum;
			return this;
		}

		public PageBuilder withLastItemNum(int lastItemNum) {
			this.lastItemNum = lastItemNum;
			return this;
		}

		public PageBuilder withTotalItems(int totalItems) {
			this.totalItems = totalItems;
			return this;
		}

		public PageBuilder withItems(List<AbstractBike> items) {
			this.items = items;
			return this;
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getFirstItemNum() {
		return firstItemNum;
	}

	public int getLastItemNum() {
		return lastItemNum;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public List<AbstractBike> getItems() {
		return items;
	}
}
