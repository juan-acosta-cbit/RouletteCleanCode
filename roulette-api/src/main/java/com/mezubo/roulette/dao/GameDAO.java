package com.mezubo.roulette.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mezubo.roulette.entity.Game;

public interface GameDAO extends JpaRepository<Game, Long>{
}
