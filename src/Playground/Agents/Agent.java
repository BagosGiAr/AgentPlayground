package Playground.Agents;

import Playground.AgentHost;
import Playground.BiDimensional;
import Playground.Playground;
import Playground.Resources.*;
import Playground.TimeDimention;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.EventObject;


/**
 * A Voluntary individual Bot, running in a 2D Playground, Having an Embryonic
 * Intelligence
 * Reacting with other Bots, and Object that seeks in its Playground.
 * 
 * By voluntary is meant that each Agent has its own Intelligence to react
 * Differently in any overcome situation.
 * 
 * This Agent instance may have different kind of forms as its vital properties
 * are parametric. Any kind of Agent-Form should inherit this class
 * and change as wish.
 * 
 * New Thread, Breathing and Acting with >1Hz
 * 
 * An Agent class is formed as abstract as it may not be considered a life form
 * in such general way. Additionally, this class should be inherit to provide
 * Basic life form to each new AgentLife Being.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 */
abstract public class Agent extends Object implements AgentInterface, Runnable,
		Walkable
{
	/**
	 * AgentTimer, provides the Agent its Time dimension interactivity. Thishow an agent may flows individual in the global timer dimension.
	 * @uml.property  name="aTimer"
	 * @uml.associationEnd  inverse="this$0:Playground.Agents.Agent$AgentTimer"
	 */
	private AgentTimer aTimer;
	/**
	 * This is Agent's Life thread, an Agent start its life by the fire of this Thread. As this thread flows this Agents continues to live and also thishow it gains its individuality. this lifeThread also holds Agent's Time dimension
	 * @uml.property  name="lifeThread"
	 */
	private Thread lifeThread;
	/**
	 * Agent's Name
	 * @uml.property  name="agentName"
	 */
	private String agentName;
	/**
	 * Where Agent is Staring At ( South, North etc )
	 * @uml.property  name="staringAt"
	 */
	private int staringAt;
	/**
	 * As the Agent is a being. It needs Energy to keep on living. As it has enough to interact it lives as it should, otherwise it dies. It's energy is being consumed by any of its actions.
	 * @uml.property  name="agentEnergy"
	 */
	private double agentEnergy;
	/**
	 * This is a switch value that provides to the Agent it own reaction time to its world.
	 * @uml.property  name="agentSpeed"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.util.ArrayList"
	 */
	private double agentSpeed;
	/**
	 * Agent's chromosome values. its statistics of its evolution.
	 * @uml.property  name="chromosome"
	 * @uml.associationEnd  inverse="this$0:Playground.Agents.Agent$Chromosome"
	 */
	private Chromosome chromosome;
	/**
	 * a Vector that keeps a history log of all the Consumed Resources by this Agent
	 * @uml.property  name="consumedResources"
	 */
	//private Vector<NutriceResource> consumedResources;
	private int consumedResources;
	/**
	 * A pointer of the agentHost that this Agent exists
	 * @uml.property  name="agentHost"
	 * @uml.associationEnd  
	 */
	private AgentHost agentHost;
	/**
	 * The Target that This Agent is now Watching//Reacting
	 * @uml.property  name="targetAgent"
	 * @uml.associationEnd  
	 */
	private Agent targetAgent;
	
	/**
	 * Agent knows it's own position of its agentHost
	 * @uml.property  name="currentPosition"
	 * @uml.associationEnd  
	 */
	private BiCoordinates currentPosition;
	
	/**
	 * A Voluntary individual Bot, running in a 2D Playground, Having an
	 * Embryonic Intelligence
	 * Reacting with other Bots, and Object that seeks in its Playground.
	 * 
	 * By voluntary is meant that each Agent has its own Intelligence to react
	 * Differently in any overcome situation.
	 * 
	 * This Agent instance may have different kind of forms as its vital
	 * properties are parametric. Any kind of Agent-Form should inherit this
	 * class and change as wish.
	 * 
	 * New Thread, Breathing and Acting with >1Hz
	 * 
	 * An Agent class is formed as abstract as it may not be considered a life
	 * form in such general way. Additionally, this class should be inherit to
	 * provide Basic life form to each new AgentLife Being.
	 * 
	 * @see #Agent(String, Playground, double, double)
	 * @see #onBreathing()
	 * @see #onCreate()
	 * @param name
	 *            Agent's Name
	 */
	public Agent( String name )
	{
		super();
		setAgentName(name);
	}
	
	/**
	 * 
	 * 
	 * @see #Agent(String)
	 * @see #onBreathing()
	 * @see #onCreate()
	 * @param name
	 *            Agent's Name
	 * @param pGround
	 *            It's Playground World
	 * @param strartingEnergy
	 *            It's Starting Energy Points
	 * @param speed
	 *            It's Clocking time
	 */
	public Agent( String name, Playground pGround, double strartingEnergy,
			double speed )
	{
		this(name);
		//As this attributes should be synchronized to all interactive objects//Hosts//listeners
		//They should be synchronized in order to archive that asynchronous interaction.
		synchronized(this)
		{
			agentHost = pGround;
			agentEnergy = strartingEnergy;
			agentSpeed = speed;
			//shares its starting energy & speed as its genetic matter
			chromosome = new Chromosome(strartingEnergy, speed);
			setStaringAt(NORTH);
			//consumedResources = new Vector<NutriceResource>();
			consumedResources = 0 ;
			currentPosition =  new BiCoordinates();
						
			// Starts a new Thread, so every Agent is Voluntary individual
			lifeThread = new Thread(aTimer = new AgentTimer(
					(int) agentSpeed * 1000, new ActionListener()
					{
						public void actionPerformed( ActionEvent e )
						{
							//Agent is taking it own Decisions Just before it Acts 
							onBreathing();
							act();
						}
					}), agentName);
			//Agent is making its own decisions
			onCreate();
			
			//Agent Awakes
			do
			{
				aTimer.setRepeats(true);
				lifeThread.start();
			}while(!isAlive());
		}
	}
	
	/**
	 * 
	 * @see #Agent(String)
	 * @see #Agent(String, Playground, double, double)
	 * @see #onBreathing()
	 * @see #onCreate()
	 * @param name
	 *            Agent's Name
	 * @param pGround
	 *            It's Playground World
	 * @param chromo The Chromosomes that has inherit by its parents.
	 */
	public Agent( String name, Playground pGround, Chromosome chromo )
	{
		this(name, pGround, chromo.energy, chromo.speed);
	}

	/**
	 * Agent has been initiated and called this method as before 
	 * its final initiation.
	 * @see Playground.Agents.AgentInterface#onCreate()
	 */
	@Override
	public void onCreate( )
	{
		
	}

	/**
	 * This method should be Overwritten and its called as the Agent starts to
	 * take its next Breath.
	 * By Breath is meant its clock time.
	 * @see Playground.Agents.AgentInterface#onBreathing()
	 */
	@Override
	public void onBreathing( )
	{
		
	}

	/**
	 * Just before Agent, Cancel its heart beat. By throwing some exception 
	 * Agent may Prevent its Death.
	 * @see Playground.Agents.AgentInterface#onDie()
	 */
	@Override
	public void onDie( )
	{
		
	}

	/**
	 * Agent is being Paused Or Deactivated. By throwing some Exception Agent 
	 * may Prevent this.
	 * @see Playground.Agents.AgentInterface#onDeactivating()
	 */
	@Override
	public void onDeactivating( )
	{
		
	}

	/**
	 * As the Agent gets its Revive Signal this method is called. This how more 
	 * Incitations may be achieved
	 * @see Playground.Agents.AgentInterface#onRevive()
	 */
	@Override
	public void onRevive( )
	{
		
	}

	/**
	 * This method is called just before Agent Clone or 
	 * Reproduce an other Agent.
	 * @see #Chromosome
	 * @see Playground.Agents.AgentInterface#onCloning()
	 */
	@Override
	public void onCloning( AgentInterface target )
	{
		
	}

	/**
	 * This method is called when Agent meet an other instance of its 
	 * kind//Agent.. Here some Vital Decisions about reacting are taking place.
	 * @see Playground.Agents.AgentInterface#onCommunicating()
	 */
	@Override
	public void onCommunicating( AgentInterface target )
	{
		
	}

	/**
	 * As the Agent should be seriazed, This method is called.
	 * @see Playground.Agents.AgentInterface#onSerialize()
	 */
	@Override
	public void onSerialize( )
	{
		
	}	
	
	/**
	 * As an Agent may Evolve and reproduct it self. It may carry its 
	 * evolutionary data to its next generations. A Chromosome class describes
	 * The hard-written Chromosomes in the body of an Agent. This How evolution
	 * may be achieved!
	 * 
	 * Thishow, Agnet implement its Genetic Algorithm.
	 * Chromosome creates a new Chromosome set by inputing both parents 
	 * Chromosome sets. The {@link #crossWith(Chromosome)} method creates 
	 * the new Chromosome set of the produced child. This set should be inherit 
	 * by the child in order tp be generated.
	 * 
	 *  @see #crossWith(Chromosome)
	 * 
	 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 *
	 */
	public class Chromosome extends Object implements Serializable
	{
		/**
		 * The Mutation Rate of the Genetic Algorithm.
		 * Sets the rate of Agents Evolution.
		 */
		final public static double MUTATION_RATE = 0.3; //30% Mutation rate
		/**
		 * Energy hard-witten in the chromosome 
		 */
		double energy;
		/**
		 * The speed Ability of the Agent that has been written to the
		 * Chromosome
		 */
		double speed;
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * As an Agent may Evolve and reproduct it self. It may carry its 
		 * evolutionary data to its next generations. A Chromosome class describes
		 * The hard-written Chromosomes in the body of an Agent. This How evolution
		 * may be achieved!
		 * 
		 * Thishow, Agnet implement its Genetic Algorithm.
		 * Chromosome creates a new Chromosome set by inputing both parents 
		 * Chromosome sets. The {@link #crossWith(Chromosome)} method creates 
		 * the new Chromosome set of the produced child. This set should be inherit 
		 * by the child in order tp be generated.
		 * 
		 *  @see #crossWith(Chromosome)
		 */
		public Chromosome( )
		{
			super();
		}
		
		/**
		 * 
		 * @see #Chromosome()
		 * @param enrg Energy
		 * @param spd Speed
		 */
		public Chromosome( double enrg, double spd )
		{
			this();
			energy = enrg;
			speed = spd;
		}
		
		/**
		 * This method implements the Genetic Algorithm. It produce a new 
		 * Chromosome set by the evolution level of its parents. It also shares 
		 * a 30% probability to mutate the child.
		 * 
		 * @param c the crossing Parent Chromosome set.
		 * @return
		 */
		public Chromosome crossWith( Chromosome c )
		{
			//tmpEnergy has the Least energy of both parents
			double tmpEnerg = ( c.energy < energy ? c.energy : energy );
			//tmpSpeed has the greatest speed of both parents
			double tmpSpeed = ( c.speed > speed ? c.speed : speed );
			
			//Probability of which parent will inherit its Energy
			double genEnerg = tmpEnerg + ( Math.random() * Math.abs( c.energy - energy ) );
			double genSpeed = tmpSpeed - ( Math.random() * Math.abs( c.speed - speed ) );
			
			if( Math.random() > (1 - MUTATION_RATE ) )
			{
				//It Mutate by 15% its Energy
				genEnerg += (genEnerg * 15 ) / 100;
			}
			if( Math.random() > (1 - MUTATION_RATE ) )
			{
				//it mutate by 10% its Speed
				genSpeed -= (genSpeed * 10 ) / 100;
			}
			
			return new Chromosome( genEnerg, genSpeed );
		}
	}
	
	
	/**
	 * A Class that makes Agent have its own Timer for Acting and calculating
	 * Is runnable in order to have Agent in a new lifeThread an run with no
	 * Dependency
	 * from other generated Agents
	 * 
	 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 */
	final public class AgentTimer extends TimeDimention implements Runnable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4231271399661634459L;
		
		protected AgentTimer( int speed, ActionListener listener )
		{
			super(speed, listener);
		}
		
		public void run( )
		{
			if ( !isRunning() )
			{
				start();
			}
			if ( !isRepeats() )
			{
				setRepeats(true);
			}
		}
	}
	
	/**
	 * A class that Calculates and perform Agent's Movements and position, it also shout for a new Movement
	 * @author  Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 */
	final public class MoveAction extends EventObject
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2218494201150890288L;
		public int x;
		public int y;
		/**
		 * @uml.property  name="movingtAgent"
		 * @uml.associationEnd  
		 */
		public Agent movingtAgent;
		
		public MoveAction( Agent target, int direction )
		{			
			super(target);
			movingtAgent = target;
			x = movingtAgent.currentPosition.axisX;
			y = movingtAgent.currentPosition.axisY;
			
			switch ( direction )
			{
				case BiDimensional.NORTH: // North
					agentHost.moveListener.actionPerformed(moveNorth());
					break;
				case BiDimensional.EAST: // East
					agentHost.moveListener.actionPerformed(moveEast());
					break;
				case BiDimensional.SOUTH: // South
					agentHost.moveListener.actionPerformed(moveSouth());
					break;
				case BiDimensional.WEST: // West
					agentHost.moveListener.actionPerformed(moveWest());
					break;
				default:
					break;
			}
		}
		
		/**
		 * 
		 * @return int One level Raised Y-Axis
		 */
		protected MoveAction moveNorth( )
		{
			setStaringAt(BiDimensional.NORTH);
			y++;
//			return new ActionEvent(movingtAgent, 0, movingtAgent.getAgentName());
			return this;
		}
		
		/**
		 * 
		 * @return int One level Reduced Y-Axis
		 */
		protected MoveAction moveSouth( )
		{
			setStaringAt(BiDimensional.SOUTH);
			y--;
//			return new ActionEvent(movingtAgent, 0, movingtAgent.getAgentName());
			return this;
		}
		
		/**
		 * 
		 * @return int One level Raised X-Axis
		 */
		protected MoveAction moveEast( )
		{
			setStaringAt(BiDimensional.EAST);
			x++;
//			return new ActionEvent(movingtAgent, 0, movingtAgent.getAgentName());
			return this;
		}
		
		/**
		 * 
		 * @return int One level Reduced X-Axis
		 */
		protected MoveAction moveWest( )
		{
			setStaringAt(BiDimensional.WEST);
			x--;
//			return new ActionEvent(movingtAgent, 0, movingtAgent.getAgentName());
			return this;
		}
	}
	
	/**
	 * Agent Start Breathing
	 * 
	 * @see #onBreathing()
	 */
	synchronized final public void run( )
	{
		if ( lifeThread != null && !lifeThread.isAlive() )
		{
			aTimer.start();
			lifeThread.start();
		}
		else
		{
			aTimer.start();
			lifeThread.start();
		}
	}
	
	/**
	 * Agent Acts, Start Moving Randomly, Checking for Food, Communicates,
	 * eats, get Tired, get Hungry, and if it get very Tired -> Dies
	 * 
	 * Actually this is an example scenario that can be overridden by a SubClass
	 * and build its own Scenario-Script.
	 * 
	 * 
	 * 
	 * @see #setCurrentPosition(int, int)
	 * @see #reactWith(Playground.Agents.Agent, boolean)
	 * @see #taste(Playground.Resources.NutriceResource)
	 * @see #die()
	 */
	synchronized public void act( )
	{
		/*
		 * Now..
		 * It actually starts my scenario, how an Agent should act
		 */
		// An Agent Should loose a bit energy by the time its "Breath"
		setAgentEnergy(getAgentEnergy() - 1);
		
		// Check if the Agent alive, (if actually has the Energy to Live)
		if ( isAlive() && ( getAgentEnergy() > 0 ) )
		{
			
			// At first The Agent Seeks of its neighborhood and react.
			seekNearObjects(true);
			// after seek it looses energy, so..
			setAgentEnergy(getAgentEnergy() - 3);
			
			// And Now, lets make random Moves
			
			/**
			 * Please Note and Copy the Pattern of this Command, as you wish
			 * your have the ability of it's individual movement on its field.
			 * In every Random Generated Number to 0-10 we get 0-3.
			 * The Actual Four Horizon Spot, the wished Agent's Moves
			 */
			new MoveAction(this, Math.abs( ( (int) ( Math.random() * 4 ) ) ) );
			
		}
		else
		// Dies..!! Snif.. Snif..!
		{
			try
			{
				// Kill the Agent
				die();
			}
			catch ( InterruptedException ie )
			{
				// Do nothing
			}
			catch ( AgentDeath death )
			{
				// Do nothing
			}
		}
	}
	
	/**
	 * @see #seekNearObjects(boolean)
	 * 
	 *      it just gives true parameter and Do react with each noticeable
	 *      objects.
	 */
	synchronized public void seekNearObjects( )
	{
		// Do React
		seekNearObjects(true);
	}
	
	/**
	 * Seek for Near Objects in current position.
	 * 
	 * @param react
	 *            False, the Agent Stay Still and doesn't react with the Objects
	 */
	synchronized public void seekNearObjects( boolean react )
	{
		/**
		 * That's a Loop as the Agent turns around its notice to seek for near
		 * Objects that may concern it.
		 * 
		 * Thishow, an algorithm take place to seek for near object
		 * x: Agent
		 * c: Coordinates
		 * | c |+1+c | c |
		 * |-1+c | x |+1+c |
		 * | c |-1+c | c |
		 */
		try
		{
			for ( int i = getAxisX() - 1; i <= getAxisX() + 1; i++ )
			{
				for ( int j = getAxisY() - 1; i <= getAxisY() + 1; i++ )
				{
					// Lock the target (Pointer)
					Object obj = ( (Playground) agentHost).queryObjectAt(i, j);
					
					if ( obj instanceof Agent ) // in Case of Agent
					{
						if ( react )
						{
							// Avoid Mirroring itSelf
							if ( !this.equals(obj) )
							{
								reactWith((Agent) obj, true);
							}
						}
					}
					else if ( obj instanceof NutriceResource ) // In Case of
																// Food
					{
						if ( react )
						{
							// Sniff the place for Resources
							taste((NutriceResource) obj);
						}
					}
				}
			}
		}
		catch ( Exception e )
		{
			// Do Nothing
		}
	}
	
	
	/**
	  This How the Agent Pause its Heart beat without Diing. Though 
	 * As the Agent has the right to prevent this, it may throw an exception in
	 * onDeactivating() method. Also Agent may not pause it timer because of 
	 * some of agentTimer's exception.
	 * 
	 * @deprecated Use {@link #deactivate()} instead
	 */
	synchronized final public void pause()
	{
		deactivate();
	}
	
	/**
	 * This How the Agent Pause its Heart beat without Diing. Though 
	 * As the Agent has the right to prevent this, it may throw an exception in
	 * onDeactivating() method. Also Agent may not pause it timer because of 
	 * some of agentTimer's exception.
	 * 
	 * @see #onDeactivating()
	 * @see #reactivate()
	 * @see #AgentTimer
	 * @return true if it's paused
	 */
	synchronized final public boolean deactivate()
	{
		try
		{
			//Agent has the right to decide if it'll deactivate or what to do 
			//just before that
			onDeactivating();
		}
		catch(Exception e )
		{
			return false;
		}
		
		//Deactivation
		try
		{
			aTimer.stop();
		}
		catch (Exception e) 
		{
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * This How the Agent unPaused its heart-beat and start running again. 
	 * it AgentTimer start beating again and just before that the onRevive() 
	 * method is being called. Thishow Agent has the freedom//Right to decide 
	 * the Just-Before action it will do.
	 * 
	 * @deprecated Use {@link #reactivate()} instead
	 */
	synchronized final public void unPause()
	{
		reactivate();
	}
	
	/**
	 * This How the Agent unPaused its heart-beat and start running again. 
	 * it AgentTimer start beating again and just before that the onRevive() 
	 * method is being called. Thishow Agent has the freedom//Right to decide 
	 * the Just-Before action it will do.
	 * 
	 * @see #onRevive()
	 * @see #deactivate()
	 * @see #AgentTimer
	 * @return true if it's unPaused
	 */
	final public boolean reactivate()
	{
		try
		{
			//Agent do something just before it will revived
			onRevive();
		}
		catch (Exception e) 
		{
			return false;
		}
		
		try
		{
			aTimer.setRepeats(true);
			aTimer.start();
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Just Talk/Response to given Agent
	 * 
	 * @see #reactWith(Agent, boolean)
	 * @see #onCommunicating(AgentInterface)
	 * @param agent
	 * @deprecated Use {@link #reactWith(Agent)} instead
	 */
	final public void chatWith( Agent agent )
	{
		reactWith(agent);
	}

	/**
	 * Just Talk/Response to given Agent
	 * 
	 * @see #onCommunicating(AgentInterface)
	 * @see #reactWith(Agent, boolean)
	 * @param agent
	 */
	final public void reactWith( Agent agent )
	{
		// This Agent "talks" with the connected Agent
//		System.out.println("--Agent " + getAgentName() + ": Hey "
//				+ agent.getAgentName() + "..!");
	}
	
	/**
	 * Talk-HandShake with the Agent
	 * 
	 * @see #onCommunicating(AgentInterface)
	 * @param agent
	 * @param responce
	 *            if response is false, Agent doesn't demand a response
	 * @deprecated Use {@link #reactWith(Agent,boolean)} instead
	 */
	final public void chatWith( Agent agent, boolean responce )
	{
		reactWith(agent, responce);
	}

	/**
	 * Talk-HandShake with the Agent
	 * 
	 * @see #onCommunicating(AgentInterface)
	 * @param agent
	 * @param responce
	 *            if response is false, Agent doesn't demand a response
	 */
	final public void reactWith( Agent agent, boolean responce )
	{
		try
		{
			//Agent has the right to Prevent this or do something just before that
			onCommunicating( agent );
		}
		catch (Exception e) 
		{
			//exit from react
			return;
		}
		
		
		// This Agent "talks" with the connected Agent
//		System.out.println("--Agent " + getAgentName() + ": WazzUp "
//				+ agent.getAgentName() + "..?");
//		if ( response )
//		{
//			// Agent Response to this Agent
//			agent.chatWith(this);
//		}
	}
	
	/**
	 * A helpful method to sniff a given NutriceResource, and perform an
	 * Eat/Drink
	 * Action
	 * 
	 * @param nutriceResource
	 * @see #consume(Playground.Resources.Eatable)
	 * @see #drink(Playground.Resources.Drinkable)
	 */
	public void taste( NutriceResource nutriceResource )
	{
		if ( nutriceResource instanceof Eatable )
		{
			this.consume((Eatable) nutriceResource);
		}
		else if ( nutriceResource instanceof Drinkable )
		{
			this.drink((Drinkable) nutriceResource);
		}
	}
	
	/**
	 * A method that Shares the ability to eat Eatable Resources
	 * 
	 * @param food
	 * @deprecated Use {@link #consume(Eatable)} instead
	 */
	final public void eat( Eatable food )
	{
		consume(food);
	}
	
	/**
	 * A method that Shares the ability to consume Eatable Resources
	 * 
	 * @param food
	 */
	synchronized final public void consume( Eatable food )
	{
		
		setAgentEnergy(((NutriceResource) food).getCalories() / 2);
		
		//consumedResources.add((NutriceResource) food);
		consumedResources++;
		
		( (Playground) agentHost ).biDsObjects.remove(food);
	}
	
	/**
	 * A method that Shares the ability to drink Drinkable Resources
	 * 
	 * @param drink
	 */
	synchronized final public void drink( Drinkable drink )
	{
		setAgentEnergy(((NutriceResource) drink).getCalories() / 3);
		
		//consumedResources.add((NutriceResource) drink);
		consumedResources++;
		( (Playground) agentHost ).biDsObjects.remove(drink);
	}
	
	/**
	 * This, Kills Agent and stop its Task and lifeThread.
	 * 
	 * it kills its lifeThread and also stop its Timer. Thishow an Agent is dead
	 * although the Agent is not deleted as the delete function
	 * should be implemented by a global Daemon which collects
	 * all non alive Agents in the agentHost.
	 * 
	 * The Agent has the right to refuse his death. So a onDie event is taking 
	 * place just before an AgentDeath Happen.
	 * 
	 * @see #onDie()
	 * @throws InterruptedException
	 * @throws AgentDeath
	 */
	synchronized final public void die( ) throws InterruptedException, AgentDeath
	{
		try
		{
			onDie();
		}
		catch (Exception e) 
		{
			//exit
			return;
		}
		
		// SoulTaker
		throw new AgentDeath(this, lifeThread, aTimer);
	}
	
	/**
	 * A Boolean Switch method checking Agent's Alive Status. 
	 * 
	 * @see #die()
	 * @return true|False of its Alive status
	 */
	final public boolean isAlive( )
	{
		return (aTimer.isRunning() || lifeThread.isAlive() ? true : false);
	}
	
	/**
	 * A Boolean Switch just to indicate Agent's Running status.
	 * 
	 * @see #reactivate()
	 * @see #deactivate()
	 * @return
	 */
	final public boolean isRunning()
	{
		return aTimer.isRunning();
	}
	
	/**
	 * Gets Agent's Name
	 * @return  String
	 * @uml.property  name="agentName"
	 */
	final public String getAgentName( )
	{
		return agentName;
	}
	
	/**
	 * Gets Agent's Energy
	 * @return  double
	 * @uml.property  name="agentEnergy"
	 */
	final public double getAgentEnergy( )
	{
		return agentEnergy;
	}
	
	/**
	 * Gets Agent's Speed
	 * @return  double
	 * @uml.property  name="agentSpeed"
	 */
	final public double getAgentSpeed( )
	{
		return agentSpeed;
	}
	
	/**
	 * Gets Agent's Position in X Axis
	 * 
	 * @return int
	 */
	@Override
	final public int getAxisX( )
	{
		return currentPosition.axisX;
	}
	
	/**
	 * Gets Agent's Position in Y Axis
	 * 
	 * @return int
	 */
	@Override
	final public int getAxisY( )
	{
		return currentPosition.axisY;
	}
	
	/**
	 * Sets Agent's Name
	 * @param  agentName
	 * @uml.property  name="agentName"
	 */
	final public void setAgentName( String agentName )
	{
		this.agentName = agentName;
	}
	
	/**
	 * Set Agent's Energy
	 * @param  agentEnergy
	 * @uml.property  name="agentEnergy"
	 */
	final public void setAgentEnergy( double agentEnergy )
	{
		this.agentEnergy = agentEnergy;
	}
	
	/**
	 * Sets The current position of the agent in a 2D Axis System
	 * 
	 * @param x
	 * @param y
	 */
	final public void setCurrentPosition( int x, int y )
	{
		currentPosition.axisX = x;
		currentPosition.axisY = y;
	}
	
	/**
	 * Sets X axis position
	 * 
	 * @param x
	 */
	@Override
	final public void setAxisX( int x )
	{
		currentPosition.axisX = x;
	}
	
	/**
	 * Sets Y axis position
	 * 
	 * @param y
	 */
	@Override
	final public void setAxisY( int y )
	{
		currentPosition.axisY = y;
	}
	
	/**
	 * @param staringAt  the staringAt to set
	 * @uml.property  name="staringAt"
	 */
	final public void setStaringAt( int staringAt )
	{
		this.staringAt = staringAt;
	}
	
	/**
	 * @return  the staringAt
	 * @uml.property  name="staringAt"
	 */
	final public int getStaringAt( )
	{
		return staringAt;
	}
	
	/**
	 * Gets the whole Resources Vector
	 * 
	 * @return the consumedResources
	 */
	//final public Vector<NutriceResource> getConsumedResources( )
	//{
	//	return this.consumedResources;
	//}
	
	/**
	 * Gets the Number of Consumed Resources
	 * 
	 * @return
	 */
	final public int getConsumedResourcesNum( )
	{
		return consumedResources;
	}
	
	/**
	 * @return  the chromosome
	 * @uml.property  name="chromosome"
	 */
	final public Chromosome getChromosome( )
	{
		return this.chromosome;
	}
	
	/**
	 * @param chromosome  the chromosome to set
	 * @uml.property  name="chromosome"
	 */
	final public void setChromosome( Chromosome chromosome )
	{
		this.chromosome = chromosome;
	}
	
	/**
	 * @return  the targetAgent
	 * @uml.property  name="targetAgent"
	 */
	public Agent getTargetAgent( )
	{
		return this.targetAgent;
	}
	
	/**
	 * @param targetAgent  the targetAgent to set
	 * @uml.property  name="targetAgent"
	 */
	public void setTargetAgent( Agent targetAgent )
	{
		this.targetAgent = targetAgent;
	}
}
