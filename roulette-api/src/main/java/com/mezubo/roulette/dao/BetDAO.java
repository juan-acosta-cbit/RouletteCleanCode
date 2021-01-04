package com.mezubo.roulette.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mezubo.roulette.entity.Bet;

public interface BetDAO extends JpaRepository<Bet, Long> {
}
