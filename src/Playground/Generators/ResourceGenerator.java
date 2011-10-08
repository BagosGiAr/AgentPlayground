

package Playground.Generators;

import Playground.BiDimensional;
import Playground.Playground;
import Playground.Resources.Food;
import Playground.Resources.Water;


/**
 * A NutriceResource Generator, A stand alone Thread from ThreadGroup of Generators.
 * Generates a new NutriceResource in the Given Playground.
 * 
 * It handles the Generation of each NutriceResource in its space-time-playground
 * 
 * As in Each Generator, also this one is a Daemon. In case of playground's 
 * Termination, Generators are also automated killed.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 */
public class ResourceGenerator extends Generator
{
	/**
	 * @uml.property  name="playground"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="resourceGenerator:Playground.Playground"
	 */
	private Playground playground;
	
	/**
	 * Generates a New NutriceResource, at this ( p ) Playground with 
	 * this ( resourceN ) Name
	 * @param thread
	 */
	public ResourceGenerator(Playground p)
    {
        super(new Thread("ResourceGeneretor"));
        playground = p;
    }
	
	/* (non-Javadoc)
	 * @see Playground.Generators.Generator#generate()
	 */
	@Override
	synchronized public boolean generate( )
	{
		try
		{
			playground.addNewGeneratedObject(new Food("Food",  Math.random()*1000 ) );
	        playground.addNewGeneratedObject(new Water("Water",  Math.random()*100 ) );
		}
		catch (Exception e) 
		{
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see Playground.Generators.Generator#generate(Playground.BiDimensional)
	 */
	@Override
	synchronized public boolean generate( BiDimensional bd )
	{
		try
		{
			playground.addNewGeneratedObject(bd);
		}
		catch (Exception e) 
		{
			return false;
		}
		return true;
	}
	
    /**
     * Runs a new thread that Generates a new Agent in Random Coordinates in the Given PlayGround
     */
    @Override
    synchronized public void run()
    {
		start();
		generate();
	}
	
	/**
	 * 
	 * A copy of {@link #generate(BiDimensional))} function with a 
	 * MultiThreading Functionality
	 */
	synchronized public ResourceGenerator run( BiDimensional bd )
	{
		try
		{
			start();
			generate(bd);
		}
		catch (Exception e)
		{
			//Do Nothing
		}
		return this;
	}
}
