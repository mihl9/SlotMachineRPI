package ch.gbssg.SlotMachine.controls;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class BarellRoll extends VBox implements ChangeListener<String>{
	@FXML private Canvas canvas;
	
	private MapProperty<String, BarellImage> icons;
	private IntegerProperty animationLenght;
	private StringProperty selectedIcon;
	
	public BarellRoll(){
		this.icons = new SimpleMapProperty<String, BarellImage>();
		this.animationLenght = new SimpleIntegerProperty();
		this.selectedIcon = new SimpleStringProperty();
		this.icons.setValue(FXCollections.observableMap(new HashMap<String,BarellImage>()));
		
		this.addListeners();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BarellRoll.fxml"));
        //fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            final Node root = (Node)fxmlLoader.load();
            assert root == this;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
            }
        };
        
        timer.start();
	}
	
	public MapProperty<String, BarellImage> iconsProperty() {
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
	
	public StringProperty selectedIcon(){
		return this.selectedIcon;
	}
	
	public void setSelectedIcon(String key){
		this.selectedIcon.set(key);
	}
	
	public String getSelectedIcon(){
		return this.selectedIcon.get();
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
				int from = 0, 
						to = -1;
					List<String> keys = new ArrayList<String>(icons.keySet());
					for (int i = 0; i < keys.size(); i++) {
						if (keys.get(i)==oldValue){
							from = i;
						}
						if (keys.get(i)==newValue){
							to = i;
						}
					}

					if(from >= 0 && to >=0){
						doAnimation(from, to);
					}				
			}
		});
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		// TODO Auto-generated method stub
		
	}
	
	private void doAnimation(int from, int to){
		this.icons.get("ass").setX(0);
		this.icons.get("ass").setY(0);
		this.icons.get("ass").setVisible(true);
	}
	
	private void draw(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
        
		Iterator<Entry<String, BarellImage>> entries = this.icons.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry<String, BarellImage> thisEntry = (Entry<String, BarellImage>) entries.next();
		  String key = thisEntry.getKey();
		  BarellImage img = thisEntry.getValue();
		  if(img.getVisible()){
			  gc.drawImage(img, img.getX(), img.getY());
		  }
		}
	}
	
	
}
