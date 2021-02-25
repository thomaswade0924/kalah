package com.kalah.service;

import com.kalah.domain.GameMove;
import com.kalah.domain.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameService {

	public static void play(GameMove move) {
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_A_WIN.name())
			|| move.getGame().getStatus().equals(GameStatus.PLAYER_B_WIN.name())) {
			log.info("Game finished. ");
		} else if (isValidMove(move)) {
			playMove(move);
		} else {
			log.info("Invalid move. ");
		}
	}
	
	private static void playMove(GameMove move) {
		int seed = clearCurrentHouse(move);
		int index = move.getIndex();
		
		while (seed > 0) {
			index = findNextIndex(index);
			
			if (!shouldDrop(move, index)) {
				continue;
			}
			
			move.getGame().getHouses()[index]++;
			
			seed--;
		}
		
		if (shouldCaptureSeed(move, index)) {
			captureSeed(move, index);
		}
		
		if (!isOwnStore(move, index)) {
			changeTurn(move);
		}
		
		checkStatus(move);
	}

	private static void checkStatus(GameMove move) {
		int playerASeeds = 0, playerBSeeds = 0;
		
		for (int i = 1; i < 7; i++) {
			playerASeeds += move.getGame().getHouses()[i];
		}
		for (int i = 7; i < 13; i++) {
			playerBSeeds += move.getGame().getHouses()[i];
		}
		
		if (playerASeeds == 0 || playerBSeeds == 0) {
			for (int i = 7; i < 13; i++) {
				move.getGame().getHouses()[13] += move.getGame().getHouses()[i];
				move.getGame().getHouses()[i] = 0;
			}
			for (int i = 1; i < 7; i++) {
				move.getGame().getHouses()[0] += move.getGame().getHouses()[i];
				move.getGame().getHouses()[i] = 0;
			}
			endGame(move);
		}
	}

	private static void endGame(GameMove move) {
		if (move.getGame().getHouses()[0] > move.getGame().getHouses()[13]) {
			log.info("Player A wins! ");
			move.getGame().setStatus(GameStatus.PLAYER_A_WIN);
		} else if (move.getGame().getHouses()[0] < move.getGame().getHouses()[13]) {
			log.info("Player B wins! ");
			move.getGame().setStatus(GameStatus.PLAYER_B_WIN);
		} else {
			log.info("Draw! ");
			move.getGame().setStatus(GameStatus.DRAW);
		}
	}

	private static void changeTurn(GameMove move) {
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_A_TURN.name())) {
			move.getGame().setStatus(GameStatus.PLAYER_B_TURN);
		} else if (move.getGame().getStatus().equals(GameStatus.PLAYER_B_TURN.name())) {
			move.getGame().setStatus(GameStatus.PLAYER_A_TURN);
		}
	}

	private static boolean isOwnStore(GameMove move, int index) {
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_A_TURN.name()) && index == 0) {
			return true;
		}
		
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_B_TURN.name()) && index == 13) {
			return true;
		}
		
		return false;
	}

	private static void captureSeed(GameMove move, int index) {
		int seedToCapture = 1 + move.getGame().getHouses()[findOpposite(index)];
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_A_TURN.name())) {
			move.getGame().getHouses()[0] += seedToCapture;
		} else {
			move.getGame().getHouses()[13] += seedToCapture;
		}
		move.getGame().getHouses()[index] = 0;
		move.getGame().getHouses()[findOpposite(index)] = 0;
	}

	private static boolean shouldCaptureSeed(GameMove move, int index) {
		if (move.getGame().getHouses()[index] == 1 
			&& move.getGame().getHouses()[findOpposite(index)] != 0
			&& ((0 < index && index < 7 && move.getGame().getStatus().equals(GameStatus.PLAYER_A_TURN.name())) 
				|| (6 < index && index < 13 && move.getGame().getStatus().equals(GameStatus.PLAYER_B_TURN.name())))) {
			return true;
		}
		
		return false;
	}

	private static int findOpposite(int index) {
		return index < 7 ? index + 6 : index - 6;
	}

	private static int findNextIndex(int index) {
		if (index == 0) {
			return 7;
		} else if (index == 13) {
			return 6;
		} else if (0 < index && index < 7) {
			return index - 1;
		} else if (6 < index && index < 13) {
			return index + 1;
		} else {
			log.error("Wrong index. ");
			return 0;
		}
	}

	private static boolean shouldDrop(GameMove move, int index) {
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_A_TURN.name()) && index == 13) {
			return false;
		}
		
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_B_TURN.name()) && index == 0) {
			return false;
		}
		
		return true;
	}

	private static int clearCurrentHouse(GameMove move) {
		int seed = move.getGame().getHouses()[move.getIndex()];
		move.getGame().getHouses()[move.getIndex()] = 0;
		return seed;
	}

	private static boolean isValidMove(GameMove move) {
		if (move.getIndex() == 0 || move.getIndex() == 13) {
			return false;
		}
		
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_A_TURN.name())) {
			return 1 <= move.getIndex() && move.getIndex() <= 6;
		}
		
		if (move.getGame().getStatus().equals(GameStatus.PLAYER_B_TURN.name())) {
			return 7 <= move.getIndex() && move.getIndex() <= 12;
		}
		
		return false;
	}
}
