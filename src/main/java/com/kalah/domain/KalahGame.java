package com.kalah.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KalahGame {
	
	private static final int startingSeed = 6;
	
	private static final int houseNumber = 6;
	
	/**
	 * {{0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0}}
	 */
	private int[] houses;
	
	private KalahPlayer playerA;
	
	private KalahPlayer playerB;
	
	private String status;
	
	public static KalahGame init() {
		KalahGame game = KalahGame.kalahBuilder()
						.setHouses()
						.setPlayerA()
						.setPlayerB()
						.setStatus(GameStatus.PLAYER_A_TURN);
		
		return game;
	}
	
	public KalahGame setStatus(GameStatus status) {
		this.status = status.name();
		return this;
	}
	
	private KalahGame setPlayerB() {
		this.playerB = new KalahPlayer("B");
		return this;
	}

	private KalahGame setPlayerA() {
		this.playerA = new KalahPlayer("A");
		return this;
	}

	private KalahGame setHouses() {
		this.houses = new int[2 * houseNumber + 2];
		
		Arrays.fill(this.houses, startingSeed);
		
		this.houses[0] = this.houses[13] = 0;
		
		return this;
	}
	
	private static KalahGame kalahBuilder() {
		return new KalahGame();
	}
}