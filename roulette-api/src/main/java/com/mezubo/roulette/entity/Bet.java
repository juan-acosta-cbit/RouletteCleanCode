package com.mezubo.roulette.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bet")
public class Bet {	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="idRoulette")
	private Long idRoulette;
	
	@Column(name="idGame")
	private Long idGame;
	
	@Column(name="number")
	private Integer number;
	
	@Column(name="color")
	private String color;
	
	@Column(name="userId")
	private Long userId;
	
	@Column(name="betValue")
	private Double betValue;
	
	@Column(name="earnedValue")
	private Double earnedValue = 0D;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getBetValue() {
		return betValue;
	}
	public void setBetValue(Double betValue) {
		this.betValue = betValue;
	}
	public Double getEarnedValue() {
		return earnedValue;
	}
	public void setEarnedValue(Double earnedValue) {
		this.earnedValue = earnedValue;
	}
	public Long getIdRoulette() {
		return idRoulette;
	}
	public void setIdRoulette(Long idRoulette) {
		this.idRoulette = idRoulette;
	}
	public Long getIdGame() {
		return idGame;
	}
	public void setIdGame(Long idGame) {
		this.idGame = idGame;
	}
}
