
package Playground.Resources;

import Playground.BiDimensional;

/**
 *
 * @author Pappas Evangelos - papas.evagelos@gmail.com ( BagosGiAr )
 */
public class NutriceResource implements BiDimensional
{
    /**
	 * @uml.property  name="resourceName"
	 */
    private String resourceName;
    /**
	 * @uml.property  name="calories"
	 */
    private double calories;
    /**
	 * It's Position
	 * @uml.property  name="currentPosition"
	 * @uml.associationEnd  
	 */
	private BiCoordinates currentPosition;

    public NutriceResource()
    {
    }

    public NutriceResource( String resourceName, double calories )
    {
        this.resourceName = resourceName;
        this.calories = calories;
        currentPosition =  new BiCoordinates();
    }

    /**
	 * @return
	 * @uml.property  name="calories"
	 */
    public double getCalories()
    {
        return calories;
    }

    /**
	 * @return
	 * @uml.property  name="resourceName"
	 */
    public String getResourceName()
    {
        return resourceName;
    }

    /**
	 * @param calories
	 * @uml.property  name="calories"
	 */
    public void setCalories( double calories )
    {
        this.calories = calories;
    }

    /**
	 * @param resourceName
	 * @uml.property  name="resourceName"
	 */
    public void setResourceName( String resourceName )
    {
        this.resourceName = resourceName;
    }

    /**
	 * Gets Position in X Axis
	 * 
	 * @return int
	 */
	@Override
	final public int getAxisX( )
	{
		return currentPosition.axisX;
	}
	
	/**
	 * Gets Position in Y Axis
	 * 
	 * @return int
	 */
	@Override
	final public int getAxisY( )
	{
		return currentPosition.axisY;
	}

	/**
	 * Sets The current position in a 2D Axis System
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


}
