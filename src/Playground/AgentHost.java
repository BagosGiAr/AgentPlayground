
package Playground;

import java.util.Vector;

import Playground.Playground.MoveListener;

/**
 * The AgentHost class defines the agent host. An instance of this class keeps 
 * track of every agent executing in the system. It works with other hosts in 
 * order to transfer agents.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 *
 */
public abstract class AgentHost
{
	/**
	 * A vector that Holds all BiDimentional objects running in the playground. A vector of pointer for all Playground's BiDimensional Objects
	 * @uml.property  name="biDsObjects"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="Playground.BiDimensional"
	 */
    public Vector<BiDimensional> biDsObjects;
    /**
	 * A listener that is called for each Movement, taking pace in the PLayGround
	 * @uml.property  name="moveListener"
	 * @uml.associationEnd  
	 */
    public MoveListener moveListener;
	
	
	
	
	
}
