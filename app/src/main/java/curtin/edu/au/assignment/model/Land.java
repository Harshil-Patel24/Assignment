package curtin.edu.au.assignment.model;

/**
 * This is the default structure that map element will be set to
 * This is also what a MapElement's structure will return to when it is destroyed
 */
public class Land extends Structure
{
    public Land( int id )
    {
        imageID = id;
        type = "LAND";
        cost = 0;
    }
    public String toString(){ return "Land"; }
}
