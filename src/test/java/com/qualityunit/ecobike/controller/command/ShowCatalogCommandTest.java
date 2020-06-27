package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.CatalogPage;
import com.qualityunit.ecobike.model.FoldingBike;
import com.qualityunit.ecobike.view.Menu;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.qualityunit.ecobike.model.CatalogPage.ITEMS_PER_PAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowCatalogCommandTest {
	@Mock
	private Menu menu;
	private ShowCatalogCommand command;
	private List<AbstractBike> catalog;

	private final ByteArrayOutputStream testErr = new ByteArrayOutputStream();
	private final PrintStream backupErr = System.err;

	@Before
	public void setUp() {
		catalog = new ArrayList<>();
		for (int i = 0; i < 101; i++) {
			catalog.add(FoldingBike.getBuilder()
					.withBikeType(BikeType.FOLDING_BIKE)
					.withBrand(String.valueOf(i))
					.withColor("").build());
		}
		command = new ShowCatalogCommand("", menu, catalog);
		System.setErr(new PrintStream(testErr));
	}

	@After
	public void tearDown() {
		System.setErr(new PrintStream(backupErr));
	}

	@Test
	public void execute_BackToMenu_GeneratesFirstPageCorrectly() {
		when(menu.displayCatalogPage(any(CatalogPage.class))).thenReturn("m");

		command.execute();

		testPage(getPage(), 1);
}

	@Test
	public void execute_PreviousAndNextPage_GeneratesCorrectPage() {
		when(menu.displayCatalogPage(any(CatalogPage.class))).thenReturn("p", "p", "n", "n", "m");

		command.execute();

		testPage(getPage(), 3);
	}

	@Test
	public void execute_GoToExistingPage_GeneratesCorrectPage() {
		when(menu.displayCatalogPage(any(CatalogPage.class))).thenReturn("6", "m");

		command.execute();
		CatalogPage page = getPage();

		testPage(page, 6);
		assertEquals(1, page.getItems().size());
	}

	@Test
	public void execute_GoToInvalidPage_GeneratesFirstOrLastPage() {
		when(menu.displayCatalogPage(any(CatalogPage.class))).thenReturn("666", "n", "m");
		command.execute();
		CatalogPage page = getPage();
		testPage(page, 6);

		when(menu.displayCatalogPage(any(CatalogPage.class))).thenReturn("-4546", "m");
		command.execute();
		page = getPage();
		testPage(page, 1);
	}

	@Test
	public void execute_InvalidCommand_PrintsErrorGeneratesSamePage() {
		when(menu.displayCatalogPage(any(CatalogPage.class))).thenReturn("3", "Yoyoyo!", "m");

		command.execute();

		testPage(getPage(), 3);
		assertThat(testErr.toString(), containsString("Invalid command"));

	}

	private CatalogPage getPage() {
		CatalogPage page = null;
		try {
			Field c = ShowCatalogCommand.class.getDeclaredField("page");
			c.setAccessible(true);
			page = (CatalogPage) c.get(command);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			System.err.println(e.getMessage());
		}
		return page;
	}

	private void testPage(CatalogPage page, int pageNum) {
		int firstIdx = (pageNum - 1) * ITEMS_PER_PAGE;
		int lastItemNum = Math.min(firstIdx + ITEMS_PER_PAGE, catalog.size());
		assertEquals(pageNum, page.getCurrentPage());
		assertEquals(6, page.getTotalPages());
		assertEquals(firstIdx + 1, page.getFirstItemNum());
		assertEquals(lastItemNum, page.getLastItemNum());
		assertEquals(catalog.size(), page.getTotalItems());
		assertEquals(catalog.subList(firstIdx, lastItemNum), page.getItems());
	}
}