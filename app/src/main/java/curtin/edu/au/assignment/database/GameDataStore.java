package curtin.edu.au.assignment.database;

import curtin.edu.au.assignment.model.*;
import curtin.edu.au.assignment.database.GameSchema.*;
import android.content.ContentValues;
import android.content.Context;
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
        //this.db = new GameDBHelper( context.getApplicationContext() ).getWritableDatabase();
        //Now read the data in
        GameCursor cursor = new GameCursor( db.query( GameSettingsTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null ) );
        try
        {
            cursor.moveToFirst();
            if( !cursor.isAfterLast() )
            {
                Settings settings = cursor.getSettings();
                gd.setSettings( settings );
            }
        }
        finally
        {
            cursor.close();
        }
    }

    public void create( Context context )
    {
        gd  = GameData.getInstance();
        //Load database
        this.db = new GameDBHelper( context.getApplicationContext() ).getWritableDatabase();
        /*
        GameCursor cursor = new GameCursor( db.query( GameSettingsTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null ) );
        try
        {
            cursor.moveToFirst();
            if( !cursor.isAfterLast() )
            {
                Settings settings = cursor.getSettings();
                gd.setSettings( settings );
            }
        }
        finally
        {
            cursor.close();
        }
        */
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
}
