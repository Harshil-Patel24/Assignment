package curtin.edu.au.assignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class SettingsActivity extends AppCompatActivity
{
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );
        /*
        FragmentManager fm = getSupportFragmentManager();
        SettingsFragment frag = ( SettingsFragment )fm.findFragmentById( R.id.settings );

        if( frag == null )
        {
            frag = new SettingsFragment();
            fm.beginTransaction().add( R.id.settings, frag ).commit();
        }
        */
    }
}
