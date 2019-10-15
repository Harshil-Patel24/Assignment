package curtin.edu.au.assignment.database;

public class GameSchema
{
    public static class GameSettingsTable
    {
        public static final String NAME = "settings";

        public static class Cols
        {
            public static final String MAP_WIDTH = "map_width";
            public static final String MAP_HEIGHT = "map_height";
            public static final String INITIAL_MONEY = "initial_money";
            public static final String FAMILY_SIZE = "family_size";
            public static final String SHOP_SIZE = "shop_size";
            public static final String SALARY = "salary";
            public static final String TAX_RATE = "tax_rate";
            public static final String SERVICE_COST = "service_cost";
            public static final String HOUSE_BUILDING_COST = "house_building_cost";
            public static final String COMMERCIAL_BUILDING_COST = "commercial_building_cost";
            public static final String ROAD_BUILDING_COST = "road_building_cost";
            public static final String MONEY = "money";
            public static final String GAME_TIME = "game_time";

        }
    }

    public static class MapElementTable
    {
        public static final String NAME = "data";

        public static class Cols
        {
            public static final String ROW_INDEX = "row_index";
            public static final String COLUMN_INDEX = "column_index";
            public static final String STRUCTURE_IMAGE = "structure_image";
            public static final String TYPE = "type";
            public static final String OWNER = "owner";
        }
    }
}
