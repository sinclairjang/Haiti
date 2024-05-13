package com.zerobase.haito.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zerobase.haito.exception.StockNotFound;
import com.zerobase.haito.model.Dividend;
import com.zerobase.haito.model.DividendHistory;
import com.zerobase.haito.model.Stock;
import com.zerobase.haito.model.constants.CacheKey;
import com.zerobase.haito.persistence.entity.DividendEntity;
import com.zerobase.haito.persistence.entity.StockEntity;
import com.zerobase.haito.persistence.repository.DividendRepository;
import com.zerobase.haito.persistence.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinanceService {
	private final  DividendRepository dividendRepository;
	private final  StockRepository stockRepository;
	
	@Cacheable(key = "#stockName", value = CacheKey.KEY_FINANCE)
	public DividendHistory getDividendByCompanyName(String stockName) {
		
		StockEntity stock = stockRepository.findByName(stockName)
				.orElseThrow(() -> new StockNotFound());
		
		List<DividendEntity> dividendEntities = dividendRepository.findAllByStock(stock);
		
		List<Dividend> dividends = dividendEntities.stream()
				.map(e -> {
					return Dividend.from(e);
				}).collect(Collectors.toList());
		
		return new DividendHistory(Stock.from(stock), dividends);
	}
	
}
