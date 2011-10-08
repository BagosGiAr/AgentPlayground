
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
public class ProkaryotesAgent extends Agent
{
	/**
	 * 
	 * @param name
	 */
    public ProkaryotesAgent(String name)
    {
        super(name);
    }
    /**
     * @see #Agent
     * @see #Agent( String name, Playground pGround, Chromosome chromo )
     * @param name
     * @param pGround
     * @param chromo
     */
	public ProkaryotesAgent( String name, Playground pGround, Chromosome chromo )
	{
		super(name, pGround, chromo);
	}
    
	/**
	 * 
	 * @see #Agent
	 * @see #Agent(String, Playground, double, double)
	 * @param name
	 *            Agent's Name
	 * @param pGround
	 *            It's Playground World
	 * @param strartingEnergy
	 *            It's Starting Energy Points
	 * @param speed
	 *            It's Clocking time
	 */
	public ProkaryotesAgent( String name, Playground pGround, double strartingEnergy,
			double speed )
	{
		super( name, pGround, strartingEnergy, speed );
	}
	
	
	/* (non-Javadoc)
	 * @see Playground.Agents.Agent#onCloning(Playground.Agents.AgentInterface)
	 */
	@Override
	public void onCloning( AgentInterface target )
	{
		// TODO Auto-generated method stub
		super.onCloning(target);
	}
	
	/* (non-Javadoc)
	 * @see Playground.Agents.Agent#onBreathing()
	 */
	@Override
	public void onBreathing( )
	{
		// TODO Auto-generated method stub
		super.onBreathing();
	}
	
	/* (non-Javadoc)
	 * @see Playground.Agents.Agent#onCommunicating(Playground.Agents.AgentInterface)
	 */
	@Override
	public void onCommunicating( AgentInterface target )
	{
		// TODO Auto-generated method stub
		super.onCommunicating(target);
	}
	
	/* (non-Javadoc)
	 * @see Playground.Agents.Agent#onCreate()
	 */
	@Override
	public void onCreate( )
	{
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	/* (non-Javadoc)
	 * @see Playground.Agents.Agent#onDeactivating()
	 */
	@Override
	public void onDeactivating( )
	{
		// TODO Auto-generated method stub
		super.onDeactivating();
	}
	
	/* (non-Javadoc)
	 * @see Playground.Agents.Agent#onDie()
	 */
	@Override
	public void onDie( )
	{
		// TODO Auto-generated method stub
		super.onDie();
	}
	
	/* (non-Javadoc)
	 * @see Playground.Agents.Agent#onRevive()
	 */
	@Override
	public void onRevive( )
	{
		// TODO Auto-generated method stub
		super.onRevive();
	}
	
	/* (non-Javadoc)
	 * @see Playground.Agents.Agent#onSerialize()
	 */
	@Override
	public void onSerialize( )
	{
		// TODO Auto-generated method stub
		super.onSerialize();
	}
	
}
