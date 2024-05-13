package com.zerobase.haito.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.Trie;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerobase.haito.exception.InvalidTickerException;
import com.zerobase.haito.exception.StockAlreadyExistException;
import com.zerobase.haito.exception.StockNotFound;
import com.zerobase.haito.model.Dividend;
import com.zerobase.haito.model.DividendHistory;
import com.zerobase.haito.model.Stock;
import com.zerobase.haito.model.constants.CacheKey;
import com.zerobase.haito.persistence.entity.DividendEntity;
import com.zerobase.haito.persistence.entity.StockEntity;
import com.zerobase.haito.persistence.repository.DividendRepository;
import com.zerobase.haito.persistence.repository.StockRepository;
import com.zerobase.haito.scraper.WebScraper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class StockService {
	
	private final StockRepository stockRepository;
	private final DividendRepository dividendRepository;
	private final WebScraper scraper;
	private final Trie<String, String> trie;
	private final CacheManager redisCacheManager;
	
	public Stock store(String ticker) throws InvalidTickerException, IOException {
		if (stockRepository.existsByTicker(ticker)) {
			throw new StockAlreadyExistException();
		}
		
		return storeStockInfo(ticker);
	}
	
	public Page<StockEntity> getAllStocks(Pageable pageable) {
		return stockRepository.findAll(pageable);
	}
	
	private Stock storeStockInfo(String ticker) throws InvalidTickerException, IOException {
		
		Stock stock = scraper.getStock(ticker);
		DividendHistory dividendHistory = scraper.getDividendHistory(stock);
		
		StockEntity stockEntity = StockEntity.from(stock);
		stockRepository.save(stockEntity);
		
		for (Dividend dividend : dividendHistory) {
			dividendRepository.save(DividendEntity.from(stockEntity, dividend));
		}
		
		return stock;
	}
	
	public void addAutoCmpleteKeyword(String keyword) {
		trie.put(keyword, null);
	}
	
	public List<String> autoComplete(String keyword) {
		return trie.prefixMap(keyword).keySet().stream().collect(Collectors.toList());
	}
	
	public void deleteAutoCompleteKeyword(String keyword) {
		trie.remove(keyword);
	}
	
	public List<String> getCompanyNamesByKeyword(String keyword) {
		Pageable limit = PageRequest.of(0, 10);
		Page<StockEntity> stockEntities = stockRepository.findByNameStartingWithIgnoreCase(keyword, limit);
		return stockEntities.stream()
				.map(e -> e.getName())
				.collect(Collectors.toList());
	}

	@Transactional
	public String deleteStock(String ticker) {
		StockEntity stock = stockRepository.findByTicker(ticker)
				.orElseThrow(() -> new StockNotFound());
		
		dividendRepository.deleteAllByStock(stock);
		stockRepository.delete(stock);
		deleteAutoCompleteKeyword(stock.getName());
		redisCacheManager.getCache(CacheKey.KEY_FINANCE).evict(stock.getName());
		return stock.getName();
	}
}
