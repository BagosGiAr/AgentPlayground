
package Playground.Agents;

import Playground.Playground;

/**
 * An Example of an Agent Implementation 
 * Implementing it own Life form.
 * 
 * An Agent Implementation could also Inherit//implement Graphic Analyzing and 
 * representation interface.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 *
 */
public class Worm extends Agent
{
	/**
	 * 
	 * @param name
	 */
    public Worm(String name)
    {
        super(name);
    }

	/**
	 * @see #Worm(String)
	 * @param name
	 * @param p
	 * @param energy
	 * @param time
	 */
	public Worm( String name, Playground pGround, double strartingEnergy,
			double speed )
	{
		super( name, pGround, strartingEnergy, speed );
	}
}
