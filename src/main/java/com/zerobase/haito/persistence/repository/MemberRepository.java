package com.zerobase.haito.persistence.repository;

import org.springframework.stereotype.Repository;

import com.zerobase.haito.persistence.entity.MemberEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	
	Optional<MemberEntity> findByUsername(String username);
	
	boolean existsByUsername(String username);
}
