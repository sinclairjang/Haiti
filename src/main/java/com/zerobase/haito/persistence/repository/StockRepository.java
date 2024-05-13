package com.zerobase.haito.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.haito.persistence.entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
	public boolean existsByTicker(String ticker);
	
	public Optional<StockEntity> findByName(String name);
	
	public Page<StockEntity> findByNameStartingWithIgnoreCase(String s, Pageable page);

	public Optional<StockEntity> findByTicker(String ticker);
}
