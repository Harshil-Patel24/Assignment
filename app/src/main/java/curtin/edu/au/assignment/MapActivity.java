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

        FragmentManager fm = getSupportFragmentManager();
        MapFragment frag = ( MapFragment )fm.findFragmentById( R.id.map );
        if( frag == null )
        {
            frag = new MapFragment();
            fm.beginTransaction().add( R.id.map, frag ).commit();
        }

    }
}
