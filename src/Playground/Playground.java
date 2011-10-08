package Playground;

import Playground.Agents.Agent;
import Playground.Agents.Agent.MoveAction;
import Playground.GUI.PlaygroundGUI;
import Playground.Generators.AgentGenerator;
import Playground.Generators.ResourceGenerator;
import Playground.Resources.NutriceResource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Iterator;
import java.util.Vector;

/**
 * A Class Describes the PLayground of the simulator, all Objects are Running in
 * this Time and 2D-Space. An Object that wish to run, must implement a
 * BiDimensional interface, and call the MoveListener to move on X,Y-Axis of the
 * Space. Thishow it the ObServe Pattern is being implemented.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 */
public class Playground extends AgentHost
{
	/**
	 * The Time Axis of the Playground
	 */
	private static TimeDimention timeDimention;
	/**
	 * The 2D Space-Playground
	 */
	private static SpaceBiDimension spaceBiDimension;
	/**
	 * The Graphic Unit Interface of this PLayground
	 */
	private static PlaygroundGUI gui;
	/**
	 * The Scavenger of this Playground
	 * @uml.property  name="scavenger"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="this$0:Playground.Playground$Scavenger"
	 */
	private Scavenger scavenger;
	/**
	 * Agent Generator
	 * @uml.property  name="agentGenerator"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="playground:Playground.Generators.AgentGenerator"
	 */
	private AgentGenerator agentGenerator;
	/**
	 * Resource Generator
	 * @uml.property  name="resourceGenerator"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="playground:Playground.Generators.ResourceGenerator"
	 */
	private ResourceGenerator resourceGenerator;
	/**
	 * The Genetic Algorithm
	 * @uml.property  name="geneticThread"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="playground:Playground.Playground$Genetic"
	 */
	private Genetic geneticThread;
	/**
	 * The Year counter; 365 days == 1 year
	 * @uml.property  name="year"
	 */
	private int year;
	/**
	 * Day Counter; Each Timer-Clock-Cycle
	 * @uml.property  name="day"
	 */
	private int day;
	/**
	 * the Number that raises each day in each Timer-Clock-cycle
	 */
	final public static int DAY_RAISE_COUNTER=10;
	/**
	 * The Day-sum of a Year
	 */
	final public static int DAY_SUM_YEAR=200;//200 days == 1 year
		
	
	/**
	 * The Constructor of the Playground, Initiate all vital Variables
	 * 
	 * @param width
	 *            - Width of the X-Axis
	 * @param height
	 *            - height of the Y-Axis
	 */
	public Playground( int width, int height )
	{
		synchronized(this)
		{
			day=0;
			year=0;
			biDsObjects = new Vector<BiDimensional>();
			spaceBiDimension = new SpaceBiDimension(width, height);
			scavenger = new Scavenger(this);
			agentGenerator = new AgentGenerator(this);
			resourceGenerator = new ResourceGenerator(this);
			geneticThread = new Genetic(this);
			//Setting the Actions Of the TimeDimention of the Playground
			setTimeDimension(new TimeDimention(1000, new TimeListener()));
			timeDimention.setRepeats(true);
			timeDimention.start();
			moveListener = new MoveListener();
		}
	}
	
	/**
	 * An internal unlimited-unstoppable loop of the playground, performs Vital
	 * Checks of the PLayground.
	 * 
	 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 */
	private class TimeListener implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		public TimeListener( )
		{
			
		}
		
