package curtin.edu.au.assignment.model;

public class Land extends Structure
{
    public Land( int id )
    {
        imageID = id;
        type = "LAND";
    }
    public String toString(){ return "Land"; }
}
