package curtin.edu.au.assignment.model;

public class Residential extends Structure
{
    public Residential(int id)
    {
        imageID = id;
        type = "RESIDENTIAL";
    }
    public String toString(){ return "Residential"; }
}