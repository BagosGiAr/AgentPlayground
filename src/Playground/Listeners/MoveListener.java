/**
 * 
 */
package Playground.Listeners;

import java.util.EventListener;

import Playground.Agents.Agent.MoveAction;

/**
 * A class describes all actions of a BiDimensional object have to implement
 * to move in the 2D PLayground
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr 
 */
public abstract class MoveListener implements EventListener
{
    abstract public void actionPerformed( MoveAction e );
}
