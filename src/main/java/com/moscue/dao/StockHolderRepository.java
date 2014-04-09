package com.moscue.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.StockHolder;

public interface StockHolderRepository extends CrudRepository<StockHolder, Long> {
	List<StockHolder> findByCode(String code);
}
