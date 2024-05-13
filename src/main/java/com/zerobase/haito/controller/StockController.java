package com.zerobase.haito.controller;

import java.io.IOException;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.haito.exception.InvalidTickerException;
import com.zerobase.haito.model.Stock;
import com.zerobase.haito.persistence.entity.StockEntity;
import com.zerobase.haito.service.StockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
	private final StockService stockService;
	
	@GetMapping("/autocomplete")
	public ResponseEntity<?> getAutoComplete(@RequestParam String keyword) {
		//List<String> autoComplete = stockService.autoComplete(keyword);
		List<String> autoComplete = stockService.getCompanyNamesByKeyword(keyword);
		return ResponseEntity.ok(autoComplete);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('READ')")
	public ResponseEntity<?> getMyStocks(final Pageable pageable) {
		Page<StockEntity> allStocks = stockService.getAllStocks(pageable);
		return ResponseEntity.ok(allStocks);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('WRITE')")
	public ResponseEntity<?> addMyStock(@RequestBody Stock request) throws InvalidTickerException, IOException {
		Stock stock = stockService.store(request.getTicker().trim());
		stockService.addAutoCmpleteKeyword(stock.getName());
		return ResponseEntity.ok(stock);
	}
	
	@DeleteMapping("/{ticker}")
	@PreAuthorize("hasRole('WRITE')")
	public ResponseEntity<?> deleteMyStock(@PathVariable String ticker) {
		String stockName = stockService.deleteStock(ticker);
		return ResponseEntity.ok(stockName);
	}
	
}
