package curtin.edu.au.assignment.database;

import curtin.edu.au.assignment.model.*;
import curtin.edu.au.assignment.database.GameSchema.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GameDataStore
{
    private GameData gd;
    private SQLiteDatabase db;

    public GameDataStore(){}

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

        gd.setMap();
        MapElement[][] map = gd.getMapElements();

        GameCursor mapCursor = new GameCursor( db.query( MapElementTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null ) );

        try
        {
            mapCursor.moveToFirst();
            while( !mapCursor.isAfterLast() )
            {
                int column = mapCursor.getColumn();
                int row = mapCursor.getRow();

                map[row][column] = mapCursor.getMapElement();
            }

        }
        finally
        {
            mapCursor.close();
        }
        gd.setMap( map );
    }

    public void create( Context context )
    {
        gd  = GameData.getInstance();

        //this.db.execSQL( "DROP TABLE IF EXISTS " + GameSettingsTable.NAME );
        //this.db.execSQL( "DROP TABLE IF EXISTS " + MapElementTable.NAME );

        //Load database
        this.db = new GameDBHelper( context.getApplicationContext() ).getWritableDatabase();
    }

    public void clear()
    {
        db.execSQL( "DELETE FROM " + GameSettingsTable.NAME );
        db.execSQL( "DELETE FROM " + MapElementTable.NAME );
    }

    //Make sure this isn't adding a new settings entry everytime
    public void addSettings( Settings settings )
    {
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
        GameCursor cursor = new GameCursor( db.query( GameSettingsTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null ) );

        if( cursor.getCount() == 0 )
        {
            db.insert( GameSettingsTable.NAME, null, cv );
        }
        else
        {
            String[] whereValue = { String.valueOf( settings.getMapWidth() ) };
            db.update( GameSettingsTable.NAME, cv, GameSettingsTable.Cols.MAP_WIDTH + " = ?", whereValue );
        }
    }

    public void update()
    {
        gd = GameData.getInstance();

        ContentValues cv = new ContentValues();

        cv.put( GameSettingsTable.Cols.MONEY, gd.getMoney() );
        cv.put( GameSettingsTable.Cols.GAME_TIME, gd.getGameTime() );


        String[] whereValue = { String.valueOf( gd.getSettings().getMapWidth() ) };
        db.update( GameSettingsTable.NAME, cv, GameSettingsTable.Cols.MAP_WIDTH + " = ?", whereValue );

        MapElement[][] map = gd.getMapElements();
        addSettings( gd.getSettings() );

        for( int ii = 0; ii < map.length; ii++ )
        {
            for( int jj = 0; jj < map[ii].length; jj++ )
            {
                updateMapElement( map[ii][jj], ii, jj );
            }
        }
    }

    public void updateMapElement( MapElement element, int row, int col )
    {
        ContentValues cv = new ContentValues();
        cv.put( MapElementTable.Cols.COLUMN_INDEX, col );
        cv.put( MapElementTable.Cols.ROW_INDEX, row );
        cv.put( MapElementTable.Cols.OWNER, element.getOwnerName() );

        cv.put( MapElementTable.Cols.TYPE, element.getStructure().getType() );
        cv.put( MapElementTable.Cols.STRUCTURE_IMAGE, element.getStructure().getImageID() );

        db.update( MapElementTable.NAME, cv,MapElementTable.Cols.COLUMN_INDEX + " = " + String.valueOf( col ) + " AND " + MapElementTable.Cols.ROW_INDEX + " = " + String.valueOf( row ), null/*new String[]{ String.valueOf( col ), String.valueOf( row ) }*/ );
    }

    public void updateStatus()
    {
        addSettings( gd.getSettings() );
        ContentValues cv = new ContentValues();

        cv.put( GameSettingsTable.Cols.MONEY, gd.getMoney() );
        cv.put( GameSettingsTable.Cols.GAME_TIME, gd.getGameTime() );


        String[] whereValue = { String.valueOf( gd.getSettings().getMapWidth() ) };
        db.update( GameSettingsTable.NAME, cv, GameSettingsTable.Cols.MAP_WIDTH + " = ?", whereValue );
    }
}
