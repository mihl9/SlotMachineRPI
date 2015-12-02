package ch.gbssg.SlotMachine.controls;

import java.io.InputStream;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;

public class BarellImage extends Image {
	public BarellImage(InputStream is) {
		super(is);	
	}
	
	public BarellImage(String arg0) {
		super(arg0);
	}

	private DoubleProperty x = new SimpleDoubleProperty();
	private DoubleProperty y = new SimpleDoubleProperty();
	private BooleanProperty visible = new SimpleBooleanProperty();
	
	

	public DoubleProperty X(){
		return this.x;
	}
	
	public void setX(double x){
		this.x.set(x);
	}
	
	public double getX(){
		return this.x.get();
	}
	
	public DoubleProperty Y(){
		return this.y;
	}
	
	public void setY(double y){
		this.y.set(y);
	}
	
	public double getY(){
		return this.y.get();
	}
	
	public BooleanProperty visible(){
		return this.visible;
	}
	
	public void setVisible(boolean isVisible){
		this.visible.set(isVisible);
	}
	
	public boolean getVisible(){
		return this.visible.get();
	}
}
