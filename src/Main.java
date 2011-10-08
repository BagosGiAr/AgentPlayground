
import java.util.Scanner;

import Playground.Playground;
import Playground.Agents.Agent;
import Playground.Agents.ProkaryotesAgent;
import Playground.Resources.NutriceResource;

/**
 * The Main body of Program, here the real Simulation is performed.
 * Generates the New Playground according to the given parameters,
 * Generates All Agents and Resources and Shares Coordinates Spots.
 * Also Generates a Control Menu for User interaction.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 */
public class Main
{	
    public static Playground playground;
    /**
	 * A Console Scanner, that is responsible for reading each user input.
	 * @uml.property  name="consoleScanner"
	 */
    public Scanner consoleScanner;

    /**
     * The main Constructor, it only generates the Console Scanner
     */
    public Main()
    {
        consoleScanner = new Scanner(System.in);
    }

    /**
     * Starts Simulate, Builds a Welcome Message, and Start the initiation,
     * The prompt the user menu for User interaction.
     * After all initiation, the simulation Starts and Exit Until Normal Death of the Bots
     * OR User Demand Exit.
     * 
     * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
     */
    @SuppressWarnings( {} )
    public void simulate()
    {
        // a dummy Variable, is used as a switch to check if the application flow
        // is right
        boolean feed = true;

        System.out.println("|| \t                                    \t||");
        System.out.println("|| \tWelcome to AgentPlayground Siulator \t||");
        System.out.println("|| \t                                    \t||");
        System.out.println("");
        System.out.println("");
        System.out.println("\t\t..Loading...");

        //The Simulator's Pre-Main Menu
        //A dummy switch that loops until User enter Valid Data
        while(feed)
        {
            System.out.println("1. Start Simulating");
            System.out.println("2. Exit Simulator");
            System.out.print("Your Choise: ");
            
            switch(consoleScanner.nextInt())
            {
                case 1: //Breaks the feed switch
                    feed = false;
                    break;
                case 2: //Terminates the application
                    terminate(true);
                    feed=false; //dummy
                    break;
                default: //in Case of Wrong Choise
                    System.out.println("\nPlease choose between 1,2 options\n");
                    feed = true;
            }
        }

        System.out.println("\nPlease Enter the population size:");
        
        int x = 0; //Dummy variables, are going to use for initiation purposes.

        feed = true;
        //A dummy switch that loops until User enter Valid Data
        while (feed)
        {
            System.out.print("Population Size ( >60 ) \t: ");
            //Scans for an integer inputed number from console
            x = consoleScanner.nextInt();
            System.out.println("");//Dummy Line-Space
            if (x >=60 )
            {
                feed = false; //Turn the Switch off to stop the loop
            }
        }
        
        System.out.println("\t\t..Loading...");
        
        //initiate, that's going to take a while
        initiation(x);
        System.out.println("\t\t..Loding Done..!");
        System.out.println("\t\t..Simulating..!");

        //The Simulator Main Menu
        //A dummy switch that loops until All NutriceResource Eclipse, or Demand it
        while( !terminate(false) )
        {
            System.out.println("1. Printout Playground's instance.");
            System.out.println("2. Generate Playground's GUI instance.");
            System.out.println("3. Status of Agents.");
            System.out.println("4. Exit.");
            System.out.print("Your Choise: ");

            switch(consoleScanner.nextInt())
            {
                case 1: // Prints the state of the playground in a timely manner
                    playground.printInstance();
                    System.out.println("\n\nType Ok to continue..");
//                    consoleScanner.next();
//                    //Force a Clear Screen..
//                    for(int i=0; i<100;  i++, System.out.println(""));
                    break;
                    
                case 2:
                	try
                	{
                		//Enabling
                		playground.enableGUI(true);
                	}
                	catch (Exception e)
                	{
						// TODO: handle exception
                		e.printStackTrace();
					}
                	
                	break;
                	
                case 3: //prints Agent Consumed Resources Status
                    System.out.println("Agents:\n");
                    for(int i=0; i<playground.getHeight(); i++)
                    {
                        for(int j=0; j<playground.getWidth(); j++)
                        {
                            //checks if there is any agent
                            try
                            {
                                //Assign and Point an Agent that Runs in the Playground
                                Agent dummyAgent = (Agent) playground.queryObjectAt( i, j);
                                System.out.println("Agent " + dummyAgent.getAgentName() +" :");
                                System.out.println( "\tResource Consumed:" + dummyAgent.getConsumedResourcesNum() );
                            }
                            catch(Exception e)// Do Nothing..
                            {
                                continue;
                            }
                        }
                    }
//                    System.out.println("\n\nType Ok to continue..");
//                    consoleScanner.next();
                    //Force a Clear Screen..
//                    for(int i=0; i<100;  i++, System.out.println(""));
                    break;
                    
                case 4: //Terminates the application
                    terminate(true);
                    break;
                    
                default: //in Case of Wrong Choice 
                    System.out.println("\nPlease ensure the bound of your choises\n");
            }
        }
    }

