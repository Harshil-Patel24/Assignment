package curtin.edu.au.assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StructureData
{
    private static final StructureData ourInstance = new StructureData();
    //private Residential[] residential;
    //private Commercial[] commercial;
    //private Road[] road;

    public static StructureData getInstance(){ return ourInstance; }

    public static final int[] DRAWABLES = {
            0, // No structure
            R.drawable.ic_building1, R.drawable.ic_building2, R.drawable.ic_building3,
            R.drawable.ic_building4, R.drawable.ic_building5, R.drawable.ic_building6,
            R.drawable.ic_building7, R.drawable.ic_building8,
            R.drawable.ic_road_ns, R.drawable.ic_road_ew, R.drawable.ic_road_nsew,
            R.drawable.ic_road_ne, R.drawable.ic_road_nw, R.drawable.ic_road_se, R.drawable.ic_road_sw,
            R.drawable.ic_road_n, R.drawable.ic_road_e, R.drawable.ic_road_s, R.drawable.ic_road_w,
            R.drawable.ic_road_nse, R.drawable.ic_road_nsw, R.drawable.ic_road_new, R.drawable.ic_road_sew};

    private List<Structure> structures = Arrays.asList(new Structure[] {
            new Residential(R.drawable.ic_building1),
            new Residential(R.drawable.ic_building2),
            new Residential(R.drawable.ic_building3),
            new Residential(R.drawable.ic_building4),
            new Commercial(R.drawable.ic_building5),
            new Commercial(R.drawable.ic_building6),
            new Commercial(R.drawable.ic_building7),
            new Commercial(R.drawable.ic_building8),
            new Road(R.drawable.ic_road_ns),
            new Road(R.drawable.ic_road_ew),
            new Road(R.drawable.ic_road_nsew),
            new Road(R.drawable.ic_road_ne),
            new Road(R.drawable.ic_road_nw),
            new Road(R.drawable.ic_road_se),
            new Road(R.drawable.ic_road_sw),
            new Road(R.drawable.ic_road_n),
            new Road(R.drawable.ic_road_e),
            new Road(R.drawable.ic_road_s),
            new Road(R.drawable.ic_road_w),
            new Road(R.drawable.ic_road_nse),
            new Road(R.drawable.ic_road_nsw),
            new Road(R.drawable.ic_road_new),
            new Road(R.drawable.ic_road_sew),
            new Land(R.drawable.ic_grass1),
            new Land(R.drawable.ic_grass2),
            new Land(R.drawable.ic_grass3),
            new Land(R.drawable.ic_grass4),
    });

    public Structure get( int index ){ return structures.get( index ); }
    public int size(){ return structures.size(); }
    public void add( Structure struc ){ structures.add( 0, struc ); }
    public void remove( int index ){ structures.remove( index ); }
}
