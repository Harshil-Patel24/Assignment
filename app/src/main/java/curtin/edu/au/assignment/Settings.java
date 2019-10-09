package curtin.edu.au.assignment;

public class Settings
{
    /*
    //For use in the ArrayList of data
    public static final int MAP_HEIGHT = 0;
    public static final int MAP_WIDTH = 1;
    public static final int INITIAL_MONEY = 2;
    public static final int FAMILY_SIZE = 3;
    public static final int SHOP_SIZE = 4;
    public static final int SALARY = 5;
    public static final int TAX_RATE = 6;
    public static final int SERVICE_COST = 7;
    public static final int HOUSE_BUILDING_COST = 8;
    public static final int COMM_BUILDING_COST = 9;
    public static final int ROAD_BUILDING_COST = 10;
*/
    private int mapHeight;
    private int mapWidth;
    private int initialMoney;
    private int familySize;
    private int shopSize;
    private int salary;
    private double taxRate;
    private int serviceCost;
    private int houseBuildingCost;
    private int commBuildingCost;
    private int roadBuildingCost;

    //Initially settings
    public Settings()
    {
        //Maybe set these to be as values in edit text in activity_settings
        //These are the default values
        mapHeight = 10;
        mapWidth = 50;
        initialMoney = 1000;
        familySize = 4;
        shopSize = 6;
        salary = 10;
        taxRate = 0.3;
        serviceCost = 2;
        houseBuildingCost = 100;
        commBuildingCost = 500;
        roadBuildingCost = 20;
    }

    public int getMapHeight(){ return mapHeight; }
    public int getMapWidth(){ return mapWidth; }
    public int getInitialMoney(){ return initialMoney; }
    public int getFamilySize(){ return familySize; }
    public int getShopSize(){ return shopSize; }
    public int getSalary(){ return salary; }
    public double getTaxRate(){ return taxRate; }
    public int getServiceCost(){ return serviceCost; }
    public int getHouseBuildingCost(){ return houseBuildingCost; }
    public int getCommBuildingCost(){ return commBuildingCost; }
    public int getRoadBuildingCost(){ return roadBuildingCost; }

    public void setMapHeight( int height )
    {
        if( height > 0 )
        {
            mapHeight = height;
        }
        else
        {
            throw new IllegalArgumentException( "Map height is not valid" );
        }
    }

    public void setMapWidth( int width )
    {
        if(  width > 0 )
        {
            mapWidth = width;
        }
        else
        {
            throw new IllegalArgumentException( "Map width is not valid" );
        }
    }

    public void setInitialMoney( int money )
    {
        if( money > 0 )
        {
            initialMoney = money;
        }
        else
        {
            throw new IllegalArgumentException( "Initial money is not valid" );
        }
    }

    public void setFamilySize( int family )
    {
        if( family > 0 )
        {
            familySize = family;
        }
        else
        {
            throw new IllegalArgumentException( "Family size is not valid" );
        }
    }

    public void setShopSize( int shop )
    {
        if( shop > 0 )
        {
            shopSize = shop;
        }
        else
        {
            throw new IllegalArgumentException( "Shop size is not valid" );
        }
    }

    public void setSalary( int sal )
    {
        if( sal > 0 )
        {
            salary = sal;
        }
        else
        {
            throw new IllegalArgumentException( "Salary is not valid" );
        }
    }

    public void setTaxRate( double tax )
    {
        if( tax > 0.0 )
        {
            taxRate = tax;
        }
        else
        {
            throw new IllegalArgumentException( "Tax rate is not valid" );
        }
    }

    public void setServiceCost( int cost )
    {
        if( cost > 0 )
        {
            serviceCost = cost;
        }
        else
        {
            throw new IllegalArgumentException( "Service cost is not valid" );
        }
    }

    public void setHouseBuildingCost( int cost )
    {
        if( cost > 0 )
        {
            houseBuildingCost = cost;
        }
        else
        {
            throw new IllegalArgumentException( "House building cost is not valid" );
        }
    }

    public void setCommBuildingCost( int cost )
    {
        if( cost > 0 )
        {
            commBuildingCost = cost;
        }
        else
        {
            throw new IllegalArgumentException( "Commercial building cost is not valid" );
        }
    }

    public void setRoadBuildingCost( int cost )
    {
        if( cost > 0 )
        {
            roadBuildingCost = cost;
        }
        else
        {
            throw new IllegalArgumentException( "Road building cost is not valid" );
        }
    }
}
