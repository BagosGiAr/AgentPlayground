
package Playground.Agents;


/**
 * A throwable class that prompts of an Agent's death. Each death of an Agent 
 * Throws this class. As this class handles to stop its life.
 * 
 *  Stops Agent's Timer, Thread and deletes it.
 *  @see #mow(Agent, Thread, Playground.Agents.Agent.AgentTimer)
 *
 * @author Pappas Evangelos - papas.evagelos@gmail.com ( BagosGiAr )
 */
class AgentDeath extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * A Class that Kills an Agent, its Thread and Timer
     * @param agent
     * @param thread
     * @param timer
     * @throws InterruptedException
     */
    public AgentDeath(Agent agent, Thread thread, Agent.AgentTimer timer) throws InterruptedException
    {
        super();
         mow( agent,  thread,  timer);
    }

    /*
     * Death, Kills Agent.
     * 
     * Stopping its Life Thread and Time float.
     */
    @SuppressWarnings( "static-access" )
    public void mow(Agent agent, Thread thread, Agent.AgentTimer aTimer) throws InterruptedException
    {
        //Stop the timer, Agent Halts
        aTimer.stop();
        //pause thread
        thread.yield();
        //Waits 1sec to kill thread, Agent's Death
        thread.join(1000);
        
    }
}
