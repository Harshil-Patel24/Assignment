package curtin.edu.au.assignment.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import curtin.edu.au.assignment.MainActivity;
import curtin.edu.au.assignment.R;
import curtin.edu.au.assignment.model.GameData;

/**
 * This is activated when the player goes bankrupt
 */
public class GameOverActivity extends AppCompatActivity
{
    private Button menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.game_over_activity );

        GameData gd = GameData.getInstance();

        gd.getStore().clear();

        menu = ( Button )findViewById( R.id.menu );

        menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity( new Intent( GameOverActivity.this, MainActivity.class ));
            }
        });
    }
}
