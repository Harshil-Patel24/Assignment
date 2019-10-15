package curtin.edu.au.assignment.model;

public class Road extends Structure
{
    public Road(int id)
    {
        imageID = id;
        type = "ROAD";
    }
    public String toString(){ return "Road"; }
}
