package ch.gbssg.SlotMachine;

import com.pi4j.io.gpio.RaspiPin;

import ch.gbssg.SlotMachine.games.LibertyBell;
import ch.gbssg.SlotMachine.io.coin.CoinAcceptor;
import ch.gbssg.SlotMachine.io.motor.MotorDriver;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
        primaryStage.getScene().setCursor(Cursor.NONE);
        primaryStage.show();
        System.out.println("Started");
	}
}
