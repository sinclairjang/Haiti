package com.zerobase.haito.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.haito.model.DividendHistory;
import com.zerobase.haito.service.FinanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FinanceController {
	private final FinanceService financeService;
	
	@GetMapping("/dividend/{stockName}")
	public ResponseEntity<DividendHistory> getDividendHistory(@PathVariable String stockName) {
		DividendHistory dividendHistory = financeService.getDividendByCompanyName(stockName);
		return ResponseEntity.ok(dividendHistory);
	}
}
