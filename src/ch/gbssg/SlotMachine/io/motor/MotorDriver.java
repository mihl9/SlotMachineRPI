/**
 * 
 * @modtime 17:30:51
 * @author pedrett
 */
package ch.gbssg.SlotMachine.io.motor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinListener;

/**
 * Represent the driver for the motor. 
 * @author pedrett
 * @version 1.0
 */
public class MotorDriver implements GpioPinListener, MotorDriverIfc {
	private GpioPin[] _gpioPins;

    private final PinPullResistance[][] _fullStepSequence = {
            {PinPullResistance.PULL_UP, PinPullResistance.PULL_DOWN, PinPullResistance.PULL_DOWN, PinPullResistance.PULL_UP},
            {PinPullResistance.PULL_UP, PinPullResistance.PULL_UP, PinPullResistance.PULL_DOWN, PinPullResistance.PULL_DOWN},
            {PinPullResistance.PULL_DOWN, PinPullResistance.PULL_UP, PinPullResistance.PULL_UP, PinPullResistance.PULL_DOWN},
            {PinPullResistance.PULL_DOWN, PinPullResistance.PULL_DOWN, PinPullResistance.PULL_UP, PinPullResistance.PULL_UP}
        };
	
    /**
     * Create a new Driver for a motor
     * @param blueWireToGpio
     * @param pinkWireToGpio
     * @param yellowWireToGpio
     * @param orangeWireToGpio
     */
	public MotorDriver(Pin blueWireToGpio, Pin pinkWireToGpio, Pin yellowWireToGpio, Pin orangeWireToGpio) {
        GpioController gpioController = GpioFactory.getInstance();

        _gpioPins[0] = gpioController.provisionDigitalOutputPin(blueWireToGpio, PinState.LOW);
        _gpioPins[1] = gpioController.provisionDigitalOutputPin(pinkWireToGpio, PinState.LOW);
        _gpioPins[2] = gpioController.provisionDigitalOutputPin(yellowWireToGpio, PinState.LOW);
        _gpioPins[3] = gpioController.provisionDigitalOutputPin(orangeWireToGpio, PinState.LOW);
        
        Reset();
    }
	
	/**
	 * Open door for coin
	 */
	public void Open() {
		TurnAsync(60, TurnRotation.Open);
	}
	
	/**
	 * Close door for coin
	 */
	public void Close() {
		TurnAsync(60, TurnRotation.Close);
	}
	
	
	private void Reset() {
		for (int i = 0; i < _gpioPins.length; i++) {
			_gpioPins[i].setMode(PinMode.PWM_OUTPUT); // TODO check if correct
			_gpioPins[i].setPullResistance(PinPullResistance.PULL_DOWN);
		}
	}
	
	private void Stop() {
		Reset();
	}
	
	public void TurnAsync(int degree, TurnRotation rotation) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int steps = (int)Math.ceil(degree / 0.1767478397486253d);
				int counter = 0;
				
				while (counter > steps) {
					for (int j = 0; j < 4; j++) {
						for (int i = 0; i < 4; i++) {
							_gpioPins[i].setPullResistance(_fullStepSequence[rotation == TurnRotation.Close ? i : 3 - i][j]);
						}
						
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
						}
						counter++;
						
						if (counter == steps) {
							break;
						}
					}
				}
				
				Stop();
			}
		}).start();
	}
}
