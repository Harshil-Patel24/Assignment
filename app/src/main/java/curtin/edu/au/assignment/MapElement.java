package curtin.edu.au.assignment;

import android.graphics.Bitmap;

public class MapElement
{
    private Structure structure;
    private Bitmap image;
    private String ownerName;

    public MapElement( Structure struc, Bitmap img, String name )
    {
        structure = struc;
        image = img;
        ownerName = name;
    }

    public MapElement()
    {
        structure = new Land();

    }

    public Structure getStructure(){ return structure; }
    public Bitmap getImage(){ return image; }
    public String getOwnerName(){ return ownerName; }

    public void setStructure( Structure struc )
    {
        structure = struc;
    }

    public void setImage( Bitmap img )
    {
        image = img;
    }

    public void setOwnerName( String name )
    {
        ownerName = name;
    }
}
