package curtin.edu.au.assignment.model;

public abstract class Structure
{
    //Might need to make protected
    protected int imageID;
    //Mostly used by the database in order to determine the type of structure
    protected String type;

    public int getImageID(){ return imageID; }
    public String getType(){ return type; }
    public abstract String toString();
}
