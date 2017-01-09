package com.flowfact.tictactoe.game;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Board")
public class GameBoard {

	protected int[][] board;

	public GameBoard() {
		// 0 = leer
		// 1 = x
		// 2 = o
		board = new int[3][3];
	}

	@XmlElement(name = "Item")
	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int row, int col, int symbol) {
		board[row][col] = symbol;
	}
	
	

}
