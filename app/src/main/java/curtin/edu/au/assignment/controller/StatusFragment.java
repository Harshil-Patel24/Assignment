package curtin.edu.au.assignment.controller;

import curtin.edu.au.assignment.R;
import  curtin.edu.au.assignment.model.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class StatusFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate( R.layout.fragment_status, ui, false );

        final GameData gd = GameData.getInstance();

        final TextView time = ( TextView )view.findViewById( R.id.timeVal );
        final Button timeIncrement = ( Button )view.findViewById( R.id.timeIncrement );
        final TextView population = ( TextView )view.findViewById( R.id.populationVal );
        final TextView money = ( TextView )view.findViewById( R.id.moneyVal );
        final TextView employment = ( TextView )view.findViewById( R.id.employmentVal );
        final Button settings = ( Button )view.findViewById( R.id.settings );

        time.setText( String.valueOf( gd.getGameTime() ) );
        money.setText( String.valueOf( gd.getMoney() ) );

        timeIncrement.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                gd.incTime();
                time.setText( String.valueOf( gd.getGameTime() ) );
            }
        });

        return view;
    }
}
