package com.mycompany.tictactoeclient;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Board")
public class GameBoard {

	protected int[][] board;

	public GameBoard() {
		board = new int[3][3];
	}

	@XmlElement(name = "Item")
	public int[][] getBoard() {
		return board;
	}

}
