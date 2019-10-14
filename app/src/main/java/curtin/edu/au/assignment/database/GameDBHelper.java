package curtin.edu.au.assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import curtin.edu.au.assignment.database.GameSchema.*;

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
                GameSettingsTable.Cols.ROAD_BUILDING_COST + " INTEGER)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {
        //Do nothing here for the time being
    }
}
