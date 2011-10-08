
package Playground;

import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * A Time Dimension implementation. Implements the laws of the time and its flow
 * 
 * It Extends the Timer
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com 
 */
public class TimeDimention extends Timer
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Initiate the timer and sets a Listener
     * @param delay
     * @param listener
     */
    public TimeDimention(int delay, ActionListener listener)
    {
        super(delay, listener);
    }

    /**
     * This Method should be Overwritten by Subclasses, Otherwise it just fire
     * the Time.
     */
    public void execute()
    {
        start();
    }
}
