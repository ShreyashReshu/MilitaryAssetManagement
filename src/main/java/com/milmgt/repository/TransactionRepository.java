package com.milmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milmgt.entity.AssetTransaction;

public interface TransactionRepository extends JpaRepository<AssetTransaction, Long> {
}