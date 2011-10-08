
package Playground;



/**
 * Describes an object that can "run" at a 2D Space.
 * Has Width and Height, and it's 2D position Coordinates.
 * 
 * BiDimensional also holds the four signs of the horizon as 
 * signs of a BiDimensional World.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 */
public interface BiDimensional
{
    /**
     * NORTH (0) + WALK_FORTH (0) % 4 = NORTH(0)
     * NORTH (0) + WALK_RIGHT (1) % 4 = EAST(1)
     * NORTH (0) + WALK_BACK (2) % 4 = SOUTH(2)
     * NORTH (0) + WALK_LEFT (3) % 4 = WEST(3)
     */
    final static int NORTH = 0;
    /**
     * EAST (1) + WALK_FORTH (0) % 4 = EAST(1)
     * EAST (1) + WALK_RIGHT (1) % 4 = SOUTH(2)
     * EAST (1) + WALK_BACK (2) % 4 = WEST(3)
     * EAST (1) + WALK_LEFT (3) % 4 = NORTH(0)
     */
    final static int EAST = 1;
    /**
     * SOUTH (2) + WALK_FORTH (0) % 4 = SOUTH(2)
     * SOUTH (2) + WALK_RIGHT (1) % 4 = WEST(3)
     * SOUTH (2) + WALK_BACK (2) % 4 = NORTH(0)
     * SOUTH (2) + WALK_LEFT (3) % 4 = EAST(1)
     */
    final static int SOUTH = 2;
    /**
     * WEST (3) + WALK_FORTH (0) % 4 = WEST(3)
     * WEST (3) + WALK_RIGHT (1) % 4 = NORTH(0)
     * WEST (3) + WALK_BACK (2) % 4 = EAST(1)
     * WEST (3) + WALK_LEFT (3) % 4 = SOUTH(2) 
     */
    final static int WEST = 3;
    
    /**
	 * It's own Coordinates class
	 * 
	 * @author Pappas Evagelos - papas.evagelos@gmail.com
	 * 
	 */
	public class BiCoordinates
	{
		public int axisX;
		public int axisY;
	};
    
    /**
     * Gets BiDimensinal Object Possition on X Axis
     * @return int X-Axis Possition
     */
    abstract public int getAxisX();

    /**
     * Gets BiDimensinal Object Position on Y Axis
     * @return int Y-Axis Possition
     */
    abstract public int getAxisY();

    /**
     * Move object to this X Axis
     * @param x
     */
    abstract public void setAxisX(int x);

    /**
     * Move object to this Y Axis
     * @param y
     */
    abstract public void setAxisY(int y);

}
