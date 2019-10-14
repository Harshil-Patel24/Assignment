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
    private TextView title;
    private TextView author;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameData gd = GameData.getInstance();

        store = gd.getStore();

        newGame = ( Button )findViewById( R.id.newGameButton );
        loadGame = ( Button )findViewById( R.id.loadGameButton);
        title = ( TextView )findViewById( R.id.title );
        author = ( TextView )findViewById( R.id.author );
        error = ( TextView )findViewById( R.id.error );

        newGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                store.create( MainActivity.this );
                //A new game will open up the settings activity to allow user to change settings before starting
                startActivity( new Intent( MainActivity.this, SettingsActivity.class ));
            }
        });

        loadGame.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                store.load( MainActivity.this );
                //This is to test if the database works
                startActivity( new Intent( MainActivity.this, SettingsActivity.class ) );
                //Loading a game will check to see if there is a game to load first
                //Display message in "error" if there is one to show
                //startActivity( new Intent( MainActivity.this, SettingsActivity.class ));
            }
        });
    }
}