    /**
     * Initiate all Vital objects for the start of Simulation.
     * This initiation is used to initiate a 2-D Playground, with its "Running"
     * objects.
     * @param x X,Y-Axis Width of the Playground and the population size
     * 
      * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
     */
    public void initiation( int x)
    {
        try
        {
            System.out.println("..Bulding Playground...");
            //The Playground of the Simulator
            playground = new Playground(x, x);
            
            System.out.println("..Generating Resources...");
            //Resources Generation..
            for(int i=0; i<( ( Math.random()*x)*(Math.random()*x) ); i++)
            {
                playground.getResourceGenerator().generate(( new NutriceResource("Resource_"+i, ( (int) Math.random() * 100 ) ) ) );
            }
            
            System.out.println("..Generating Agents...");
            //Agents Generation..
            for(int i=0; i<x; i++)
            {
                playground.getAgentGenerator().generate((new ProkaryotesAgent("Agent_"+i, playground,  ( (int) (Math.random() * 1000) ),  ( (int) (Math.random() * 10) ))));
            }
//            for(int i=0; i<( ( Math.random()*x)*(Math.random()*y) ); i++)
//            {
//            	playground.addNewGeneratedObject( new NutriceResource("Resource_"+i, ( (int) Math.random() * 100 ) ) );
//            }
//            
//            System.out.println("..Generating Agents...");
//            //Agents Generation..
//            for(int i=0; i<x; i++)
//            {
//            	playground.addNewGeneratedObject(new ProkaryotesAgent("Agent_"+i, playground,  ( (int) (Math.random() * 1000) ),  ( (int) (Math.random() * 10) )));
//            }
            
            
            
        }
        catch(Exception e) { e.printStackTrace();}
    }

    /**
     * Exit and Terminate the Simulator
     * @param exitSwitch
     * @return 
     */
    public boolean terminate(boolean exitSwitch)
    {
        if(exitSwitch)
        {
            System.out.println("Simulation Terminated!");
            System.exit(0);
            return true;
        }
        else if(playground != null)
        {
//            if(playground.resources <= 0)
//            {
//                System.out.println( "No more Resources to Serve.." );
//                terminate(true);
//                return true;
//            }
//            else
//            {
//                return false;
//            }
        	return false;
        }
        else
        {
            System.out.println("Playground is Null...???"); //Dummy For Debug
            return true;
        }
    }

    /**
     * @param args the command line arguments
     * 
      * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
     */
    public static void main( String[] args )
    {
        try
        {
            //Builds a new Thread, and fire it to start.
            //Parametres: A new Runnable body, and a Thread title.
            new Thread(
                    new Runnable() //The runnable part of the new Thread
                    {

                        public void run()
                        {
                            new Main().simulate(); //Start the Simulation
                        }
                    }, "AgentPlayground").run();
        }
        catch (Exception e)
        {
            // shows an error when there is a problem in the current thread
            e.printStackTrace();
        }
    }
}
