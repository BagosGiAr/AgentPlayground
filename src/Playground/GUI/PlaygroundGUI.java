/**
 * 
 */
package Playground.GUI;

import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import Playground.AgentHost;
import Playground.BiDimensional;
import Playground.Playground;
import Playground.Agents.Agent;
import Playground.Agents.AgentInterface;
import Playground.Resources.NutriceResource;

/**
 * Just an instance of the {@link #Playground()} with a Gui representation
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
 * 
 */
public class PlaygroundGUI extends Frame implements ActiveEvent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The Playground of this GUI   {@link #Playground} 
	 * @uml.property  name="playground"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Playground playground;
	/**
	 * The Main Canvas of the Frame
	 * @see  #PlaygrooundCanvas
	 * @uml.property  name="mainCanvas"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="this$0:Playground.GUI.PlaygroundGUI$PlagroundCanvas"
	 */
	private PlagroundCanvas mainCanvas;
	/**
	 * Report Window
	 * @see  #ReportWin
	 * @uml.property  name="reportWin"
	 * @uml.associationEnd  inverse="this$0:Playground.GUI.PlaygroundGUI$ReportWin"
	 */
	private ReportWin reportWin;
	/**
	 * a boolean Switch if the Window is Active//Alive
	 * @uml.property  name="isAlive"
	 */
	private boolean isAlive;
	
	/**
	 * Just an instance of the {@link #Playground()} with a Gui representation
	 * 
	 * @param p
	 */
	public PlaygroundGUI( Playground p )
	{
		super("Agent - Playground");
		playground = p;
		setSize(600, 475);
		setBackground(Color.white);
		setFont(new Font("Monospaced", Font.PLAIN, 12));
		setResizable(false);
		
		mainCanvas = new PlagroundCanvas(this);
		add(mainCanvas, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing( WindowEvent evt )
			{
				close();
			}
			
			public void windowClosed( WindowEvent evt )
			{
				close();
			}
			
			public void windowOpened( WindowEvent evt )
			{
				mainCanvas.init();
				reportWin = new ReportWin();
			}
		});
		
