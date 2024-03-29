package curtin.edu.au.assignment.model;

import java.util.Random;
import java.util.*;
import curtin.edu.au.assignment.controller.SelectorFragment;
import curtin.edu.au.assignment.controller.StatusFragment;
import curtin.edu.au.assignment.database.*;
//import java.util.ArrayList;

public class GameData
{
    private static final GameData ourInstance = new GameData();

    private Settings settings;
    private GameDataStore store;
    private MapElement[][] map;
    private int money;
    private int gameTime;
    private SelectorFragment selector;
    private StatusFragment status;
    private Random rand;
    //private int population;


    //private long debugInt;
    //public long getDebugInt(){ return debugInt; };
    //public void setDebugInt( long in ){ debugInt = in; }


    public static GameData getInstance() { return ourInstance; }

    /**
    * Initialise the gamedata to be all default settings
    */
    private GameData()
    {
        settings = new Settings();
        store = new GameDataStore();
        rand = new Random();
        rand.setSeed( System.currentTimeMillis() );
        map = null;
        money = 0;
        gameTime = 0;
    }

    /**
     * Accessors
     */
    public Settings getSettings(){ return settings; }
    public GameDataStore getStore(){ return store; }
    public int getGameTime(){ return gameTime; }
    public int getMoney(){ return money; }
    public int getPopulation(){ return getNumResidential() * settings.getFamilySize(); }
    public SelectorFragment getSelector(){ return selector; }
    public StatusFragment getStatus(){ return status; }
    public int nextRand(){ return rand.nextInt(); }

    public int getNumResidential()
    {
        int num = 0;
        for( int ii = 0; ii < map.length; ii++ )
        {
            for( int jj = 0; jj < map[ii].length; jj++ )
            {
                if( map[ii][jj].getStructure().getType().equals( "RESIDENTIAL" ) )
                {
                    num++;
                }
            }
        }
        return num;
    }

    public int getNumCommercial()
    {
        int num = 0;
        for( int ii = 0; ii < map.length; ii++ )
        {
            for( int jj = 0; jj < map[ii].length; jj++ )
            {
                if( map[ii][jj].getStructure().getType().equals( "COMMERCIAL" ) )
                {
                    num++;
                }
            }
        }
        return num;
    }

    public int getMapHeight()
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

    /**
     * Mutators
     */
    public void setSettings( Settings inSettings )
    {
        if( settings != null )
        {
            settings = inSettings;
        }
    }
    public void incTime(){ gameTime++; }
    public void setGameTime( int time ){ gameTime = time; }
    public void setMoney( int inMoney ){ money = inMoney; }
    public void incMoney( int income ){ money += income; }

    public void setSelector( SelectorFragment selectorFragment ){ selector = selectorFragment; }

    public void setStatus( StatusFragment statusFragment ){ status = statusFragment; }

    /*
    public void incPopulation( int increase ){ population += increase; }
    */

    public void setMap()
    {
        int width = settings.getMapWidth();
        int height = settings.getMapHeight();
        if( width > 0 && height > 0 )
        {
            map = new MapElement[height][width];

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

    public void setMap( MapElement[][] inMap )
    {
        map = inMap;
    }
    /*
    public int getMapWidth()
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
    */

    /*
    public ArrayList<MapElement> getMapList()
    {
        ArrayList<MapElement> list = new ArrayList<>();
        for( int ii = 0; ii < map.length; ii++ )
        {
            for( int jj = 0; jj < map[ii].length; jj++ )
            {
                list.add( map[ii][jj] );
            }
        }

        return list;
    }
    */
}
