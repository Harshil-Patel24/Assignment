package curtin.edu.au.assignment.model;

public class Commercial extends Structure
{
    public Commercial(int id)
    {
        imageID = id;
        type = "COMMERCIAL";
        cost = GameData.getInstance().getSettings().getCommBuildingCost();
    }
    public String toString(){ return "Commercial"; }
}
