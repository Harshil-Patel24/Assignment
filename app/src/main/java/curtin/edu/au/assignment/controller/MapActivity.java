package curtin.edu.au.assignment.controller;

import android.os.Bundle;

import curtin.edu.au.assignment.R;
import curtin.edu.au.assignment.model.GameData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MapActivity extends AppCompatActivity
{
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_map );

        GameData gd = GameData.getInstance();
        //gd.setMap();
        //gd.setMoney( gd.getSettings().getInitialMoney() );
        //gd.setMapContext( MapActivity.this );

        FragmentManager fm = getSupportFragmentManager();

        SelectorFragment fragSelector = ( SelectorFragment )fm.findFragmentById( R.id.selector );
        if( fragSelector == null )
        {
            fragSelector = new SelectorFragment();
            fm.beginTransaction().add( R.id.selector, fragSelector ).commit();
        }


        StatusFragment fragStatus = ( StatusFragment )fm.findFragmentById( R.id.status );
        if( fragStatus == null )
        {
            fragStatus = new StatusFragment();
            fm.beginTransaction().add( R.id.status, fragStatus ).commit();
        }

        gd.setSelector( fragSelector );
        gd.setStatus( fragStatus );

        MapFragment fragMap = ( MapFragment )fm.findFragmentById( R.id.map );
        if( fragMap == null )
        {
            fragMap = new MapFragment();
            fm.beginTransaction().add( R.id.map, fragMap ).commit();
        }
    }
}
