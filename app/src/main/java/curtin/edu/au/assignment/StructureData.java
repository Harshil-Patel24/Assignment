package curtin.edu.au.assignment;

public class StructureData
{
    private static final StructureData ourInstance = new StructureData();
    private Residential[] residential;
    private Commercial[] commercial;
    private Road[] road;

    public static StructureData getInstance() {
        return ourInstance;
    }

    private StructureData()
    {
    }
}
