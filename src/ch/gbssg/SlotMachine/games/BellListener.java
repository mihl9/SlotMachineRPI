package ch.gbssg.SlotMachine.games;

import ch.gbssg.SlotMachine.helper.BarellRoll;

public interface BellListener {
	void notifyByStep(double step, BarellRoll roll);
}
