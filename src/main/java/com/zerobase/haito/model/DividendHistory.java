package com.zerobase.haito.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DividendHistory implements Iterable<Dividend> {
	
	private Stock company;
	
	private List<Dividend> dividends;
	
	public DividendHistory() { this.dividends = new ArrayList<>(); }
	
	public void addDividend(Dividend dividend) {
		dividends.add(dividend);
	}

	@Override
	public Iterator<Dividend> iterator() {
		return dividends.iterator();
	}
}
