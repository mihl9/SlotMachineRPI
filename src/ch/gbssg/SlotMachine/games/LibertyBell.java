package ch.gbssg.SlotMachine.games;

import java.io.IOException;

import ch.gbssg.SlotMachine.controls.BarellImage;
import ch.gbssg.SlotMachine.controls.BarellRoll;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LibertyBell {
	@FXML
	private AnchorPane root;
	
	@FXML
	private BarellRoll roll1;
	
	public LibertyBell() {
		// load fxml from file and set controller
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LibertyBell.fxml"));
			loader.setController(this);
			root = (AnchorPane) loader.load();
			
			BarellRoll roll = new BarellRoll();
			roll.iconsProperty().get().put("ass", new BarellImage("/7.png"));
			roll.setSelectedIcon("ass");

			root.getChildren().add(roll);
			// bind new pane to parent pane
			AnchorPane.setTopAnchor(root, 0.0);
			AnchorPane.setBottomAnchor(root, 0.0);
			AnchorPane.setLeftAnchor(root, 0.0);
			AnchorPane.setRightAnchor(root, 0.0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Pane getContent(){
		return this.root;
	}
}
