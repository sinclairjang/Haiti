package com.zerobase.haito.scraper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.zerobase.haito.model.Stock;
import com.zerobase.haito.model.DividendHistory;

class DividendHistoryScraperTest {
	@Test
	void get_dividend_history() throws Exception {
		Stock company = Stock.builder()
				.name("Costco")
				.ticker("COST")
				.build();
		
		DividendHistoryScraper dividendHistoryScraper = new DividendHistoryScraper();
		
		DividendHistory scrapeResult = dividendHistoryScraper.getDividendHistory(company);
		System.out.println(scrapeResult);
	}
	
	@Test
	void get_company() throws Exception {
		DividendHistoryScraper dividendHistoryScraper = new DividendHistoryScraper();
		dividendHistoryScraper.getStock("COST");
	}
}
