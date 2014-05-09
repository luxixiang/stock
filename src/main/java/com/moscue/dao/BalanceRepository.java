package com.moscue.dao;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.Balance;

public interface BalanceRepository extends CrudRepository<Balance, Long> {
	public Balance getBalanceByHolderId(long holderId);
}