		pack();
		setVisible(true);
		isAlive = true;
		repaint();
	}
	
	/**
	 * A Panel class it's responsible for all drawings inside the canvas of the
	 * main Frame. Such as Agents and resources representations.
	 * 
	 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 * 
	 */
	final protected class PlagroundCanvas extends Panel
	{
		int cellSize;
		int cols;
		int rows;
		int[][] cellContents;
		private Image imageWorker;
		private Graphics graphicsWorker;
		private Frame frameParent;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * 
		 * A Panel class it's responsible for all drawings inside the canvas of
		 * the
		 * main Frame. Such as Agents and resources representations.
		 * 
		 */
		protected PlagroundCanvas( Frame frame )
		{
			frameParent = frame;
			setSize(frameParent.getSize());
			
			setBackground(Color.WHITE);
			setFont(new Font("Monospaced", Font.PLAIN, 12));
			setVisible(true);
			
		}
		
		/**
		 * 
		 * Synchronized panel paint
		 * 
		 */
		synchronized final public void paint( Graphics g )
		{
			if ( imageWorker == null )
			{
				g.setColor(Color.white);
				g.fillRect(0, 0, getSize().width, getSize().height);
			}
			else
			{
				g.drawImage(imageWorker, 0, 0, this);
			}
		}
		
		public final void update( Graphics g )
		{
			paint(g);
		}
		
		/**
		 * Initialize Content
		 */
		synchronized final public void init( )
		{
			imageWorker = createImage(getWidth(), getHeight());
			graphicsWorker = imageWorker.getGraphics();
			graphicsWorker.setColor(Color.white);
			graphicsWorker.fillRect(0, 0, getWidth(), getHeight());
			graphicsWorker.setColor(Color.black);
			graphicsWorker.drawLine(0, getHeight() - 19, getWidth(),
					getHeight() - 19);
			cellSize = getWidth() / 60;
			cols = 60;
			rows = (getHeight() - 20) / cellSize;
			cellContents = new int[rows + 2][cols + 2];
			for ( int i = 0; i < rows + 2; i++ )
			{
				cellContents[i][0] = 9999;
				cellContents[i][cols + 1] = 9999;
			}
			for ( int j = 0; j < cols + 2; j++ )
			{
				cellContents[0][j] = 9999;
				cellContents[rows + 1][j] = 9999;
			}
		}
		
		final public Dimension getPreferredSize( )
		{
			return new Dimension(getWidth(), getHeight());
		}
		
		/**
		 * Redraw all Contents on the Emulator Frame
		 * 
		 * @param g
		 * @param bdAgents
		 * @param bdsResources
		 */
		final public void redrawAll( BiDimensional[] bdAgents,
				BiDimensional[] bdsResources )
		{
			// clear scr
			try
			{
				graphicsWorker.setColor(Color.WHITE);
				graphicsWorker.clearRect(0, 0, mainCanvas.getWidth(),
						mainCanvas.getHeight());
			}
			catch ( Exception e )
			{
				
			}
			
			graphicsWorker.setColor(Color.BLUE);
			for ( int i = 0; i < bdsResources.length; i++ )
			{
				try
				{
					// A Bug in Resource BiCordinates Solved by reversing them
					drawResource(bdsResources[i].getAxisY(),
							bdsResources[i].getAxisX());
				}
				catch ( Exception e )
				{
				}
			}
			graphicsWorker.setColor(Color.RED);
			for ( int i = 0; i < bdAgents.length; i++ )
			{
				try
				{
					drawAgent(((Agent) bdAgents[i]).getStaringAt(),
							bdAgents[i].getAxisX(), bdAgents[i].getAxisY());
				}
				catch ( Exception e )
				{
				}
			}
			
			repaint();
		}
		
		/**
		 * Draws an Agent depending its Position and Facing status
		 * 
		 * @param facing
		 * @see #Bidemsional
		 * @param row
		 * @param col
		 */
		final private void drawAgent( int facing, int row, int col )
		{
			int EAST = (col - 1) * cellSize + 1;
			int SOUTH = (row - 1) * cellSize + 1;
			int WEST = EAST + cellSize - 2;
			int NORTH = SOUTH + cellSize - 2;
			int hor = (EAST + WEST) / 2; // Horizontal
			int ver = (NORTH + SOUTH) / 2; // Vertical
			switch ( facing )
			{
				case 0:
				{
					graphicsWorker.drawLine(EAST, NORTH, EAST, SOUTH - 1);
					graphicsWorker.drawLine(EAST, ver, WEST - 1, ver);
					break;
				}
				case 1:
				{
					graphicsWorker.drawLine(EAST, SOUTH - 1, WEST - 1,
							SOUTH - 1);
					graphicsWorker.drawLine(hor, NORTH, hor, SOUTH - 1);
					break;
				}
				case 2:
				{
					graphicsWorker.drawLine(WEST - 1, NORTH, WEST - 1,
							SOUTH - 1);
					graphicsWorker.drawLine(EAST, ver, WEST - 1, ver);
					break;
				}
				case 3:
				{
					graphicsWorker.drawLine(EAST, NORTH, WEST - 1, NORTH);
					graphicsWorker.drawLine(hor, NORTH, hor, SOUTH - 1);
					break;
				}
			}
		}
		
		/**
		 * Draw a Resource Depending its position
		 * 
		 * @param g
		 * @param row
		 * @param col
		 */
		final private void drawResource( int col, int row )
		{
			graphicsWorker.drawOval((col - 1) * cellSize + 1, (row - 1)
					* cellSize + 1, cellSize - 1, cellSize - 1);
		}
	}
	
	/**
	 * The Report Window. It holds the Summary report and Statistics of the
	 * Simulation
	 * 
	 * @author Pappas Evangelos - papas.evagelos@gmail.com - BagosGiAr
	 * 
	 */
	final protected class ReportWin extends Frame
	{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 
		 */
		private int nextAvg = 100;
		private double[] avgEnergList = new double[101];
		private double[] avgSpeedList = new double[101];
		private double bestEnergScore = 0;
		private double bestSpeedScore = 0;
		
		private String[] stats = new String[500];
		private int statsCt;
		
		private TextArea list;
		protected int currentYear;
		private static final String blanks = "                    ";
		
		/**
		 * The Report Window. It holds the Summary report and Statistics of the
		 * Simulation
		 * 
		 */
		protected ReportWin( )
		{
			super("Statistics");
			currentYear = 0;
			list = new TextArea("", 20, 120, TextArea.SCROLLBARS_BOTH);
			add(list, BorderLayout.CENTER);
			Font f = new Font("Monospaced", Font.PLAIN, 12);
			Label lb = new Label(
					"*  YEAR   POPULATION   MUTATION      AVERAGE SCORE Enrg Spd      HIGH SCORE Enrg Spd      100-YEAR AVERAGE Enrg Spd  *");
			lb.setFont(f);
			add(lb, BorderLayout.NORTH);
			list.setFont(f);
			list.setEditable(false);
			list.setBackground(Color.white);
			setBackground(Color.lightGray);
			pack();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(screenSize.width - 10 - getSize().width, 25);
			setVisible(true);
		}
		
		/**
		 * Report Generator and printer.
		 * 
		 * @param year
		 * @param avgScore
		 * @param highScore
		 */
		final protected void doReport( int year, int population,
				double avgEnergScore, double avgSpeedScore,
				double hiEnergScore, double hiSpeedScore )
		{
			if ( currentYear < year )
			{
				currentYear = year;
			}
			boolean isBest = false;
			
			isBest = (hiEnergScore > bestEnergScore ? false : true);
			bestEnergScore = (hiEnergScore > bestEnergScore ? hiEnergScore
					: bestEnergScore);
			bestSpeedScore = (hiSpeedScore > bestSpeedScore ? hiSpeedScore
					: bestSpeedScore);
			
			String csEnerg = "";
			String csSpeed = "";
			if ( year < 100 )
			{
				avgEnergList[year] = avgEnergScore;
				avgSpeedList[year] = avgSpeedScore;
			}
			else
			{
				avgEnergList[nextAvg] = avgEnergScore;
				avgSpeedList[nextAvg] = avgSpeedScore;
				nextAvg++;
				if ( nextAvg > 100 )
				{
					nextAvg = 1;
				}
				double tempEnerg = 0;
				double tempSpeed = 0;
				for ( int i = 1; i <= 100; i++ )
				{
					tempEnerg += avgEnergList[i];
					tempSpeed += avgSpeedList[i];
				}
				csEnerg = "" + (((int) (tempEnerg * 100)) / 10000.0);
				csSpeed = "" + (((int) (tempSpeed * 100)) / 10000.0);
			}
			if ( year <= 100 || (year <= 1000 && year % 10 == 0)
					|| year % 100 == 0 )
			{
				String ys = "" + year;
				String pop = "" + population;
				String mut = "" + Agent.Chromosome.MUTATION_RATE;
				String dsEnerg = "" + (((int) (avgEnergScore * 1000)) / 1000.0);
				String dsSpeed = "" + (((int) (avgSpeedScore * 1000)) / 1000.0);
				String hsEnerg = "" + hiEnergScore;
				String hsSpeed = "" + hiSpeedScore;
				ys = blanks.substring(0, 6 - ys.length()) + ys;
				pop = blanks.substring(0, 6 - pop.length()) + pop;
				mut = blanks.substring(0, 14 - mut.length()) + mut;
				dsEnerg = blanks.substring(0, 14 - dsEnerg.length()) + dsEnerg;
				dsSpeed = blanks.substring(0, 14 - dsSpeed.length()) + dsSpeed;
				if ( isBest )
				{
					hsEnerg = " *" + blanks.substring(0, 11 - hsEnerg.length())
							+ hsEnerg;
					hsSpeed = " *" + blanks.substring(0, 11 - hsSpeed.length())
							+ hsSpeed;
				}
				else
				{
					hsEnerg = blanks.substring(0, 13 - hsEnerg.length())
							+ hsEnerg;
					hsSpeed = blanks.substring(0, 13 - hsSpeed.length())
							+ hsSpeed;
				}
				list.append(ys + "|" + pop + "|" + mut + "|" + dsEnerg + "//"
						+ dsSpeed + "|" + hsEnerg + "//" + hsSpeed + "|"
						+ "            " + csEnerg + " " + csSpeed + "\n");
			}
		}
		
		/**
		 * Clears the List report.
		 */
		final protected void clear( )
		{
			list.setText("");
			nextAvg = 100;
			bestEnergScore = 0;
			bestSpeedScore = 0;
		}
	}
	
	/**
	 * 
	 * Dispose and close all GUI of this playground
	 * 
	 */
	public void close( )
	{
		try
		{
			isAlive = false;
			reportWin.dispose();
			dispose();
		}
		catch ( Exception e )
		{
			// Do nothing
		}
		
	}
	
	/**
	 * Gets true if the Window is alive
	 * @return
	 * @uml.property  name="isAlive"
	 */
	final public boolean isAlive( )
	{
		return isAlive;
	}
	
	/**
	 * @param playground  the playground to set
	 * @uml.property  name="playground"
	 */
	public void setPlayground( Playground playground )
	{
		this.playground = playground;
	}
	
	/**
	 * @return  the playground
	 * @uml.property  name="playground"
	 */
	public Playground getPlayground( )
	{
		return this.playground;
	}
	
	/**
	 * This method is called by {@link #Playground} 's Timer so it will redraw
	 * all Entities in this playground.
	 * 
	 * @see java.awt.ActiveEvent#dispatch()
	 */
	@Override
	public void dispatch( )
	{
		// An Iterator to Parse the Playground
		// Iterator<BiDimensional> iterator = playground.biDsObjects.iterator();
		// Temporary Array Lists
		ArrayList<BiDimensional> agents = new ArrayList<BiDimensional>();
		ArrayList<BiDimensional> resources = new ArrayList<BiDimensional>();
		// Variables to monitor Mutations and etc
		double hiEnergScore = 0;
		double hiSpeedScore = 0;
		double avgEnergScoreCounter = 0;
		double avgSpeedScoreCounter = 0;
		int population = 0;
		
		// Seeking for objects in the playground
		for ( int i = 0; i < playground.getHeight(); i++ )
		{
			for ( int j = 0; j < playground.getWidth(); j++ )
			{
				try
				{
					Object currentObj = playground.queryObjectAt(j, i);
					if ( currentObj instanceof Agent )
					{
						avgEnergScoreCounter += ((Agent) currentObj)
								.getAgentEnergy();
						avgSpeedScoreCounter += ((Agent) currentObj)
								.getAgentSpeed();
						hiEnergScore = (((Agent) currentObj).getAgentEnergy() > hiEnergScore ? ((Agent) currentObj)
								.getAgentEnergy() : hiEnergScore);
						hiSpeedScore = (((Agent) currentObj).getAgentSpeed() > hiSpeedScore ? ((Agent) currentObj)
								.getAgentSpeed() : hiSpeedScore);
						
						agents.add((AgentInterface) currentObj);
					}
					if ( currentObj instanceof NutriceResource )
					{
						resources.add((NutriceResource) currentObj);
					}
				}
				catch ( Exception e ) // Do Nothing
				{
				}
			}
		}
		population = agents.size();
		// while(iterator.hasNext())
		// {
		// BiDimensional currentBD = iterator.next();
		//
		// if(currentBD instanceof Agent)
		// {
		// agents.add( (AgentInterface) currentBD);
		// }
		// if( currentBD instanceof NutriceResource )
		// {
		// resources.add( (NutriceResource) currentBD );
		// }
		// }
		
		BiDimensional[] ageArr = new BiDimensional[agents.size()];
		BiDimensional[] resArr = new BiDimensional[resources.size()];
		
		mainCanvas.redrawAll(agents.toArray(ageArr), resources.toArray(resArr));
		repaint();
		if ( playground.getYear() > reportWin.currentYear )
		{
			reportWin.doReport(playground.getYear(), population,
					avgEnergScoreCounter / population, avgSpeedScoreCounter
							/ population, hiEnergScore, hiSpeedScore);
		}
	}
}
