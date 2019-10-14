package curtin.edu.au.assignment.model;

import android.graphics.Bitmap;

import java.util.Random;

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
        //Random numbers to make land terrain make use of different textures
        Random random = new Random();
        random.setSeed( System.currentTimeMillis() );
        random.setSeed( random.nextLong() );
        int rand = random.nextInt() % 4;
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
