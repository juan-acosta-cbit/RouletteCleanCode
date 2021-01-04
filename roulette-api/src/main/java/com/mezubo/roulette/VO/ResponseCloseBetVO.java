package com.mezubo.roulette.VO;

import java.util.List;

import com.mezubo.roulette.entity.Bet;
import com.mezubo.roulette.entity.Result;

public class ResponseCloseBetVO {

	private String message;
	private Result result;
	private List<Bet> bet;

	public ResponseCloseBetVO() {
		super();
	}

	public ResponseCloseBetVO(String message, Result result, List<Bet> bet) {
		super();
		this.message = message;
		this.result = result;
		this.bet = bet;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public List<Bet> getBet() {
		return bet;
	}

	public void setBet(List<Bet> bet) {
		this.bet = bet;
	}
}
