package curtin.edu.au.assignment.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import curtin.edu.au.assignment.R;

public abstract class Structure
{
    //Might need to make protected
    protected int imageID;
    //protected Bitmap image;
    //Mostly used by the database in order to determine the type of structure
    protected String type;
    protected  int cost;

    public Structure()
    {
        //image = BitmapFactory.decodeResource( GameData.getInstance().getMapContext().getResources(), imageID );
    }

    public int getImageID(){ return imageID; }
    public String getType(){ return type; }
    public int getCost(){ return cost; }
    //public Bitmap getImage(){ return image; }
    public abstract String toString();
}
