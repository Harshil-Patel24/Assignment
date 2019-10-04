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
            //Fill 'er up with basic map elements (land or smth idk)
        }
    }
}
