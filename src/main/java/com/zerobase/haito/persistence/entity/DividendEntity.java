package com.zerobase.haito.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.zerobase.haito.model.Dividend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "DIVIDEND")
@Builder
@Table(
		uniqueConstraints = { 
				@UniqueConstraint( 
						columnNames = {"stock_id", "payoutDate"}
				)
		}
)
		
public class DividendEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="stock_id")
	private StockEntity stock;
	
	private LocalDate exDividendDate;
	
	private LocalDate payoutDate;
	
	private String cashAmount;
	
	public static DividendEntity from(StockEntity stock, Dividend dividend) {
		return DividendEntity.builder()
			.stock(stock)
			.exDividendDate(dividend.getExDividendDate())
			.payoutDate(dividend.getPayoutDate())
			.cashAmount(dividend.getCashAmount())
			.build();
	}
	
}
