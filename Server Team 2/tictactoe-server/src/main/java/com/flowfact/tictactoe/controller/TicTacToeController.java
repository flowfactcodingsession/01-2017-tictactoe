package com.flowfact.tictactoe.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flowfact.tictactoe.game.GameBoard;
import com.flowfact.tictactoe.game.GameLogic;

@Controller
public class TicTacToeController {
	int lastPlayer = 2;
	int playerCount = 0;
	
	public GameBoard board;
	public GameLogic logic;
	
	//public TicTacToeController() {}

	@RequestMapping(path = "start", method = RequestMethod.POST)
	public ResponseEntity<Integer> start() {
		board = new GameBoard();
		logic = new GameLogic();
		
		if (playerCount < 2)
		{
			playerCount++;
			return new ResponseEntity<Integer>(playerCount, HttpStatus.OK);
		}
		
		return new ResponseEntity<Integer>(444, HttpStatus.INTERNAL_SERVER_ERROR); //444 = game full
	}
	
	@RequestMapping(path = "restart", method = RequestMethod.POST)
	public int reStart() { return 0;}
	
	@RequestMapping(path = "turn", method = RequestMethod.POST)
	public ResponseEntity<String> turn(@PathParam(value = "row") int row, @PathParam(value = "col") int col, @PathParam(value = "symbol") int symbol) 
	{	    
	    if(board.getBoard()[row][col] != 0) 
	    {
	    	return new ResponseEntity<String>("FAILED - Already in use", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    try {
	    	if (lastPlayer != symbol)
	    	{
				board.setBoard(row, col, symbol);
				lastPlayer = logic.changePlayer(lastPlayer);
			}else{
				return new ResponseEntity<String>("FAILED - not your turn", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getStackTrace().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	    int iState = logic.checkWin(board.getBoard());
	    switch(iState){
	    	case 1:
	    		board = new GameBoard();
		    	return new ResponseEntity<String>("WIN - Player x", HttpStatus.OK);
	    	case 2:
	    		board = new GameBoard();
		    	return new ResponseEntity<String>("WIN - Player o", HttpStatus.OK);
	    	case 3:
	    		board = new GameBoard();
		    	return new ResponseEntity<String>("DRAW", HttpStatus.OK);
		    default:
		    	return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	    }
	}
	
	@RequestMapping(path = "get", method = RequestMethod.POST)
	public ResponseEntity<GameBoard> sendBoard() {
		return new ResponseEntity<GameBoard>(board, HttpStatus.OK);
	}
	
	@RequestMapping(path = "currentPlayer", method = RequestMethod.POST)
	public ResponseEntity<Integer> sendCurrentPlayer() {
		
		if(logic.checkPlayTime()){
			return new ResponseEntity<Integer>(lastPlayer, HttpStatus.OK);
		}
		
		return new ResponseEntity<Integer>((logic.changePlayer(lastPlayer)*10), HttpStatus.OK);
		
	}
	

}
