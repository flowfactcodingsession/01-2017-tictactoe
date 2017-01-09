package com.flowfact.tictactoe.game;

import java.sql.Timestamp;

public class GameLogic{
	
	private long endTime;
	private long startTime;
	private int turns = 0;
	
	// Spielfeldgröße
	private int x = 3;
	private int y = 3;

	public GameLogic() {
		setTimestamps();
	}
	
	
	public void EndGame(){
		
	}
	
	public boolean turnAvailable(){
		if((turns + 1) == (x*y)){
			return false;
		}
		
		return true;
	}
	
	public int checkWin(int[][] board){
		if(!turnAvailable()){
			return 3;
		}
		
		for(int row = 0; row < 3; row++){
			// 1-3 | 4-6 | 7-9
			if(board[row][0] == 1 && board[row][1] == 1 && board[row][2] == 1){
				return 1;
			}
			if(board[row][0] == 2 && board[row][1] == 2 && board[row][2] == 2){
				return 2;
			}
		}
		
		for(int col = 0; col < 3; col++){
			// 1,4,7 | 2,5,8 | 3,6,9
			if(board[0][col] == 1 && board[1][col] == 1 && board[2][col] == 1){
				return 1;
			}
			if(board[0][col] == 2 && board[1][col] == 2 && board[2][col] == 2){
				return 2;
			}
		}
		
		for(int dia = 1; dia < 3; dia++){
			// 1,5,9 | 3,5,7
			if(board[0][0] == dia && board[1][1] == dia && board[2][2] == dia){
				return 1;
			}
			
			if(board[0][2] == dia && board[1][1] == dia && board[2][0] == dia){
				return 2;
			}
		}
		
		return 0;
	}
	
	public void setTimestamps(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		startTime = timestamp.getTime();
		endTime = timestamp.getTime() + 30;
	}
	
	public boolean checkPlayTime(){
		if(endTime >= startTime){
			return false;
		}
		
		return true;
	}
	
	public int changePlayer(int player){
		switch (player)
		{
			case 2:
				return 1;
			default:
				return 2;
		}
	}
	
}
