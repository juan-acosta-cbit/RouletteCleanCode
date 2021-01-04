package com.mezubo.roulette.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mezubo.roulette.entity.Roulette;

public interface RouletteDAO extends JpaRepository<Roulette, Long> {
}
