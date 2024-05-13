package com.zerobase.haito.model;

import com.zerobase.haito.persistence.entity.StockEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {
	private String ticker;
	private String name;
	public static Stock from(StockEntity stockEntity) {
		
		return Stock.builder()
				.ticker(stockEntity.getTicker())
				.name(stockEntity.getName())
				.build();
	}
}