		public void actionPerformed( ActionEvent e )
		{
			//new day passes by
			day+=DAY_RAISE_COUNTER;
			if( day >= DAY_SUM_YEAR ) 
			{
				year++;
				day=0;
				newGeneration();
			}
			
			//Promote an individual Thread to the Scavenging
			// Check for Running Status; in case it hasn't end its previous Job
			if( scavenger.isAlive && !scavenger.isRunning())
			{
				try
				{
					//Run the Scavenger
					new Thread(scavenger).start();
				}
				catch (Exception exc) 
				{ }
			}
			
			if( gui != null && gui.isAlive())
			{
				gui.dispatch();
			}
		}
	}
	
	/**
	 * Genetic Algorithm. This implements the Evolutionary//reproductive part of the simulation.
	 * @author  Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 */
	public class Genetic implements Runnable
	{
		/**
		 * @uml.property  name="playground"
		 * @uml.associationEnd  
		 */
		Playground playground;
		boolean isRunning;
		
		/**
		 * 
		 */
		public Genetic( Playground p )
		{
			synchronized(this)
			{
				playground = p;
				isRunning = false;
			}
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		synchronized public void run( )
		{
			System.out.println("..Generating..");
			isRunning = true;
			Vector<BiDimensional> tempVect = new Vector<BiDimensional>(playground.biDsObjects);
			Iterator<BiDimensional> iterator = tempVect.iterator();
			playground.biDsObjects.removeAll( playground.biDsObjects );
			playground.biDsObjects.trimToSize();
			
			//Hard removing
			//While Parsing
			for(int i=0; i< getHeight(); i++)
			{
				for(int j=0; j<getWidth(); j++)
				{
					try
					{
						//Temporary Current BiDimetional Object
						BiDimensional tempBD = (BiDimensional) queryObjectAt(i, j);
						if( tempBD instanceof Agent && tempBD != null )
						{
							//Pop out of the list
							playground.biDsObjects.remove(tempBD);
							
							//remove it
							removeObjAt(tempBD.getAxisX(), tempBD.getAxisY());
						}
					}
					catch (Exception e) 
					{
						//Do nothing
					}
				}
			}
			
			//While Parsing
			while(iterator.hasNext())
			{
				try
				{
					//Temporary Current BiDimetional Object -- The Two Parents
					BiDimensional[] parrentBDs = new Agent[2];
					BiDimensional tempBD = iterator.next();
					//this is a boolean Switch to handle the Fibonacc Algorithm
					boolean switchCouple = false;
					
					//In case of other objects
					if(tempBD != null && tempBD instanceof Agent )
					{
						//if there's only one parent registered
						if(!switchCouple )
						{
							parrentBDs[0] = tempBD;
							switchCouple = true;
						}
						else
						{
							//register and the second one
							parrentBDs[1] = parrentBDs[0];
							parrentBDs[0] = tempBD;
							//Behold the Evolution -crossover
							agentGenerator.generate( 
									( (Agent) parrentBDs[0] ).getChromosome().crossWith(  
											( (Agent) parrentBDs[1] ).getChromosome()  
											));
							//Forget those two
							switchCouple = false;
						}
					}
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			for(int i=0; i<biDsObjects.size(); i++)
			{
				resourceGenerator.generate();
			}
			
			//As the Loop has ended; stops Running.
			isRunning = false;
		}
	}
	
	/**
	 * A Class that Seeks in the playground to clean up any dead//Unused//illegal//NotSuported Entity. It Kills//Dispose//Remove it.
	 * @author  Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 */
	final private class Scavenger implements Runnable
	{
		/**
		 * @uml.property  name="playground"
		 * @uml.associationEnd  
		 */
		private Playground playground;
		/**
		 * @uml.property  name="isAlive"
		 */
		private boolean isAlive;
		/**
		 * @uml.property  name="isRunning"
		 */
		private boolean isRunning;
		
		/**
		 * 
		 * Seeks in the playground to clean up any
		 * dead//Unused//illegal//NotSuported Entity.
		 * It Kills//Dispose//Remove it.
		 * @param p Its Playground
		 */
		public Scavenger( Playground p )
		{
			synchronized(this)
			{
				playground = p;
				isAlive = true;
				isRunning = false;
			}
		}
		
		synchronized final public void run()
		{
			isAlive = true;
			isRunning = true;
			//While Parsing
			for(int i=0; i< getHeight(); i++)
			{
				for(int j=0; j<getWidth(); j++)
				{
					try
					{
						//Temporary Current BiDimetional Object
						BiDimensional tempBD = (BiDimensional) queryObjectAt(i, j);
						if( tempBD instanceof Agent && tempBD != null )
						{
							if( !( (Agent) tempBD ).isAlive() )
							{
								//kills it
								( (Agent) tempBD ).die();
								//Pop out of the list
								playground.biDsObjects.remove(tempBD);
								playground.biDsObjects.trimToSize();
								
								//Generate its ancestor
								agentGenerator.generate( ( (Agent) tempBD ).getChromosome() );
								//remove it
								removeObjAt(tempBD.getAxisX(), tempBD.getAxisY());
							}
						}
					}
					catch (Exception e) 
					{
						//Do nothing
					}
				}
			}
			//As the Loop has ended Scavenger stops Running.
			isRunning = false;
		}
		
		/**
		 * Switch Method. Gets True if the Scavenger is Alive//Initiated
		 * @return
		 * @uml.property  name="isAlive"
		 */
		@SuppressWarnings( "unused" )
		final boolean isAlive()
		{
			return isAlive;
		}
		/**
		 * Switch Method. Gets True if the Scavenger has been initiated  and running.
		 * @return
		 * @uml.property  name="isRunning"
		 */
		final boolean isRunning()
		{
			return isRunning;
		}
	}
	
	/**
	 * A class describes all actions of a BiDimensional object have to implement
	 * to move in the 2D PLayground
	 * 
	 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 */
	final public class MoveListener implements EventListener
	{
		/**
		 * Listeners initiation
		 */
		public MoveListener( )
		{
			super();
		}
		
		/**
		 * Calls an initiation, an ask for an Event
		 * 
		 * @see MoveListener
		 * @param e
		 */
		public MoveListener( MoveAction e )
		{
			this();
			actionPerformed(e);
		}
		
		synchronized public void actionPerformed( MoveAction e )
		{
			try
			{
				// in Case of the Attempted Coordinates, an Agent is already
				// located, No movement performed
				if ( !(spaceBiDimension.getBiDimensional(((MoveAction) e).x,
						((MoveAction) e).y) instanceof Agent) )
				{
					// Temporary Variables
					int x = ((MoveAction) e).movingtAgent.getAxisX();
					int y = ((MoveAction) e).movingtAgent.getAxisY();
					
					// place the Agent to its new Coordinates
					spaceBiDimension.placeBiDimensional(
							((MoveAction) e).movingtAgent, ((MoveAction) e).x,
							((MoveAction) e).y);
					
					// and finally Moves
					((MoveAction) e).movingtAgent.setCurrentPosition(
							((MoveAction) e).x, ((MoveAction) e).y);
					
					// Remove its remaining instance
					spaceBiDimension.removeBiDimensionalAt(x, y);
					
				}
			}
			// in case of an Out of Bounds Exception, No Movement is performed,
			// this
			// Occurs when the Given Coordinates are Out of the pre-Confined
			// Space
			catch ( ArrayIndexOutOfBoundsException aioobe ) 
			{
				// Do Nothing
				
				//System.out.println("Agent :"
						//+ ((MoveAction) e).movingtAgent.getAgentName()
						//+ " Just had a Wall-head-bounce..!!");
			}
		}
	}
	
	/**
	 * This method creates a new generation Signal. Thishow, the Genetic 
	 * algorithm is called and so the evolution starts.
	 * By calling this method TimeDimention is being paused. This outcomes to 
	 * handle and parse the new generated BiDimetional objects.
	 */
	synchronized public void newGeneration()
	{
		System.out.println("new Generation is About to start");
		try
		{
			scavenger.wait();
		}
		catch (Exception e)
		{}
		try
		{
			timeDimention.stop();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		//New Genesis starts
		new Thread(geneticThread).start();
		
		//an infinite loop While genetic algorithm is taking place
		while(geneticThread.isRunning)
		{
			continue;
		}
		
		try
		{
			timeDimention.start();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Queries an object at the given coordinates,
	 * Useful because of the use of Try - Catch that returns Null in any
	 * Exception
	 * 
	 * @param x
	 *            X-Axis value
	 * @param y
	 *            Y-Axis value
	 * @return Object OR null
	 */
	public Object queryObjectAt( int x, int y )
	{
		try
		{
			// Returns the current BiDimentional Object in this Spot
			return (Object) spaceBiDimension.getBiDimensional(x, y);
		}
		catch ( Exception e )
		{
			// a possible ArrayIndexOutOfBoundsException caught
			// Or in any other case
			return null;
		}
	}
	
	/**
	 * This method is used to remove BiDimensional Object from the Playground. 
     * Simply sets the pointer to null.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean removeObjAt( int x, int y )
	{
		return spaceBiDimension.removeBiDimensionalAt(x, y);
	}
	
	/**
	 * Ads a new BiDimensional Object into the Playground
	 * If in selected Coordinates is located an other object, gets False
	 * 
	 * @param bds
	 * @return boolean
	 */
	final public boolean addObject( BiDimensional bds )
	{
		try
		{
			if ( spaceBiDimension.getBiDimensional(bds.getAxisX(),
					bds.getAxisY()) == null )
			{
				spaceBiDimension.placeBiDimensional(bds, bds.getAxisX(),
						bds.getAxisY());
				return biDsObjects.add(bds);
			}
			else
			{
				return false;
			}
		}
		catch ( NullPointerException npe )
		{
			spaceBiDimension.placeBiDimensional(bds, bds.getAxisX(),
					bds.getAxisY());
			return biDsObjects.add(bds);
		}
		catch ( Exception e )
		{
			return false;
		}
	}
	
	/**
	 * Ads a new BiDimensional Object into the Playground
	 * If in selected Coordinates is located an other object, gets False
	 * 
	 * @param bds
	 * @return boolean
	 */
	public boolean addNewGeneratedObject( BiDimensional bds )
	{
		int x = 0, y = 0; // dummy initialization
		
		try
		{
			do // Loops Until it finds a blank place
			{
				x = (int) (Math.random() * spaceBiDimension.getWidth() - 1);
				y = (int) (Math.random() * spaceBiDimension.getHeight() - 1);
				
			}
			while (spaceBiDimension.getBiDimensional(x, y) != null);
		}
		catch ( Exception e )
		{
			return false;
		}
		
		try
		{
			spaceBiDimension.placeBiDimensional(bds, x, y);
			return biDsObjects.add(bds);
		}
		catch ( NullPointerException npe )
		{
			return biDsObjects.add(bds);
		}
		catch ( Exception e )
		{
			return false;
		}
	}
	
	/**
	 * Enable//Disable the GUI of this PLayground
	 * 
	 * @param enabl
	 *            Enable//Disable Switch
	 * @return
	 */
	final public boolean enableGUI( boolean enabl )
	{
		if ( enabl )
		{
			try
			{
				if ( gui == null || !gui.isActive() )
				{
					gui = new PlaygroundGUI(this);
				}
			}
			catch ( Exception e )
			{
				return false;
			}
			return true;
		}
		else
		{
			if ( gui != null )
			{
				gui.close();
			}
			else
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets The GUI of this PLayground
	 * 
	 * @return the gui
	 */
	public static PlaygroundGUI getGui( )
	{
		return gui;
	}
	
	/**
	 * @param timeDimention
	 *            the timeDimention to set
	 */
	final public static void setTimeDimension( TimeDimention timeDimention )
	{
		Playground.timeDimention = timeDimention;
	}
	
	/**
	 * @return the timeDimention
	 */
	final public static TimeDimention getTimeDimension( )
	{
		return timeDimention;
	}
	
	/**
	 * This method is Used to print at the System's console the instance
	 * of the Playground at the demanded time.
	 */
	public void printInstance( )
	{
		for ( int i = 0; i < spaceBiDimension.getHeight(); i++ )
		{
			for ( int j = 0; j < spaceBiDimension.getWidth(); j++ )
			{
				try
				{
					if ( spaceBiDimension.getBiDimensional(j, i) == null )
					{
						System.out.print(".");
					}
					else if ( spaceBiDimension.getBiDimensional(j, i) instanceof Agent )
					{
						System.out.print("A");
					}
					else if ( spaceBiDimension.getBiDimensional(j, i) instanceof NutriceResource )
					{
						System.out.print("R");
					}
				}
				catch ( NullPointerException npe )
				{
					System.out.print(".");
				}
				catch ( Exception e ) // Do Nothing
				{
				}
			}
			System.out.println("");
		}
	}
	
	/**
	 * 
	 * @return int
	 */
	final public int getWidth( )
	{
		return spaceBiDimension.getWidth();
	}
	
	/**
	 * 
	 * @return int
	 */
	final public int getHeight( )
	{
		return spaceBiDimension.getHeight();
	}
	
	/**
	 * @return  the year
	 * @uml.property  name="year"
	 */
	public int getYear( )
	{
		return this.year;
	}
	
	/**
	 * @return  the day
	 * @uml.property  name="day"
	 */
	public int getDay( )
	{
		return this.day;
	}
	
	/**
	 * @return  the agentGenerator
	 * @uml.property  name="agentGenerator"
	 */
	public AgentGenerator getAgentGenerator( )
	{
		return this.agentGenerator;
	}
	
	/**
	 * @return  the resourceGenerator
	 * @uml.property  name="resourceGenerator"
	 */
	public ResourceGenerator getResourceGenerator( )
	{
		return this.resourceGenerator;
	}
}
