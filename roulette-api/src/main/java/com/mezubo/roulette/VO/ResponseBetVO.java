package com.mezubo.roulette.VO;

import com.mezubo.roulette.entity.Bet;

public class ResponseBetVO {

	private String message;
	private Bet bet;
	
	public ResponseBetVO(String message, Bet bet) {
		super();
		this.message = message;
		this.bet = bet;
	}	

	public ResponseBetVO() {
		super();
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Bet getBet() {
		return bet;
	}
	
	public void setBet(Bet bet) {
		this.bet = bet;
	}
}
