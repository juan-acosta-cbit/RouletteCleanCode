package com.mezubo.roulette.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mezubo.roulette.VO.ResponseBetVO;
import com.mezubo.roulette.VO.ResponseCloseBetVO;
import com.mezubo.roulette.VO.ResponseVO;
import com.mezubo.roulette.VO.ResultGameVO;
import com.mezubo.roulette.common.RouletteCommon;
import com.mezubo.roulette.dao.BetDAO;
import com.mezubo.roulette.dao.GameDAO;
import com.mezubo.roulette.dao.ResultDAO;
import com.mezubo.roulette.dao.RouletteDAO;
import com.mezubo.roulette.entity.Bet;
import com.mezubo.roulette.entity.Game;
import com.mezubo.roulette.entity.Result;
import com.mezubo.roulette.entity.Roulette;

@RestController
@RequestMapping("roulettes")
public class RouletteREST {	

	@Autowired
	private RouletteDAO rouletteDAO;

	@Autowired
	private BetDAO betDAO;

	@Autowired
	private GameDAO gameDAO;
	
	@Autowired
	private ResultDAO resultDAO;
	
	public RouletteCommon commons = new RouletteCommon();
	public ResponseBetVO response;

	@GetMapping
	public ResponseEntity<List<Roulette>> getAllRoulettes() {
		List<Roulette> roulettes = rouletteDAO.findAll();
		
		return ResponseEntity.ok(roulettes);
	}

	@RequestMapping(value="{rouletteId}") 
	public ResponseEntity<Roulette> getRouletteById(@PathVariable("rouletteId") Long rouletteId) {
		Optional<Roulette> roulette = rouletteDAO.findById(rouletteId);
		if(roulette.isPresent()){
			
			return ResponseEntity.ok(roulette.get());
		}
		else{
			
			return ResponseEntity.noContent().build();
		}
	}

	@PostMapping
	public ResponseEntity<Roulette> createRoulette(@RequestBody Roulette roulette) {
		Roulette newRoulette = rouletteDAO.save(roulette);	
		
		return ResponseEntity.ok(newRoulette);
	}

	@PutMapping(value="{rouletteId}") 
	public ResponseEntity<ResponseVO> openRoulette(@PathVariable("rouletteId") Long rouletteId) {
		Optional<Roulette> roulette = rouletteDAO.findById(rouletteId);
		ResponseVO response = new ResponseVO();
		if(roulette.isPresent()) {
			Roulette updateRoulette = roulette.get();
			updateRoulette.setState(true);
			rouletteDAO.save(updateRoulette);
			response.setMessage("Ruleta " + roulette.get().getName() + " abierta exitosamente");			
			Game game = new Game();
			game.setIdRoulette(updateRoulette.getId());
			game.setOpenDate(new Date());
			game.setState(true);
			gameDAO.save(game);			
		}
		else {
			response.setMessage("Ruleta " + rouletteId + " no ha sido creada");
		}
		
		return ResponseEntity.ok(response);
	}

	@PostMapping(value="/bet")
	public ResponseEntity<ResponseBetVO> betForOpeRoulette(@RequestBody Bet bet, @RequestHeader Long idUser) {		
		Optional<Roulette> roulette = rouletteDAO.findById(bet.getIdRoulette());
		bet.setUserId(idUser);
		if(roulette.isPresent()) {
			if(roulette.get().getState()) {
				if(bet.getBetValue() > 10000) {
					response.setMessage("El valor maximo de la apuesta es de USD$10.000");					
				}
				else{
					Bet newBet = bet;
					Roulette getRoulette = roulette.get();
				    Game game = getActiveGameByRoulette(getRoulette);					
					bet.setIdGame(game.getId());
				    betDAO.save(bet);
				    response = new ResponseBetVO("Apuesta realizada exitosamente", newBet);
				}
			}
			else {
				response.setMessage("Ruleta " + bet.getIdRoulette() + " no ha sido abierta");
			}
		}
		else {
			response.setMessage("Ruleta " + bet.getIdRoulette() + " no ha sido creada");
		}

		return ResponseEntity.ok(response);
	}

	@PostMapping(value="closeBet/{rouletteId}")
	public ResponseEntity<ResponseCloseBetVO> closeRoulette(@PathVariable("rouletteId") Long idRoulette) {
		Optional<Roulette> roulette = rouletteDAO.findById(idRoulette);
		if(roulette.isPresent()) {
			if(roulette.get().getState()) {
		
				Game game = getActiveGameByRoulette(roulette.get());
				ResultGameVO resultLaunch = commons.launchBallRoulette();
				Result result = saveResultGame(game, resultLaunch );
				List<Bet> betList = getBetsByGame(game, resultLaunch);
				closeRoulette(roulette.get(), game);
		
				return ResponseEntity.ok(new ResponseCloseBetVO("Juego cerrado exitosamente", result, betList));
			}
			else {
				
				return ResponseEntity.ok(new ResponseCloseBetVO("Ruleta " + idRoulette + " no ha sido abierta", null, null));
			}
		}
		else {
			
			return ResponseEntity.ok(new ResponseCloseBetVO("Ruleta " + idRoulette + " no ha sido creada", null, null));
		}
	}
	
	private Game getActiveGameByRoulette(Roulette roulette) {
		Game game = new Game();
		game.setIdRoulette(roulette.getId());
		game.setState(true);
	    Example<Game> gameExample = Example.of(game);
	    Optional<Game> newGame = gameDAO.findOne(gameExample);
	    System.out.println("ingreso");
		return newGame.get();
	}
	
	private List<Bet> getBetsByGame(Game game, ResultGameVO resultLaunch) {
		List<Bet> betList = new ArrayList<Bet>();
		Bet bet = new Bet();
		Double earnedValue = 0D;
		bet.setIdGame(game.getId());
	    Example<Bet> bets = Example.of(bet);
		Iterable<Bet> findBets = betDAO.findAll(bets);		
		for (Bet item : findBets) {
			if(item.getNumber() != null) {
		    	if(resultLaunch.getWinningNumber() == item.getNumber()) {
		    		earnedValue = item.getBetValue() * 5;  		
		    	}
		    }
		    if(!item.getColor().isEmpty() || item.getColor() == "") {			    	
		    	if(resultLaunch.getWinningColor().toUpperCase().equals(item.getColor().toUpperCase())) {
		    		earnedValue += item.getBetValue() * 1.8;
		    	}
		    }
		    item.setEarnedValue(earnedValue);
			betList.add(item);
	    }
		
		return betList;
	}
	
	private void closeRoulette(Roulette roulette, Game game) {
		game.setState(false);
		roulette.setState(false);
		gameDAO.save(game);
		rouletteDAO.save(roulette);
	}
	
	private Result saveResultGame(Game game, ResultGameVO resultGameVO) {
		Result result = new Result();
	    result.setResultDate(new Date());
	    result.setWinningColor(resultGameVO.getWinningColor());
	    result.setWinningNumber(resultGameVO.getWinningNumber());
	    result.setIdGame(game.getId());
	    resultDAO.save(result);	
	    
	    return result;
	}
}
