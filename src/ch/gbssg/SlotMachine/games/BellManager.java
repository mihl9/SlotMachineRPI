package ch.gbssg.SlotMachine.games;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.gbssg.SlotMachine.helper.BarellRoll;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class BellManager implements BellListener {
	private ArrayList<BarellRoll> rolles;
	private int lastStartedRoll;
	private int startNextRoll;
	public boolean isInit;
	private BellManagerListener listener;
	
	public BellManager(Canvas bell1, Canvas bell2, Canvas bell3, BellManagerListener listener) {
		this.listener = listener;
		Map<String, Image> icons = new HashMap<String, Image>();
		icons.put("7", new Image("/7.png"));
		icons.put("banana", new Image("/banana.png"));
		icons.put("citron", new Image("/citron.png"));
		icons.put("grape", new Image("/grape.png"));
		icons.put("melon", new Image("/melon.png"));

		startNextRoll = 800;
		rolles = new ArrayList<>();
		lastStartedRoll = 0;
		
		BarellRoll helper1 = new BarellRoll(bell1, icons);
		helper1.setSelectedIcon("7");
		helper1.start();
		rolles.add(helper1);
		
		BarellRoll helper2 = new BarellRoll(bell2, icons);
		helper2.setSelectedIcon("7");
		helper2.start();
		rolles.add(helper2);
		
		BarellRoll helper3 = new BarellRoll(bell3, icons);
		helper3.setSelectedIcon("7");
		helper3.start();
		rolles.add(helper3);
	}
	
	public boolean isFinish() {
		boolean isFinish = true;
		for (BarellRoll barellRoll : rolles) {
			isFinish &= barellRoll.isFinish;
		}
		return isFinish;
	}

	
	private int calcPoints(){
		int result = 0;
		boolean isEquals = true;
		String bufferIcon = rolles.get(0).getSelectedIcon();
		for (BarellRoll barellRoll : rolles) {
			if (bufferIcon == barellRoll.getSelectedIcon()) {
				isEquals &= true;
			} else {
				isEquals = false;
			}
		}
		
		if(isEquals) {
			if(bufferIcon == "7"){
				result = 1000;
			}else if(bufferIcon == "banana"){
				result = 800;
			}else if(bufferIcon == "citron"){
				result = 600;
			}else if(bufferIcon == "grape"){
				result = 400;
			}else if(bufferIcon == "melon"){
				result = 200;
			}
		}
		
		return result;
	}
	
	public void init() {
		for (int i = 0; i < rolles.size();) {
			if (rolles.get(i).isFinish) {
				rolles.get(i).registerListener(this);	
				i++;
			}
		}
		isInit = true;
	}
	
	public void start() {
			lastStartedRoll = 0;
			rolles.get(lastStartedRoll).doRoll();
	}
	
	private boolean lastRollFinish = false;
	
	@Override
	public void notifyByStep(double step, BarellRoll roll) {
		if (step == startNextRoll) {
			lastStartedRoll++;
			if (lastStartedRoll < rolles.size()) {
				rolles.get(lastStartedRoll).doRoll();	
			} else {
				lastRollFinish = true;
			}
		}
		
		if (isFinish() && lastRollFinish) {
			lastRollFinish = false;
			listener.isRoundIsFinish(calcPoints());
		}
	}
}
