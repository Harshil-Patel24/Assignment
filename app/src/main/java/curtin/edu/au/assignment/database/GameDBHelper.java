package curtin.edu.au.assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import curtin.edu.au.assignment.database.GameSchema.*;

/**
 * The database helper for this game
 */
public class GameDBHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "game.db";

    public GameDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        //This creates a table foe the game settings and stats
        db.execSQL( "CREATE TABLE " + GameSettingsTable.NAME + "(" +
                GameSettingsTable.Cols.MAP_WIDTH + " INTEGER, " +
                GameSettingsTable.Cols.MAP_HEIGHT + " INTEGER, " +
                GameSettingsTable.Cols.INITIAL_MONEY + " INTEGER, " +
                GameSettingsTable.Cols.FAMILY_SIZE + " INTEGER, " +
                GameSettingsTable.Cols.SHOP_SIZE + " INTEGER, " +
                GameSettingsTable.Cols.SALARY + " INTEGER, " +
                GameSettingsTable.Cols.TAX_RATE + " REAL, " +
                GameSettingsTable.Cols.SERVICE_COST + " INTEGER, " +
                GameSettingsTable.Cols.HOUSE_BUILDING_COST + " INTEGER, " +
                GameSettingsTable.Cols.COMMERCIAL_BUILDING_COST + " INTEGER, " +
                GameSettingsTable.Cols.ROAD_BUILDING_COST + " INTEGER, " +
                GameSettingsTable.Cols.GAME_TIME + " INTEGER, " +
                GameSettingsTable.Cols.MONEY + " INTEGER)" );

        //This creates a table for the map elements, ie. this will represent the map
        db.execSQL( "CREATE TABLE " + MapElementTable.NAME + "(" +
                MapElementTable.Cols.ROW_INDEX + " INTEGER, " +
                MapElementTable.Cols.COLUMN_INDEX + " INTEGER, " +
                MapElementTable.Cols.STRUCTURE_IMAGE + " INTEGER, " +
                MapElementTable.Cols.TYPE + " TEXT, " +
                MapElementTable.Cols.OWNER + " TEXT, " +
                MapElementTable.Cols.IMAGE + " BLOB)" );
    }

    /**
     * We do not need an onUpgrade for the scope of this assignment as we will just stick to
     * our SQLite database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {
        //Do nothing here for the time being
    }
}
