/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tictactoeclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.time.Clock;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Administrator
 */
public class TicTacToe extends Application {
    
    public int rows;
    public int columns;
    public Label info = new Label("");
    public int id;
    public int click = 1;
    
    public boolean started = false;
    
    public Button[][] buttons = null;
    @Override
    public void start(Stage stage) {
        
        
        
        stage.setMinWidth(1000);stage.setMaxWidth(1000);
        stage.setMinHeight(500);stage.setMaxHeight(500);
        
        VBox main = new VBox();
        main.setMinSize(1000, 500);
        main.setMaxSize(1000, 500);
        main.setStyle("-fx-background-color:gray;");
        main.setAlignment(Pos.CENTER);
        
        VBox feld = new VBox();
        //feld.autosize();
        feld.setAlignment(Pos.CENTER);
        
        rows = columns = 3;
        buttons = new Button[rows][columns];
        
                Button start = new Button("Spiel Starten");
        
                
                start.setStyle("-fx-background-color:transparent;-fx-text-fill:cyan;");
                start.setFont(new Font("Arial",18));
                
                
                
                
                             start.setOnAction(new EventHandler<ActionEvent>() {
                       
            @Override
            public void handle(ActionEvent event) {
                ((Button) event.getSource()).setVisible(false);
                final Button tmp = ((Button)event.getSource());
                new Thread(new Runnable() {public void run() {
                boolean worked = spielStarten();
                if(worked) {
                started = true;
                }else {
                    
                   Platform.runLater(new Runnable() {
                       public void run() {
                    tmp.setVisible(true);
                   }
                   });
                }
            }
            }).start();
                
            }
        });
                             
        main.getChildren().add(start);
        
        for(int a = 0; a < rows; a++) {
            HBox h = new HBox();
            h.setAlignment(Pos.CENTER);
            for(int b = 0; b < columns; b++) {
                Button m = new Button(" ");
                m.setMinSize(50,50);
                m.setMaxSize(50,50);
                m.setFont(new Font("Arial",22));
                m.setStyle("-fx-background-color:transparent; -fx-text-fill:white;");
                
           m.setId(a+","+b);
            
            //System.out.println(a+"."+b);
            
            h.getChildren().add(m);
            
            if(b<(columns-1)) {
            VBox line = new VBox();
            line.setMinSize(3,50);
            line.setMaxSize(3,50);
            line.setStyle("-fx-background-color:black;");
            h.getChildren().add(line);
;            }
            
                     m.setOnAction(new EventHandler<ActionEvent>() {
            final        
            @Override
            public void handle(ActionEvent event) {

                if(binichdran(id)) {
                
                
                int row = Integer.parseInt(((Button)event.getSource()).getId().split(",")[0]);
                
                int column = Integer.parseInt(((Button)event.getSource()).getId().split(",")[1]);
                
                
                // hier server daten senden...
                Client client = Client.create();
                
                
                   // WebResource webResource = client.resource("http://172.16.4.160:8080/tictactoe-server/turn?row=" + row + "&col=" + column + "&symbol=" + id);
WebResource webResource = client.resource("http://172.16.4.160:8080/tictactoe-server/get");
                    GameBoard gb = webResource.post(GameBoard.class);
                    
                    
                    /*if (response.getStatus() != 200) {
                        System.out.println("KEIN 200 ERROR");
                    }*/
                    
                    /*String resp = response.getEntity(String.class);
                    if(resp.equals("10")) {
                        if(id == 1) {
                            //gewonnen
                        }
                        else 
                        {
                    // verloren
                        }
                    }
                    else if(resp.equals("20")) {
                        if(id == 2) {
                            //gewonnen
                        }
                        else 
                        {
                    // verloren
                        }
                    }
                    else {
                    
                        
                        
                    }*/
                
                System.out.println("Zeile : "+row);
                System.out.println("Spalte : "+column);
                System.out.println("");
                
                }else {
                info.setText("Du bist nicht am Zug!");
                }
                
                
            }
        });
        
            
             buttons[a][b] = m;
            
            }
        
            feld.getChildren().add(h);
            if(a<(rows-1)) {
            VBox line = new VBox();
            line.setMinSize(50*columns,3);
            line.setMaxSize(50*columns,3);
            line.setStyle("-fx-background-color:black;");
            feld.getChildren().add(line);
;            }
        
        }
        
        main.getChildren().add(feld);
        
        info = new Label("");
        info.setFont(new Font("Arial",20));
        info.setTextFill(Color.CORAL);
        Label l1 = new Label("");
        main.getChildren().addAll(l1,info);
        
        
        
        Scene sc = new Scene(main);
        stage.setScene(sc);
        
        stage.show();
        
    }
    
    
    public boolean binichdran(int id) {
        boolean is = false;
    Client client = Client.create();
         WebResource webResource = client.resource("http://172.16.4.160:8080/tictactoe-server/currentplayer");

                    ClientResponse response = webResource.post(ClientResponse.class);
                    
                    
                    if (response.getStatus() != 200) {
                        System.out.println("KEIN 200 ERROR");
                    }
                    
                    String resp = response.getEntity(String.class);
    
                    int current = Integer.parseInt(resp);
                    if(current==id) {
                    is=true;
                    }
                    
                    return is;
    
    }
    
        public boolean spielStarten()
    {
        boolean work = false;
        try {

                    // create the REST-Client
                    Client client = Client.create();

                    WebResource webResource = client.resource("http://172.16.4.160:8080/tictactoe-server/start");

                    ClientResponse response = webResource.post(ClientResponse.class);

                    if (response.getStatus() != 200) {
                   work=false;
                    }
                    
                    String playerId = response.getEntity(String.class);

                   id = Integer.parseInt(playerId);
work=true;
            } catch (Exception e) {

             }
        
        return work;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }
    
}
