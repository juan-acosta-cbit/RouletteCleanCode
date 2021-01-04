package com.mezubo.roulette.common;

import com.mezubo.roulette.VO.ResultGameVO;

public class RouletteCommon {
	
	private static int min = 0;
    private static int max = 36;	
	
	public ResultGameVO launchBallRoulette() {
		Integer randomNumber = (int)(Math.random() * (max - min + 1) + min);
	    String randomColor = (randomNumber % 2 == 0 ? "ROJO" : "NEGRO");	    

	    return new ResultGameVO(randomNumber, randomColor);
	}
}
