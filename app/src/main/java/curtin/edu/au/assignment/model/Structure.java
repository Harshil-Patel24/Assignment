package curtin.edu.au.assignment.model;

public abstract class Structure
{
    //Might need to make protected
    protected int imageID;

    public int getImageID(){ return imageID; }
    public abstract String toString();
}
