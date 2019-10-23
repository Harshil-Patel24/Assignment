package curtin.edu.au.assignment.model;

import curtin.edu.au.assignment.R;

/**
 * This is not really structure but this also has very similar functionality to a structure
 */
public class Destroy extends Structure
{
    public Destroy()
    {
        imageID = R.drawable.ic_bomb;
        type = "DESTROY";
        cost = 0;
    }

    public String toString()
    {
        return "Destroy";
    }
}
