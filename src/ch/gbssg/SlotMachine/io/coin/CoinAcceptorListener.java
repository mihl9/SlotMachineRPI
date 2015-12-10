/**
 * 
 * @modtime 17:55:41
 * @author pedrett
 */
package ch.gbssg.SlotMachine.io.coin;

/**
 * @author pedrett
 * @version 1.0
 */
public interface CoinAcceptorListener {
	/**
	 * fire if coin acceptor detected a coin
	 * @param value
	 */
	void receiveCoin(float value);
	
	void timerHandlerChanged(int seconds);
}
