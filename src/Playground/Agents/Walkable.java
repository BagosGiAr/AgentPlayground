package Playground.Agents;

/**
 * An interface about Walkable objects that need to use Walkable Directions.
 * 
 * The Walkable Interface contains Constants of the four directions 
 * of an 2-D Space;
 * 
 * WALK_FORTH = 0
 * WALK_RIGTH = 1
 * WALK_BACK  = 2
 * WALK_LEFT  = 3
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 */
public interface Walkable
{
    /**
     * Walk Forth, Were it was already Staring at
     */
    final static int WALK_FORTH = 0;
    /**
     * Turns 1 right-90-Degrees at its Right
     */
    final static int WALK_RIGHT = 1;
    /**
     * Turns 2 right-90-Degrees (180) at its Back
     */
    final static int WALK_BACK = 2;
    /**
     * Turns 3 right-90-Degrees (270) at its Left
     */
    final static int WALK_LEFT = 3;
}
