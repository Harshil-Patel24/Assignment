package curtin.edu.au.assignment.database;

import curtin.edu.au.assignment.model.*;
import curtin.edu.au.assignment.database.GameSchema.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class GameDataStore
{
    private GameData gd;
    private SQLiteDatabase db;

    /**
     * A default constructor
     */
    public GameDataStore(){}

    /**
     * This will load data from an already existing database
     */
    public void load( Context context )
    {
        gd  = GameData.getInstance();

        //Load database
        create( context );
        //Now read the data in
        GameCursor settingsCursor = new GameCursor( db.query( GameSettingsTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null ) );

        //Retrieve the settings object (this program only allows one settings object to be created)
        try
        {
            settingsCursor.moveToFirst();
            if( !settingsCursor.isAfterLast() )
            {
                Settings settings = settingsCursor.getSettings();
                gd.setSettings( settings );
                gd.setMoney( settingsCursor.getMoney() );
                gd.setGameTime( settingsCursor.getGameTime() );
            }
        }
        finally
        {
            settingsCursor.close();
        }

        Settings settings = gd.getSettings();
        int height = settings.getMapHeight();
        int width = settings.getMapWidth();

        //Now that the settings have been loaded, we can set the map
        gd.setMap();
        MapElement[][] map = gd.getMapElements();

        //Here is a cursor for all of the map elements
        GameCursor mapCursor = new GameCursor( db.query( MapElementTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null ) );

        //Try to get and store all of the map elements in our 2D array
        try
        {
            mapCursor.moveToFirst();
            while( !mapCursor.isAfterLast() )
            {
                int column = mapCursor.getColumn();
                int row = mapCursor.getRow();

                map[row][column] = mapCursor.getMapElement();
                mapCursor.moveToNext();
            }

        }
        finally
        {
            mapCursor.close();
        }
        //Set the map to the 2D array with the retrieved map elements
        gd.setMap( map );
    }

    /**
     * This will create a new database (or get an instance anyways)
     */
    public void create( Context context )
    {
        //Load database
        this.db = new GameDBHelper( context.getApplicationContext() ).getWritableDatabase();
    }

    /**
     * This will clear all of the data contained inside of the database
     */
    public void clear()
    {
        db.execSQL( "DELETE FROM " + GameSettingsTable.NAME );
        db.execSQL( "DELETE FROM " + MapElementTable.NAME );
    }

    /**
     * This is the method used to add new settings objects to the database (and/or update them)
     */
    public void addSettings( Settings settings )
    {
        gd = GameData.getInstance();
        //Put all of the values in a ContentValues object
        ContentValues cv = new ContentValues();
        cv.put( GameSettingsTable.Cols.MAP_WIDTH, settings.getMapWidth() );
        cv.put( GameSettingsTable.Cols.MAP_HEIGHT, settings.getMapHeight() );
        cv.put( GameSettingsTable.Cols.INITIAL_MONEY, settings.getInitialMoney() );
        cv.put( GameSettingsTable.Cols.FAMILY_SIZE, settings.getFamilySize() );
        cv.put( GameSettingsTable.Cols.SHOP_SIZE, settings.getShopSize() );
        cv.put( GameSettingsTable.Cols.SALARY, settings.getSalary() );
        cv.put( GameSettingsTable.Cols.TAX_RATE, settings.getTaxRate() );
        cv.put( GameSettingsTable.Cols.SERVICE_COST, settings.getServiceCost() );
        cv.put( GameSettingsTable.Cols.HOUSE_BUILDING_COST, settings.getHouseBuildingCost() );
        cv.put( GameSettingsTable.Cols.COMMERCIAL_BUILDING_COST, settings.getCommBuildingCost() );
        cv.put( GameSettingsTable.Cols.ROAD_BUILDING_COST, settings.getRoadBuildingCost() );
        cv.put( GameSettingsTable.Cols.MONEY, gd.getMoney() );
        cv.put( GameSettingsTable.Cols.GAME_TIME, gd.getGameTime() );

        //Retrieve a GameSettingsTable cursor
        GameCursor cursor = new GameCursor( db.query( GameSettingsTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null ) );

        /**
         * This next part ensures that no more than one settings object is stored in this database
         * */
        //If there are no already existing settings objects then insert
        if( cursor.getCount() == 0 )
        {
            db.insert( GameSettingsTable.NAME, null, cv );
        }
        //If one already exists, then update it
        else
        {
            String[] whereValue = { String.valueOf( settings.getMapWidth() ) };
            db.update( GameSettingsTable.NAME, cv, GameSettingsTable.Cols.MAP_WIDTH + " = ?", whereValue );
        }
    }

    /**
     * This will update the whole map and settings
     */
    public void update()
    {
        gd = GameData.getInstance();

        ContentValues cv = new ContentValues();

        //Add the stored statistics
        cv.put( GameSettingsTable.Cols.MONEY, gd.getMoney() );
        cv.put( GameSettingsTable.Cols.GAME_TIME, gd.getGameTime() );

        //Update the game settings table
        String[] whereValue = { String.valueOf( gd.getSettings().getMapWidth() ) };
        db.update( GameSettingsTable.NAME, cv, GameSettingsTable.Cols.MAP_WIDTH + " = ?", whereValue );

        MapElement[][] map = gd.getMapElements();
        //Update the settings object
        addSettings( gd.getSettings() );

        //Update all of the elements in the map
        for( int ii = 0; ii < map.length; ii++ )
        {
            for( int jj = 0; jj < map[ii].length; jj++ )
            {
                updateMapElement( map[ii][jj], ii, jj );
            }
        }
    }

    /**
     * This updates a single map element in the database
     */
    public void updateMapElement( MapElement element, int row, int col )
    {
        //Converts the bitmap into a byte array to store into the database
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bm = element.getImage();
        bm.compress( Bitmap.CompressFormat.PNG, 70, stream );
        byte[] ba = stream.toByteArray();

        //Add all of the values to a ContentValues object
        ContentValues cv = new ContentValues();
        cv.put( MapElementTable.Cols.COLUMN_INDEX, col );
        cv.put( MapElementTable.Cols.ROW_INDEX, row );
        cv.put( MapElementTable.Cols.OWNER, element.getOwnerName() );
        cv.put( MapElementTable.Cols.TYPE, element.getStructure().getType() );
        cv.put( MapElementTable.Cols.STRUCTURE_IMAGE, element.getStructure().getImageID() );
        cv.put( MapElementTable.Cols.IMAGE, ba );

        //Either update it if there is an already existing entry for that row and column otherwise insert it
        if( db.update( MapElementTable.NAME, cv,MapElementTable.Cols.COLUMN_INDEX + " = " + String.valueOf( col ) + " AND " + MapElementTable.Cols.ROW_INDEX + " = " + String.valueOf( row ), null ) == 0 )
        {
            db.insert( MapElementTable.NAME, null, cv );
        }

    }

    /**
     * This will update the status stats into the database
     */
    public void updateStatus()
    {
        addSettings( gd.getSettings() );
        ContentValues cv = new ContentValues();

        cv.put( GameSettingsTable.Cols.MONEY, gd.getMoney() );
        cv.put( GameSettingsTable.Cols.GAME_TIME, gd.getGameTime() );


        String[] whereValue = { String.valueOf( gd.getSettings().getMapWidth() ) };
        db.update( GameSettingsTable.NAME, cv, GameSettingsTable.Cols.MAP_WIDTH + " = ?", whereValue );
    }

    /**
     * Returns the number of map elements
     */
    public int getMapElementCount()
    {
        int count;

        Cursor cursor = new GameCursor( db.query( MapElementTable.NAME,
            null,
            null,
            null,
            null,
            null,
            null ) );

        count = cursor.getCount();

        return count;
    }

    /**
     * Returns the number of settings elements
     */
    public int getSettingsElementCount()
    {
        int count;

        Cursor cursor = new GameCursor( db.query( GameSettingsTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null ) );

        count = cursor.getCount();

        return count;
    }
}

