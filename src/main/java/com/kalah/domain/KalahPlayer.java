package com.kalah.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KalahPlayer {
	
	private String name;
	
	public KalahPlayer() {}

	public KalahPlayer(String label) {
		this.name = label;
	}

}
