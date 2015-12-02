package ch.gbssg.SlotMachine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ch.gbssg.SlotMachine.controls.BarellImage;
import ch.gbssg.SlotMachine.controls.BarellRoll;
import ch.gbssg.SlotMachine.games.LibertyBell;
import ch.gbssg.SlotMachine.io.coin.CoinAcceptor;
import ch.gbssg.SlotMachine.io.coin.CoinAcceptorListener;
import ch.gbssg.SlotMachine.io.motor.MotorDriver;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Machine extends Application implements CoinAcceptorListener{
	private TextField txt;
	private CoinAcceptor coinAcceptor;
	private MotorDriver motor;
	
	public static void main(String[] args) {		
		launch(args);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hello World!");        
		//roll.iconsProperty().get().put("ass", new BarellImage("/7.png"));
        //this.coinAcceptor = new CoinAcceptor(RaspiPin.GPIO_24, RaspiPin.GPIO_26);
        //this.motor = new MotorDriver(RaspiPin.GPIO_19, RaspiPin.GPIO_13, RaspiPin.GPIO_16, RaspiPin.GPIO_12);
        
        //this.motor.TurnAsync(5, TurnRotation.Close);
        //this.coinAcceptor.registerListener(this);
        
		LibertyBell game = new LibertyBell();

        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(game.getContent(), 300, 250));
        primaryStage.show();
	}

	@Override
	public void receiveCoin(int value) {
		// TODO Auto-generated method stub
		System.out.println(value);
		this.txt.setText(this.txt.getText()+value);
	}
}
