package com.mezubo.roulette.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mezubo.roulette.entity.Result;

public interface ResultDAO extends JpaRepository<Result, Long> {
}
