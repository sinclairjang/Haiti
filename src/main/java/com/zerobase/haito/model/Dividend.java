package com.zerobase.haito.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.zerobase.haito.persistence.entity.DividendEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dividend {
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate exDividendDate;
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate payoutDate;
	private String cashAmount;
	
	public static Dividend from(DividendEntity dividendEntity) {
		return Dividend.builder()
				.exDividendDate(dividendEntity.getExDividendDate())
				.payoutDate(dividendEntity.getPayoutDate())
				.cashAmount(dividendEntity.getCashAmount())
				.build();
		
	}
}
