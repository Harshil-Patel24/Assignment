package curtin.edu.au.assignment.database;

import curtin.edu.au.assignment.model.*;
import curtin.edu.au.assignment.database.GameSchema.*;
import android.database.Cursor;
import android.database.CursorWrapper;

public class GameCursor extends CursorWrapper
{
    public GameCursor( Cursor cursor )
    {
        super( cursor );
    }

    public Settings getSettings()
    {
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

        return settings;
    }
}
