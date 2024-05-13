package com.zerobase.haito.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.haito.persistence.entity.DividendEntity;
import com.zerobase.haito.persistence.entity.StockEntity;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
	boolean existsByStock(StockEntity stock);
	boolean existsByStockAndPayoutDate(StockEntity stock, LocalDate payoutDate);
	List<DividendEntity> findAllByStock(StockEntity stock);
	void deleteAllByStock(StockEntity stock);
}
