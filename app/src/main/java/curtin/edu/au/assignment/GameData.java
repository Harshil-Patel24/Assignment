package curtin.edu.au.assignment;

public class GameData
{
    private static final GameData ourInstance = new GameData();

    private Settings settings;
    private GameDataStore store;
    private MapElement[][] map;
    private int money;
    private int gameTime;

    public static GameData getInstance() { return ourInstance; }

    /*
    * Initialise the gamedata to be all default settings
    */
    private GameData()
    {
        settings = new Settings();
        store = new GameDataStore();
        map = null;
        money = 0;
        gameTime = 0;
    }

    public Settings getSettings(){ return settings; }
    public GameDataStore getStore(){ return store; }

    public void setSettings( Settings inSettings )
    {
        if( settings != null )
        {
            settings = inSettings;
        }
    }

    //Creates the map array
    public void setMap( int width, int height )
    {
        if( width > 0 && height > 0 )
        {
            map = new MapElement[width][height];

            //Might not need to do this idk
            for( int ii = 0; ii < map.length; ii++ )
            {
                for( int jj = 0; jj < map[ii].length; jj++ )
                {
                    map[ii][jj] = new MapElement();
                }
            }
        }
    }

    public int getMapWidth()
    {
        if( map != null )
        {
            return map.length;
        }
        else
        {
            return 0;
        }
    }

    public int getMapHeight()
    {
        if( map[0] != null )
        {
            return map[0].length;
        }
        else
        {
            return 0;
        }
    }

    public MapElement[][] getMapElements()
    {
        return map;
    }

    public int getCount()
    {
        int count = 0;
        if( map != null )
        {
            count = map.length * map[0].length;
        }
        return count;
    }
}
