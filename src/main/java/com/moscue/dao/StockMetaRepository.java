package com.moscue.dao;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.Balance;
import com.moscue.entity.StockHolder;
import com.moscue.entity.StockMeta;

public interface StockMetaRepository extends CrudRepository<StockMeta, Long> {
}
