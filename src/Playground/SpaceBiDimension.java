
package Playground;


/**
 * A 2-D Space implementation. Simulating the laws of a BiDimention Space World.
 * This space tracks ( pointers ) all of its BiDimentional Objects.
 * 
 * @author Pappas Evangelos - papas.evagelos@gmail.com
 */
public class SpaceBiDimension
{
    /**
     * The Whole 2D-Map. A 2D Array implementing the width and height of the 
     * 2D space.  
     */
    private static BiDimensional[][] biDimensional;

    /**
     * 
     * @see #setSize(double, double)
     * @param width
     * @param height
     */
    public SpaceBiDimension(int width, int height)
    {
        setSize(width, height);
    }

    /**
     * 
     * @return int
     */
    public int getWidth()
    {
        return biDimensional[0].length;
    }

    /**
     *
     * @return int
     */
    public int getHeight()
    {
        return biDimensional.length;
    }

    /**
     * Initialize Size Dimension and Values ( are set Null)
     * @param width
     * @param height
     */
    public void setSize( double width, double height )
    {
        biDimensional = new BiDimensional[ (int) width][ (int) height];
//        for(int i=0; i<biDimensional.length; i++)
//        {
//            biDimensional[i] = null;
//            for(int j=0; j<biDimensional[0].length; j++)
//            {
//                biDimensional[j] = null;
//            }
//        }
    }

    /**
     * Gets Object in given coordinates
     * @param x X-Axis position
     * @param y Y-Axis position
     * @return BiDimensional OR null
     * @throws ArrayIndexOutOfBoundsException
     */
    public BiDimensional getBiDimensional(int x, int y) throws ArrayIndexOutOfBoundsException
    {
        //if These Cordinates is out of range, returns null
        if(biDimensional.length <= x || biDimensional[0].length <= y)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        else
        {
            return biDimensional[x][y];
        }
    }

    /**
     * Place this BiDimensional object in the given position
     * @param bd
     * @param x
     * @param y
     */
    public void placeBiDimensional(BiDimensional bd, int x, int y)
    {
        biDimensional[x][y] = bd;
        bd.setAxisX(x);
        bd.setAxisY(y);
    }
    
    
    /**
     * This method is used to remove BiDimensional Object from the Playground. 
     * Simply sets the pointer to null.
     * @param x
     * @param y
     */
    public boolean removeBiDimensionalAt(int x,int y)
    {
        try
        {
        	placeBiDimensional( null , x, y);
        	return true;
        }
        catch(Exception e) //in any Exception return False
        {
        	return false;
        }
    }
    
    /**
     * This method is used to remove Object from the SpaceBiDimesion. Simply set
     * their value to null, and remove from the Vectors.
     * @see #removeBiDimensionalAt(int, int)
     * @param bds
     * @return 
     */
    public boolean removeBiDimensional(BiDimensional bds)
    {
        try
        {
        	placeBiDimensional( null , bds.getAxisX(), bds.getAxisY());
            return true;
        }
        catch(Exception e) //in any Exception return False
        {
            return false;
        }
    }
}
