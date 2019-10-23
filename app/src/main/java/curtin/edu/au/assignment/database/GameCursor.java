package curtin.edu.au.assignment.database;

import curtin.edu.au.assignment.model.*;
import curtin.edu.au.assignment.database.GameSchema.*;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GameCursor extends CursorWrapper
{
    public GameCursor( Cursor cursor )
    {
        super( cursor );
    }

    /**
     * Gets the settings object and returns it
     */
    public Settings getSettings()
    {
        GameData gd = GameData.getInstance();
        Settings settings = new Settings();

        settings.setMapWidth( getInt( getColumnIndex( GameSettingsTable.Cols.MAP_WIDTH ) ) );
        settings.setMapHeight( getInt( getColumnIndex( GameSettingsTable.Cols.MAP_HEIGHT) ) );
        settings.setInitialMoney( getInt( getColumnIndex( GameSettingsTable.Cols.INITIAL_MONEY ) ) );
        settings.setFamilySize( getInt( getColumnIndex( GameSettingsTable.Cols.FAMILY_SIZE ) ) );
        settings.setShopSize( getInt( getColumnIndex( GameSettingsTable.Cols.SHOP_SIZE ) ) );
        settings.setSalary( getInt( getColumnIndex( GameSettingsTable.Cols.SALARY ) ) );
        settings.setTaxRate( getDouble( getColumnIndex( GameSettingsTable.Cols.TAX_RATE ) ) );
        settings.setServiceCost( getInt( getColumnIndex( GameSettingsTable.Cols.SERVICE_COST ) ) );
        settings.setHouseBuildingCost( getInt( getColumnIndex( GameSettingsTable.Cols.HOUSE_BUILDING_COST ) ) );
        settings.setCommBuildingCost( getInt( getColumnIndex( GameSettingsTable.Cols.COMMERCIAL_BUILDING_COST ) ) );
        settings.setRoadBuildingCost( getInt( getColumnIndex( GameSettingsTable.Cols.ROAD_BUILDING_COST ) ) );
        gd.setGameTime( getInt( getColumnIndex( GameSettingsTable.Cols.GAME_TIME ) ) );
        gd.setMoney( getInt( getColumnIndex( GameSettingsTable.Cols.MONEY ) ) );

        return settings;
    }

    /**
     * Creates a map element using the values in the cursor
     */
    public MapElement getMapElement()
    {
        MapElement element = new MapElement();
        Structure structure = null;
        String type = getString( getColumnIndex( MapElementTable.Cols.TYPE ) );
        int image = getInt( getColumnIndex( MapElementTable.Cols.STRUCTURE_IMAGE ) );

        if( type.equals( "COMMERCIAL" ) )
        {
            structure = new Commercial( image );
        }
        else if( type.equals( "RESIDENTIAL" ) )
        {
            structure = new Residential( image );
        }
        else if( type.equals( "ROAD" ) )
        {
            structure = new Road( image );
        }
        else
        {
            structure = new Land( image );
        }

        element.setStructure( structure );
        element.setOwnerName( getString( getColumnIndex( MapElementTable.Cols.OWNER ) ) );

        //Must get bitmap by converting it from a byte array
        byte[] ba = getBlob( getColumnIndex( MapElementTable.Cols.IMAGE ) );
        Bitmap bm = BitmapFactory.decodeByteArray( ba, 0, ba.length );

        element.setImage( bm );

        return element;
    }

    /**
     * The following are accessors for some fields
     */
    public int getColumn()
    {
        return getInt( getColumnIndex( MapElementTable.Cols.COLUMN_INDEX ) );
    }
    public int getRow()
    {
        return getInt( getColumnIndex( MapElementTable.Cols.ROW_INDEX ) );
    }
    public int getMoney(){ return getInt( getColumnIndex( GameSettingsTable.Cols.MONEY ) ); }
    public int getGameTime(){ return getInt( getColumnIndex( GameSettingsTable.Cols.GAME_TIME ) ); }

}
