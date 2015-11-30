/**
 * 
 * @modtime 17:54:52
 * @author pedrett
 */
package ch.gbssg.SlotMachine.io.motor;

/**
 * @author pedrett
 * @version 1.0
 */
public interface MotorDriverIfc {
	
	/**
	 * Open door for coin
	 */
	public void Open() ;
	
	/**
	 * Close door for coin
	 */
	public void Close();
}
