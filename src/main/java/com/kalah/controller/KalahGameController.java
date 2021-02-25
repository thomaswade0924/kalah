package com.kalah.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kalah.domain.GameMove;
import com.kalah.domain.KalahGame;
import com.kalah.service.GameService;

/**
 * Controller of the Kalah game. 
 *
 */
@RestController
@RequestMapping("/kalah")
public class KalahGameController {
	
	/**
	 * Initialze a new game. 
	 * @return A new Kalah game.
	 */
	// @RequestMapping(value = "/init", method = RequestMethod.GET)
	@RequestMapping("/init")
	public KalahGame initGame() {
		return KalahGame.init();
	}
	
	/**
	 * Used for every single move of a game. 
	 * @return Kalah game after this move. 
	 */
	@RequestMapping(value = "/move", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public KalahGame move(@RequestBody GameMove move) {
		GameService.play(move);
		return move.getGame();
	}
}
