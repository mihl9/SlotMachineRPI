package ch.gbssg.SlotMachine.games;

import java.io.IOException;

import ch.gbssg.SlotMachine.io.coin.CoinAcceptorListener;
import ch.gbssg.SlotMachine.io.motor.MotorDriver;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LibertyBell implements CoinAcceptorListener, BellManagerListener {
	@FXML
	private AnchorPane root;
	
	@FXML
	private Label lbPoints;
	@FXML
	private Label lbTimer;
	@FXML
	private Label lbRounds;
	@FXML
	private Pane rules;
	@FXML
	private Canvas roll1;
	@FXML
	private Canvas roll2;
	@FXML
	private Canvas roll3;

	private BellManager manger;
	
	private int avRounds = 0;
	private float totalCoins = 0;
	private int Points = 0;
	private MotorDriver motor;
	public LibertyBell(MotorDriver motor)  {
		this.motor = motor;
		// load fxml from file and set controller
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LibertyBell.fxml"));
			loader.setController(this);
			root = (AnchorPane) loader.load();
			root.setCursor(Cursor.NONE);
			
			// bind new pane to parent pane
			AnchorPane.setTopAnchor(root, 0.0);
			AnchorPane.setBottomAnchor(root, 0.0);
			AnchorPane.setLeftAnchor(root, 0.0);
			AnchorPane.setRightAnchor(root, 0.0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BackgroundImage myBI= new BackgroundImage(new Image("/wood-Background.jpg", root.getWidth(), root.getHeight(), false, true),
		       BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		//set background
		root.setBackground(new Background(myBI));
		
		BackgroundImage myRulesBI= new BackgroundImage(new Image("/board.png", rules.getWidth(), rules.getHeight(), false, true),
			       BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
		//set background
		rules.setBackground(new Background(myRulesBI));
		manger = new BellManager(roll1, roll2, roll3, this);
		
	}
	
	public Pane getContent(){
		return this.root;
	}
	
	private Runnable start(){
		return new Runnable() {

			@Override
			public void run() {		
				lbRounds.setText("Runden: " + avRounds);
				lbPoints.setText("Punkte: " + Points);
				
				if (!manger.isInit){
					manger.init();	
				}
				manger.start();
			}
			
		};
	}

	
	@Override
	public void receiveCoin(float value) {	
		totalCoins += value;
		
		if (avRounds <= 0) {
			this.avRounds = 3*((int) (value * 2f));
			javafx.application.Platform.runLater(this.start());	
		} else {
			System.out.println("Kannst nur spielen wenn game fertig ist!");
		}
	}
	

	@Override
	public void timerHandlerChanged(int seconds) {
		javafx.application.Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				lbTimer.setText("Spiel startet in " + seconds + " s");
				if (seconds == 0) {
					lbTimer.setVisible(false);
				} else {
					if(rules.getOpacity()==1.0){
						hideRules();
					}
					lbTimer.setVisible(true);
				}
			}
		});
	}

	
	@Override
	public void isRoundIsFinish(int wonPoints) {
		Points += wonPoints;
		avRounds--;
		
		lbRounds.setText("Runden: " + avRounds);
		lbPoints.setText("Punkte: " + Points);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (avRounds > 0) {		
			javafx.application.Platform.runLater(this.start());
		} else if(avRounds == 0) {
			finishGame();
		}
	}
	
	private void finishGame() {
		if (Points >= 1000) {
			motor.Open();
			motor.Close();
			
			totalCoins = 0;
		}
		showRules();
		Points = 0;
	}
	
	private void showRules(){
		FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), this.rules);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);
		fadeIn.play();
	}
	
	private void hideRules(){
		FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), this.rules);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.play();
	}
}
