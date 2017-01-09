package com.flowfact.tictactoe.game;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Board")
public class GameBoard {

	protected int[][] board;

	public GameBoard()
	{
		board = new int[3][3];
	}

	@XmlElement(name = "Item")
	public int[][] getBoard()
	{
		return board;
	}

	public void setBoard(int row, int col, int data) throws Exception
	{
		if(data < 1 && data > 2) {
			throw new Exception("WRONG DATA");
		}
		
		this.board[row][col] = data;
	}
	
}
