
package Playground.Agents;

import Playground.BiDimensional;

/**
 * The AgentInterface class defines the agent interface. An instance of this 
 * class envelopes an agent and provides access to it via a well-defined 
 * interface. It is also the primary conduit for communication between agents.

 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 *
 */
public interface AgentInterface extends BiDimensional
{
	/**
	 * Called on Creation//initiation operation
	 */
	void onCreate();
	
	/**
	 * Called on Initiation finalization and on each breath of the agent
	 */
	void onBreathing();
	
	/**
	 * Called on die response
	 */
	void onDie();
	
	/**
	 * Called on Deactivating//Pause Signal
	 */
	void onDeactivating();
	
	/**
	 * Called on Revive Signal
	 */
	void onRevive();
	
	/**
	 * Called on Cloning//reproduction procedure
	 */
	void onCloning(AgentInterface target );
	
	/**
	 * Called on Connection//Communication act
	 */
	void onCommunicating(AgentInterface target);
	
	/**
	 * Called on Serialization procedure
	 */
	void onSerialize();
}
