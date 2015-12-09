package ch.gbssg.SlotMachine.games;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.media.jfxmediaimpl.platform.Platform;

import ch.gbssg.SlotMachine.helper.BarellRoll;
import ch.gbssg.SlotMachine.io.coin.CoinAcceptorListener;
import ch.gbssg.SlotMachine.io.motor.MotorDriver;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LibertyBell implements CoinAcceptorListener{
	@FXML
	private AnchorPane root;
	
	@FXML
	private Label lbPoints;
	
	@FXML
	private Canvas roll1;
	@FXML
	private Canvas roll2;
	@FXML
	private Canvas roll3;
	
	private BarellRoll helper1;
	private BarellRoll helper2;
	private BarellRoll helper3;
	
	private int avRounds = 0;
	private int Points = 0;
	private boolean isRunning = false;
	private MotorDriver motor;
	public LibertyBell(MotorDriver motor)  {
		this.motor = motor;
		// load fxml from file and set controller
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LibertyBell.fxml"));
			loader.setController(this);
			root = (AnchorPane) loader.load();
			Map<String, Image> icons = new HashMap<String, Image>();
			icons.put("7", new Image("/7.png"));
			icons.put("banana", new Image("/banana.png"));
			icons.put("citron", new Image("/citron.png"));
			icons.put("grape", new Image("/grape.png"));
			icons.put("melon", new Image("/melon.png"));

			this.helper1 = new BarellRoll(this.roll1);
			this.helper1.iconsProperty().get().putAll(icons);
			
			
			this.helper1.setSelectedIcon("7");
			
			this.helper2 = new BarellRoll(this.roll2);
			this.helper2.iconsProperty().get().putAll(icons);
			
			this.helper2.setSelectedIcon("7");
			
			this.helper3 = new BarellRoll(this.roll3);
			this.helper3.iconsProperty().get().putAll(icons);
			
			this.helper3.setSelectedIcon("7");
			
			// bind new pane to parent pane
			AnchorPane.setTopAnchor(root, 0.0);
			AnchorPane.setBottomAnchor(root, 0.0);
			AnchorPane.setLeftAnchor(root, 0.0);
			AnchorPane.setRightAnchor(root, 0.0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.roll1.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				start();
			}
		});
	}
	
	public Pane getContent(){
		return this.root;
	}
	
	private Runnable start(){
		return new Runnable() {

			@Override
			public void run() {
				while(avRounds>0){
					helper1.doRoll();
					helper2.doRoll();
					helper3.doRoll();
					System.out.println("1: " + helper1.selectedIcon() + " 2: " + helper2.selectedIcon() + " 3: " + helper3.selectedIcon());
					Points += calcPoints(helper1.getSelectedIcon(), helper2.getSelectedIcon(), helper3.getSelectedIcon());
					lbPoints.setText(Points + " Punkte");
					
					avRounds--;
				}
			}
		};
	}
	
	private int calcPoints(String r1, String r2, String r3){
		int result = 0;
		if(r1==r2 && r1==r3){
			if(r1=="7"){
				result = 1000;
			}else if(r1=="banana"){
				result = 800;
			}else if(r1=="citron"){
				result = 600;
			}else if(r1=="grape"){
				result = 400;
			}else if(r1=="melon"){
				result = 200;
			}
		}
		
		return result;
	}
	
	@Override
	public void receiveCoin(float value) {
		this.avRounds = 3*((int) (value / 2));
		javafx.application.Platform.runLater(this.start());
	}
}
