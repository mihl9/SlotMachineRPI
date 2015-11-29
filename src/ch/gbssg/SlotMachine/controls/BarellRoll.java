package ch.gbssg.SlotMachine.controls;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class BarellRoll extends AnchorPane implements ChangeListener<String>{
	@FXML private Canvas canvas;
	
	private MapProperty<String, Image> icons;
	private IntegerProperty animationLenght;
	private StringProperty selectedIcon;
	
	public BarellRoll(){
		this.icons = new SimpleMapProperty<String, Image>();
		this.animationLenght = new SimpleIntegerProperty();
		this.selectedIcon = new SimpleStringProperty();
		
		this.addListeners();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BarellRoll.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	public MapProperty<String, Image> iconsProperty() {
		return this.icons;
	}

	public IntegerProperty animationLenght(){
		return this.animationLenght;
	}
	
	public void setAnimationLenght(int lenght){
		this.animationLenght.set(lenght);
	}
	
	public int getAnimationLenght(){
		return this.animationLenght.get();
	}
	
	public void doRoll(){
		//this.icons.values().toArray();
		List<String> keys = new ArrayList<String>(this.icons.keySet());
		
		
		for (int i = 0; i < keys.size(); i++) {
		    Object obj = keys.get(i);
		    
		}
	}
	
	private void addListeners(){
		this.selectedIcon.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		// TODO Auto-generated method stub
		
	}
}
