package ch.gbssg.SlotMachine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.pi4j.io.gpio.RaspiPin;

import java.util.Map.Entry;

import ch.gbssg.SlotMachine.games.LibertyBell;
import ch.gbssg.SlotMachine.helper.BarellImage;
import ch.gbssg.SlotMachine.helper.BarellRoll;
import ch.gbssg.SlotMachine.io.coin.CoinAcceptor;
import ch.gbssg.SlotMachine.io.coin.CoinAcceptorListener;
import ch.gbssg.SlotMachine.io.motor.MotorDriver;
import ch.gbssg.SlotMachine.io.motor.TurnRotation;
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

public class Machine extends Application {
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
        this.coinAcceptor = new CoinAcceptor(RaspiPin.GPIO_05, RaspiPin.GPIO_25);
        this.motor = new MotorDriver(RaspiPin.GPIO_24, RaspiPin.GPIO_23, RaspiPin.GPIO_27, RaspiPin.GPIO_26);
        

        
		LibertyBell game = new LibertyBell(this.motor);
		this.coinAcceptor.registerListener(game);
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(game.getContent(), 300, 250));
        primaryStage.show();
        System.out.println("Started");
	}
}
