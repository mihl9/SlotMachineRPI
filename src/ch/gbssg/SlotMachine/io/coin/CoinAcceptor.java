/**
 * 
 * @modtime 17:56:30
 * @author pedrett
 */
package ch.gbssg.SlotMachine.io.coin;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 *  /// A demo window for count coins.
    /// 
    /// IMPORTANT: Coin Acceptor must be configured to:
    ///     0.50 CHf = 1 Tick
    ///     1.00 CHf = 2 Tick
    ///     2.00 CHf = 4 Tick
    ///     5.00 CHf = 10 Tick
    /// 
    ///     NC and Fast mode must be enabled.
 * @author pedrett
 * @version 1.0
 */
public class CoinAcceptor extends TimerTask implements GpioPinListenerDigital {
	private ArrayList<CoinAcceptorListener> listeners;
	private int tickCounter;
	private final int WAIT_FOR_NEXT_COIN = 1500;
	private volatile int timerHandler;
	
	
	public CoinAcceptor(Pin pin1, Pin pin2) {
		GpioController gpioController = GpioFactory.getInstance();
		
		GpioPinDigitalInput coinPin = gpioController.provisionDigitalInputPin(pin1);
		coinPin.addListener(this);
		
		Timer timer = new Timer();
		timer.schedule(this, 0, 1); // interval 1ms
	}
	
	
	public void registerListener(CoinAcceptorListener listener) {
		listeners.add(listener);
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg) {
		// TODO: check if correct
		if (arg.getState() == PinState.LOW) {
			tickCounter++;
			// reset waiting handler
			timerHandler = WAIT_FOR_NEXT_COIN;
		}
	}

	@Override
	public void run() {
		if (timerHandler > 0) {
			timerHandler--;
		}
		
		if ((timerHandler <= 0) && tickCounter > 0) {
			for (CoinAcceptorListener listener : listeners) {
				listener.receiveCoin(tickCounter / 2);
			}
			tickCounter = 0;
		}
	}
}
