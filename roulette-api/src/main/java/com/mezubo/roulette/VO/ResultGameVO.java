package com.mezubo.roulette.VO;

public class ResultGameVO {

	private Integer winningNumber;
	private String winningColor;	

	public ResultGameVO() {
		super();
	}

	public ResultGameVO(Integer winningNumber, String winningColor) {
		super();
		this.winningNumber = winningNumber;
		this.winningColor = winningColor;
	}

	public Integer getWinningNumber() {
		return winningNumber;
	}

	public void setWinningNumber(Integer winningNumber) {
		this.winningNumber = winningNumber;
	}

	public String getWinningColor() {
		return winningColor;
	}

	public void setWinningColor(String winningColor) {
		this.winningColor = winningColor;
	}

}
