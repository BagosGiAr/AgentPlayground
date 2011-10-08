package Playground.Generators;

import Playground.Agents.Agent.Chromosome;
import Playground.Agents.ProkaryotesAgent;
import Playground.BiDimensional;
import Playground.Playground;

/**
 * An Agent Generator, A stand alone Thread from the ThreadGroup of Generators.
 * Generates a new Agent in the Given Playground.
 * 
 * It handles the Generation of each Agent in its space-time-playground
 * 
 * As in Each Generator, also this one is a Daemon. In case of playground's
 * Termination, Generators are also automated killed.
 * 
 * @see #AgentGenerator(Playground, String)
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 */
public class AgentGenerator extends Generator
{
	/**
	 * @uml.property  name="playground"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="agentGenerator:Playground.Playground"
	 */
	private Playground playground;
	/**
	 * @uml.property  name="agentName"
	 */
	private String agentName;
	public static int agentCount = 0;
	public static int generetionYear = 0;
	/**
	 * @uml.property  name="pendingGenerations"
	 */
	public int pendingGenerations;
	
	/**
	 * Generates a New Agent, at this ( p ) Playground with this ( agentN ) Name
	 * 
	 * @see #AgentGenerator(Playground)
	 * @param p
	 * @param agentN
	 */
	public AgentGenerator( Playground p, String agentN )
	{
		super(new Thread("AgentGenerator"));
		synchronized(this)
		{
			playground = p;
			agentName = agentN;
			pendingGenerations = 0;
		}
	}
	
	/**
	 * Generates a New Agent, at this ( p ) Playground
	 * 
	 * @see #AgentGenerator(Playground, String)
	 * @param p
	 */
	public AgentGenerator( Playground p )
	{
		this(p, "DummyAgent");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see Playground.Generators.Generator#generate()
	 */
	@Override
	synchronized public boolean generate( )
	{
		playground.addNewGeneratedObject(new ProkaryotesAgent(agentName + "_"
				+ ++agentCount, playground, 1000, 1000) );
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see Playground.Generators.Generator#generate(Playground.BiDimensional)
	 */
	@Override
	synchronized public boolean generate( BiDimensional bd )
	{
		try
		{
			playground.addNewGeneratedObject(bd);
		}
		catch ( Exception e )
		{
			return false;
		}
		
		return true;
	}
	
	synchronized public boolean generate( Chromosome c )
	{
		try
		{
			playground.addNewGeneratedObject(new ProkaryotesAgent(agentName + "_"
					+ ++agentCount, playground, c) );
		}
		catch ( Exception e )
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Runs a new thread that Generates a new Agent in Random Coordinates in the
	 * Given PlayGround
	 */
	@Override
	synchronized public void run( )
	{
		start();
	}
	
	/**
	 * 
	 * A copy of {@link #generate(Chromosome))} function with a MultiThreading 
	 * Functionality
	 */
	synchronized public AgentGenerator run( Chromosome c )
	{
		try
		{
			run();
			generate(c);
		}
		catch (Exception e)
		{
			//Do Nothing
		}
		return this;
	}
	
	/**
	 * 
	 * A copy of {@link #generate(BiDimensional))} function with a 
	 * MultiThreading Functionality
	 */
	synchronized public AgentGenerator run( BiDimensional bd )
	{
		try
		{
			run();
			generate(bd);
		}
		catch (Exception e)
		{
			//Do Nothing
		}
		return this;
	}
}
