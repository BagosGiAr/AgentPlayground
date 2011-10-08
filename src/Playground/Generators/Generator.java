
package Playground.Generators;

import Playground.BiDimensional;

/**
 * A Daemon Thread that Generate threads. Useful to be abstract for other kind 
 * of generators.
 * As in Each Generator, daemon property is been set. In case of playground's 
 * Termination, Generators are also automated killed.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 */
abstract public class Generator extends Thread
{
    /**
     * A static generatorGroup Class to Hold all Generators of the Simulator
     */
    static class GeneratorsGroup extends ThreadGroup
    {
        public GeneratorsGroup(String name)
        {
          super(name);
          //Setting the Generator into a daemon. As the Generator is not a basic
          //thread of the main flow, but lives to serve its child (Generations)
          //Should be set as daemon.
          setDaemon(true);
        }
    };
    
    /**
     * Constructor.
     * Creates a new Generator Group of threads.
     * So every Generator can monitor its Child threads.
     * @param thread
     */
    public Generator(Thread thread)
    {
        super(new GeneratorsGroup("Generators"), thread);
        thread.start();
        
    }
    
    abstract public boolean generate();
    abstract public boolean generate( BiDimensional bd );
}
