package curtin.edu.au.assignment.model;

import curtin.edu.au.assignment.R;

public class Destroy extends Structure
{
    public Destroy()
    {
        imageID = R.drawable.ic_bomb;
        type = "DESTROY";
    }

    public String toString()
    {
        return "Destroy";
    }
}
