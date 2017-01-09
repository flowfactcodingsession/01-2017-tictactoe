package com.flowfact.tictactoe.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flowfact.tictactoe.game.Game;
import com.flowfact.tictactoe.game.GameBoard;

@Controller
public class TicTacToeController
{
	//Game game1 = new Game();
	int iState;
	boolean gameOver = false;
	GameBoard board;
	int lastPlayer = 2;
	int playerCount = 0;
	int currentPlayer =0;
	int turn = 0;
	int x = 3;
	int y = 3;
	
	
	@RequestMapping(path = "start", method = RequestMethod.POST)
	public ResponseEntity<Integer> start() 
	{
		if (playerCount < 2)
		{
			playerCount++;
			return new ResponseEntity<Integer>(playerCount, HttpStatus.OK);
		}	
		board = new GameBoard();
		return new ResponseEntity<Integer>(444, HttpStatus.INTERNAL_SERVER_ERROR); //444 = game full // TEST
	}
	
	@RequestMapping(path = "get", method = RequestMethod.POST)
	public ResponseEntity<int[][]> sendBoard() 
	{
		return new ResponseEntity<int[][]>(board.getBoard(), HttpStatus.OK); //444 = game full
	}
	
	@RequestMapping(path = "currentPlayer", method = RequestMethod.POST)
	public ResponseEntity<Integer> sendPlayer() 
	{
		if (lastPlayer == 1)
		{
			currentPlayer = 2;
		}
		if (lastPlayer == 2)
		{
			currentPlayer = 1;
		}
		return new ResponseEntity<Integer>(currentPlayer, HttpStatus.OK); //
	}
	
	@RequestMapping(path = "turn", method = RequestMethod.POST)
	public ResponseEntity<String> turn(@PathParam(value = "row") int row, @PathParam(value = "col") int col, @PathParam(value = "symbol") int symbol) 
	{
		System.out.println(row);
		System.out.println(col);
	    System.out.println(symbol);
	    
	    if(board.getBoard()[row][col] != 0) 
	    {
	    	return new ResponseEntity<String>("FAILED - Already in use", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    try
	    {
	    	if (lastPlayer != symbol)
	    	{
				board.setBoard(row, col, symbol);
				turn++;
				if (turn >= x*y)
				{
					gameOver = true;
				}
				
				switch (lastPlayer)
				{
					case 2:
						lastPlayer = 1;
						break;
					default:
						lastPlayer = 2;
						break;	
				}
				
			}
		}
	    catch (Exception e) 
	    {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getStackTrace().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    iState = checkWin(board.getBoard());
	    if (iState == 0 && gameOver)
	    {
	    	gameOver = false;
	    	board = new GameBoard();
	    	return new ResponseEntity<String>("draw", HttpStatus.OK);
		}
	    if (iState == 1)
	    {
	    	gameOver = false;
	    	board = new GameBoard();
	    	return new ResponseEntity<String>("player 1 wins", HttpStatus.OK);
		}
	    if (iState == 2)
	    {
	    	gameOver = false;
	    	board = new GameBoard();
	    	return new ResponseEntity<String>("player 2 wins", HttpStatus.OK);	    	
		}    
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	
	public int checkWin(int[][] board)
	{
		for(int x = 0; x < 3; x++)
		{
			// 1-3
			// 4-6
			// 7-9
			if(board[x][0] == 1 && board[x][1] == 1 && board[x][2] == 1)
			{
				return 1;
			}
			if(board[x][0] == 2 && board[x][1] == 2 && board[x][2] == 2)
			{
				return 2;
			}
		}
		
		for(int x = 0; x < 3; x++)
		{
			// 1,4,7
			// 2,5,8
			// 3,6,9
			if(board[0][x] == 1 && board[1][x] == 1 && board[2][x] == 1)
			{
				return 1;
			}
			if(board[0][x] == 2 && board[1][x] == 2 && board[2][x] == 2)
			{
				return 2;
			}
		}
		
		for(int x = 1; x < 3; x++)
		{
			// 1,5,9
			// 3,5,7
			if(board[0][0] == x && board[1][1] == x && board[2][2] == x)
			{
				return 1;
			}
			
			if(board[0][2] == x && board[1][1] == x && board[2][0] == x)
			{
				return 2;
			}
		}
		return 0;
	}
}
