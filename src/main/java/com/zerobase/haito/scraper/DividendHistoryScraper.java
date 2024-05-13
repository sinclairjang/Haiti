package com.zerobase.haito.scraper;

import java.io.IOException;
import java.time.LocalDate;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.zerobase.haito.exception.InvalidTickerException;
import com.zerobase.haito.exception.ParseException;
import com.zerobase.haito.model.Stock;
import com.zerobase.haito.model.Dividend;
import com.zerobase.haito.model.DividendHistory;

@Component
public class DividendHistoryScraper implements WebScraper {
	private static String scrapeUrl = "https://dividendhistory.org/payout/";
	
	public  DividendHistory getDividendHistory(Stock company) {
		DividendHistory dividendHistory = new DividendHistory();
		dividendHistory.setCompany(company);
		
		Connection connection = Jsoup.connect(scrapeUrl + company.getTicker());
		
		try {
			Document document = connection.get();
			Element element = document.getElementById("dividend_table");
			Elements tbodys = element.getElementsByTag("tbody");
			for (Element e : tbodys.get(0).children()) {
				if (e.className().equals("unconfirmed-div"))
					continue;

				for (int tdIdx = 0; tdIdx < 3; ++tdIdx) {
					if (e.child(tdIdx).tag().getName().equals("td") == false) {

						throw new ParseException();

					}
				}

				dividendHistory.addDividend(Dividend.builder().exDividendDate(LocalDate.parse(e.child(0).text()))
						.payoutDate(LocalDate.parse(e.child(1).text())).cashAmount(e.child(2).text()).build());

			}
				
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			throw new ParseException();
		}
		return dividendHistory;
	}
	
	public  DividendHistory getDividendHistory(Stock company, LocalDate startDate, LocalDate endDate) {
		DividendHistory dividendHistory = new DividendHistory();
		dividendHistory.setCompany(company);
		
		Connection connection = Jsoup.connect(scrapeUrl + company.getTicker());
		
		try {
			Document document = connection.get();
			Elements elements = document.getElementsByTag("tbody");
			for (Element e : elements.get(1).children()) {
				if (e.className().equals("unconfirmed-div"))
					continue;

				for (int i = 0; i < 3; ++i) {
					if (e.child(i).tag().getName().equals("td") == false) {
						throw new ParseException();
					}
				}
				
				if (LocalDate.parse(e.child(0).text()).isBefore(startDate))
					continue;
				
				if (LocalDate.parse(e.child(0).text()).isAfter(endDate))
					continue;
				
				dividendHistory.addDividend(
						Dividend.builder()
							.exDividendDate(LocalDate.parse(e.child(0).text()))
							.payoutDate(LocalDate.parse(e.child(1).text()))
							.cashAmount(e.child(2).text())
							.build()
						);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dividendHistory;
	}
	
	public  Stock getStock(String ticker) throws InvalidTickerException, IOException {
		Connection connection = Jsoup.connect(scrapeUrl + ticker);

		try {
			Document document = connection.get();
			String companyName = document.getElementsByTag("h4")
									.get(0)
									.text()
									.replace("(" + ticker + ")", "")
									.trim();
			return Stock.builder()
					.name(companyName)
					.ticker(ticker)
					.build();
		} catch (HttpStatusException e) {
			throw new InvalidTickerException();
		} catch (IOException e) {
			throw e;
			
		}
	}
}
