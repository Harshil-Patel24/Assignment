package curtin.edu.au.assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
        int rand = ( ( int )Math.random() ) % 4;
        switch( rand )
        {
            case 0:
                structure = new Land( StructureData.DRAWABLES[24] );
                break;
            case 1:
                structure = new Land( StructureData.DRAWABLES[25] );
                break;
            case 2:
                structure = new Land( StructureData.DRAWABLES[26] );
                break;
            case 3:
                structure = new Land( StructureData.DRAWABLES[27] );
                break;
            default:
                structure = new Land( StructureData.DRAWABLES[24] );
                break;
        }
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
