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
        mapHeight = 50;
        mapWidth = 10;
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
    }

    public void setMapWidth( int width )
    {
        if(  width > 0 )
        {
            mapHeight = width;
        }
    }

    public void setInitialMoney( int money )
    {
        if( money > 0 )
        {
            initialMoney = money;
        }
    }
}
