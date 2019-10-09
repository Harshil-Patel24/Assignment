package curtin.edu.au.assignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MapActivity extends AppCompatActivity
{
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_map );

        GameData gd = GameData.getInstance();
        gd.setMap();

        FragmentManager fm = getSupportFragmentManager();
        MapFragment fragMap = ( MapFragment )fm.findFragmentById( R.id.map );
        if( fragMap == null )
        {
            fragMap = new MapFragment();
            fm.beginTransaction().add( R.id.map, fragMap ).commit();
        }

        SelectorFragment fragSelector = ( SelectorFragment )fm.findFragmentById( R.id.selector );
        if( fragSelector == null )
        {
            fragSelector = new SelectorFragment();
            fm.beginTransaction().add( R.id.selector, fragSelector ).commit();
        }

    }
}
