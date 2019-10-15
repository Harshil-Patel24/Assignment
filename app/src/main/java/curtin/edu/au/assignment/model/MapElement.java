package curtin.edu.au.assignment.model;

import java.util.Random;

public class MapElement
{
    private Structure structure;
    private Land land;
    private String ownerName;

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
                land = new Land( StructureData.DRAWABLES[24] );
                break;
            case 1:
                land = new Land( StructureData.DRAWABLES[25] );
                break;
            case 2:
                land = new Land( StructureData.DRAWABLES[26] );
                break;
            case 3:
                land = new Land( StructureData.DRAWABLES[27] );
                break;
            default:
                land = new Land( StructureData.DRAWABLES[24] );
                break;
        }
    }

    public Structure getStructure(){ return structure; }
    public Land getLand(){ return land; }
    //public Bitmap getImage(){ return image; }
    public String getOwnerName(){ return ownerName; }

    public void setStructure( Structure struc )
    {
        structure = struc;
    }
/*
    public void setImage( Bitmap img )
    {
        image = img;
    }
*/
    public void setOwnerName( String name )
    {
        ownerName = name;
    }
}
