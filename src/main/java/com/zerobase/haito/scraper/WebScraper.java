package com.zerobase.haito.scraper;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.zerobase.haito.exception.InvalidTickerException;
import com.zerobase.haito.model.Stock;
import com.zerobase.haito.model.DividendHistory;

@Component
public interface WebScraper {
	public DividendHistory getDividendHistory(Stock company);
	
	public DividendHistory getDividendHistory(Stock company, LocalDate startDate, LocalDate endDate);
	
	public Stock getStock(String ticker) throws InvalidTickerException, IOException;
	
}
