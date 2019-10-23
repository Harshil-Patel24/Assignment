package curtin.edu.au.assignment;

import curtin.edu.au.assignment.database.*;
import curtin.edu.au.assignment.model.*;
import curtin.edu.au.assignment.controller.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private GameDataStore store;

    private Button newGame;
    private Button loadGame;
    //A TextView is how I will display error throughout the program
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GameData gd = GameData.getInstance();

        store = gd.getStore();

        newGame = ( Button )findViewById( R.id.newGameButton );
        loadGame = ( Button )findViewById( R.id.loadGameButton);
        error = ( TextView )findViewById( R.id.error );

        /**
        * Upon clicking this button the player will be taken to the settings menu to select their settings
        */
        newGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                //Actually create the database
                store.create( MainActivity.this );
                //Clear the contents of it so it doesn't conflict with the new game
                store.clear();
                //Start the settings activity
                startActivity( new Intent( MainActivity.this, SettingsActivity.class ));
            }
        });

        /**
        * Load game will retrieve the state the game was at last time it was played and let the player
        * continue playing
        */
        loadGame.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Retrieve the data from the database
                store.load( MainActivity.this );

                GameDataStore store = gd.getStore();

                //If there are no map elements in the database and no settings object then there is no game to load
                if( !( store.getMapElementCount() == 0 && store.getSettingsElementCount() == 0 ) )
                {
                    //Start the map activity straight away (no need to go to the settings activity)
                    startActivity( new Intent( MainActivity.this, MapActivity.class ) );
                }
                //If there's no database loaded then display message
                else
                {
                    error.setText( "There is no game to load! Please start a new game!" );
                }
            }
        });
    }
}
