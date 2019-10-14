package curtin.edu.au.assignment.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import curtin.edu.au.assignment.R;

public class StructureData
{
    private static final StructureData ourInstance = new StructureData();
    //private Residential[] residential;
    //private Commercial[] commercial;
    //private Road[] road;

    public static StructureData getInstance(){ return ourInstance; }

    public static final int[] DRAWABLES = {
            0, // No structure
            /*1*/R.drawable.ic_building1, /*2*/R.drawable.ic_building2, /*3*/R.drawable.ic_building3,
            /*4*/R.drawable.ic_building4, /*5*/R.drawable.ic_building5, /*6*/R.drawable.ic_building6,
            /*7*/R.drawable.ic_building7, /*8*/R.drawable.ic_building8,
            /*9*/R.drawable.ic_road_ns, /*10*/R.drawable.ic_road_ew, /*11*/R.drawable.ic_road_nsew,
            /*12*/R.drawable.ic_road_ne, /*13*/R.drawable.ic_road_nw, /*14*/R.drawable.ic_road_se,
            /*15*/R.drawable.ic_road_sw, /*16*/R.drawable.ic_road_n, /*17*/R.drawable.ic_road_e,
            /*18*/R.drawable.ic_road_s, /*19*/R.drawable.ic_road_w, /*20*/R.drawable.ic_road_nse,
            /*21*/R.drawable.ic_road_nsw, /*22*/R.drawable.ic_road_new, /*23*/R.drawable.ic_road_sew,
            /*24*/R.drawable.ic_grass1, /*25*/R.drawable.ic_grass2, /*26*/R.drawable.ic_grass3,
            /*27*/R.drawable.ic_grass4, /*28*/R.drawable.ic_bomb };

    private List<Structure> structures = Arrays.asList(new Structure[] {
            new Destroy(),
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
            /*new Land(R.drawable.ic_grass1),
            new Land(R.drawable.ic_grass2),
            new Land(R.drawable.ic_grass3),
            new Land(R.drawable.ic_grass4),*/
    });

    public Structure get( int index ){ return structures.get( index ); }
    public int size(){ return structures.size(); }
    public void add( Structure struc ){ structures.add( 0, struc ); }
    public void remove( int index ){ structures.remove( index ); }
    public ArrayList<Structure> getStructures(){ return new ArrayList<Structure>(structures); }
}
