package com.zerobase.haito.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zerobase.haito.model.DividendHistory;
import com.zerobase.haito.model.Stock;
import com.zerobase.haito.model.constants.CacheKey;
import com.zerobase.haito.persistence.entity.DividendEntity;
import com.zerobase.haito.persistence.entity.StockEntity;
import com.zerobase.haito.persistence.repository.DividendRepository;
import com.zerobase.haito.persistence.repository.StockRepository;
import com.zerobase.haito.scraper.WebScraper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableCaching
@RequiredArgsConstructor
public class ScraperScheduler {
	private final StockRepository stockRepository;
	private final DividendRepository dividendRepository;
	private final WebScraper dividendHistoryScraper;

	@CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
	@Scheduled(cron = "${scheduler.scrape.dividend-history}")
	public void scrapePeriodically() {
		log.info("periodic scraping starting ({})", LocalDateTime.now());
		List<StockEntity> myStocks = stockRepository.findAll();
		for (StockEntity myStock : myStocks) {
			DividendHistory dividendHistory = dividendHistoryScraper
					.getDividendHistory(Stock.from(myStock));
			
			dividendHistory.getDividends().stream()
			.map(e -> DividendEntity.from(myStock, e))
			.forEach(e -> {
					boolean exists = dividendRepository.existsById(e.getId());
					if (!exists) {
						dividendRepository.save(e);
					}
				}
			);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
			
		}	

	}
}
