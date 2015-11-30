/**
 * 
 * @modtime 17:56:30
 * @author pedrett
 */
package ch.gbssg.SlotMachine.io.coin;

import java.util.ArrayList;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.Pin;

/**
 * @author pedrett
 * @version 1.0
 */
public class CoinAcceptor {
	public ArrayList<CoinAcceptorListener> listeners;
	
	public CoinAcceptor(Pin pin1, Pin pin2) {
		GpioController gpioController = GpioFactory.getInstance();
		
		GpioPinAnalogInput coinPin = gpioController.provisionAnalogInputPin(pin1);
		// TODO
	}
	
	public void registerListener(CoinAcceptorListener listener) {
		listeners.add(listener);
	}
}
