package com.moscue.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.Position;

public interface PositionRepository extends CrudRepository<Position, Long> {
	List<Position> findByCode(String code);
}
