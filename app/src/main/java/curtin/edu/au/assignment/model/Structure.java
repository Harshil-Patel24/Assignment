package curtin.edu.au.assignment.model;

public abstract class Structure
{
    protected int imageID;
    //Mostly used by the database in order to determine the type of structure
    protected String type;
    protected  int cost;

    public Structure(){}

    /**
     * Accessors for the fields
     */
    public int getImageID(){ return imageID; }
    public String getType(){ return type; }
    public int getCost(){ return cost; }

    /**
     * This forces the subclasses have a to string method
     */
    public abstract String toString();
}
