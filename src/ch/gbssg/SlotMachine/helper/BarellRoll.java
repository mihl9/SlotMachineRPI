package ch.gbssg.SlotMachine.helper;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.gbssg.SlotMachine.games.BellListener;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;


public class BarellRoll  {
	private Canvas canvas;
	
	public volatile boolean isFinish;
	
	private ArrayList<BellListener> listeners;
	
	private MapProperty<String, Image> icons;
	private DoubleProperty animationLenght;
	private volatile StringProperty selectedIcon;
	private DoubleProperty animationSpeed;
	
	//animation vars
	private int pos = 0;
	private int next = 0;
	private double currentOffset = 0;
	private double pathDone = 0;
	private double animationPath = 0;
	private String oldValue;
	
	private BarellImage[] imageSettings = new BarellImage[0];
	
	public BarellRoll(Canvas canvas, Map<String, Image> images){
		this.listeners = new ArrayList<>();
		this.isFinish = false;
		this.canvas = canvas;
		this.icons = new SimpleMapProperty<String, Image>();
		this.animationLenght = new SimpleDoubleProperty();
		this.animationSpeed = new SimpleDoubleProperty();
		this.selectedIcon = new SimpleStringProperty();
		this.icons.setValue(FXCollections.observableMap(new HashMap<String,Image>()));
		this.iconsProperty().get().putAll(images);
		this.setAnimationLenght(3);
		this.setAnimationSpeed(20);
        
	  List<String> keys = new ArrayList<String>(icons.keySet());
		if(imageSettings.length!=keys.size()){
			this.imageSettings = new BarellImage[keys.size()];
			int i = 0;
			while( i < imageSettings.length){
				imageSettings[i] = new BarellImage();
				i++;
			}
		}
			
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
            }
        };
        
        timer.start();
	}
	
	public void registerListener(BellListener listener) {
		listeners.add(listener);
	}
	
	public MapProperty<String, Image> iconsProperty() {
		return this.icons;
	}

	public DoubleProperty animationLenght(){
		return this.animationLenght;
	}
	
	public void setAnimationLenght(double lenght){
		this.animationLenght.set(lenght);
	}
	
	public double getAnimationLenght(){
		return this.animationLenght.get();
	}
	
	public DoubleProperty animationSpeed(){
		return this.animationSpeed;
	}
	
	public void setAnimationSpeed(double speed){
		this.animationSpeed.set(speed);
	}
	
	public double getAnimationSpeed(){
		return this.animationSpeed.get();
	}
	
	public StringProperty selectedIcon(){
		return this.selectedIcon;
	}
	
	public void setSelectedIcon(String key){
		oldValue = this.selectedIcon.get();
		this.selectedIcon.set(key);
	}
	
	public String getSelectedIcon(){
		return this.selectedIcon.get();
	}
	
	public void doRoll() {
		//this.icons.values().toArray();
		List<String> keys = new ArrayList<String>(this.icons.keySet());
		
		int rand = (int) Math.floor(Math.random() * keys.size());
		setSelectedIcon(keys.get(rand));
		start();
	}

	
	public void start() {
		int from = 0, to = -1;
			List<String> keys = new ArrayList<String>(icons.keySet());
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i)==oldValue){
					from = i;
				}
				if (keys.get(i)==selectedIcon.get()){
					to = i;
				}
			}

			if(from >= 0 && to >=0){
				doAnimation(from, to);
			}			
	}
		
	private void doAnimation(int from, int to){
		List<String> keys = new ArrayList<String>(this.icons.keySet());
		
		this.imageSettings[from].setX(0);
		this.imageSettings[from].setY(0);
		this.imageSettings[from].setVisible(true);

		double animationPath = (this.canvas.getHeight()*(keys.size()))*this.getAnimationLenght();
		animationPath -= this.canvas.getHeight();
		if(from>to){
			animationPath += this.canvas.getHeight()*(((keys.size()-from)+to)+1);
		}else{
			animationPath += (this.canvas.getHeight()*((to+1) - from));
		}
		//System.out.println(animationPath);
		this.pos = from;
		this.next = pos+1;
		if(this.next>keys.size()-1){
			this.next = 0;
		}
		this.currentOffset = 0;
		this.pathDone = 0;
		this.animationPath = animationPath;
	}
	
	private void move(){
		List<String> keys = new ArrayList<String>(this.icons.keySet());
		if(pathDone < animationPath){
			isFinish = false;
			if(this.imageSettings[pos].getY()>=this.canvas.getHeight()){
				pathDone = pathDone - (this.imageSettings[pos].getY()-this.canvas.getHeight());
				this.imageSettings[pos].setX(0);
				this.imageSettings[pos].setY(0);
				this.imageSettings[pos].setVisible(false);
				currentOffset = 0;
				pos++;
				if(pos>keys.size()-1){
					pos = 0;
				}
				next = pos+1;
				if(next>keys.size()-1){
					next = 0;
				}
			}
			currentOffset += this.getAnimationSpeed();
			this.imageSettings[pos].setX(0);
			this.imageSettings[pos].setY(currentOffset);
			this.imageSettings[pos].setVisible(true);
			
			this.imageSettings[next].setX(0);
			this.imageSettings[next].setY((this.canvas.getHeight()*-1)+currentOffset);
			this.imageSettings[next].setVisible(true);
			pathDone += this.getAnimationSpeed();
		}else{
			isFinish = true;
		}
		
		for (BellListener listener : listeners) {
			listener.notifyByStep(pathDone, this);
		}
	}
	
	private void draw(){
		this.move();
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		List<String> keys = new ArrayList<String>(icons.keySet());
		for (int i = 0; i < keys.size(); i++) {
			if(this.imageSettings[i].getVisible()){
				gc.drawImage(this.icons.get(keys.get(i)), this.imageSettings[i].getX(), this.imageSettings[i].getY(), this.canvas.getWidth(), this.canvas.getHeight());
			}
		}
	}	
}
