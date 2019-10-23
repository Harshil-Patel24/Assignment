package curtin.edu.au.assignment.controller;

import curtin.edu.au.assignment.MainActivity;
import curtin.edu.au.assignment.R;
import  curtin.edu.au.assignment.model.*;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * This fragment will display the current statistics of the game
 */
public class StatusFragment extends Fragment
{
    private TextView time;
    private Button timeIncrement;
    private TextView population;
    private TextView money;
    private TextView employment;
    private Button menu;
    private TextView error;
    private TextView income;
    private GameData gd = GameData.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate( R.layout.fragment_status, ui, false );

        time = ( TextView )view.findViewById( R.id.timeVal );
        timeIncrement = ( Button )view.findViewById( R.id.timeIncrement );
        population = ( TextView )view.findViewById( R.id.populationVal );
        employment = ( TextView )view.findViewById( R.id.employmentVal );
        menu = ( Button )view.findViewById( R.id.mainMenu );
        error = ( TextView )view.findViewById( R.id.error );
        money = ( TextView )view.findViewById( R.id.moneyVal );
        income = ( TextView )view.findViewById( R.id.income );

        time.setText( String.valueOf( gd.getGameTime() ) );
        money.setText( String.valueOf( gd.getMoney() ) );
        population.setText( String.valueOf( gd.getPopulation() ) );
        setEmployment();

        /**
         * Incrementing time will update money and and other stats and also update the db
         */
        timeIncrement.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                double salary = gd.getSettings().getSalary();
                double tax = gd.getSettings().getTaxRate();
                double serviceCost = gd.getSettings().getServiceCost();
                //Calculates and sets the employment rate
                setEmployment();
                double employmentRate = Double.parseDouble( employment.getText().toString() );
                double populationNum = gd.getPopulation();
                //Calculates the income
                int incomeVal = ( int )( populationNum * ( employmentRate * salary * tax - serviceCost ) );
                //Increases the current game time
                gd.incTime();
                time.setText( String.valueOf( gd.getGameTime() ) );
                //Updates the money
                addMoney( incomeVal );
                //Update the database
                gd.getStore().addSettings( gd.getSettings() );
                gd.getStore().updateStatus();

                //If the money drops below 0 you lose!
                if( gd.getMoney() < 0 )
                {
                    setError( "You've gone bankrupt!" );
                    startActivity( new Intent( getActivity(), GameOverActivity.class ) );
                }
            }
        });

        /**
         * This will let you return to the main menu
         */
        menu.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                startActivity( new Intent( getActivity(), MainActivity.class ));
            }
        });

        return view;
    }

    /**
     * This will set the text of this view to the most recent income
     * Income will be green and loss of money will be red
     */
    public void addMoney( int inIncome )
    {
        if( inIncome > 0 )
        {
            income.setTextColor( Color.parseColor( "#008000" ) );
            income.setText( "+$" + inIncome );
        }
        else
        {
            income.setTextColor( Color.parseColor( "#FF0000" ) );
            income.setText( "-$" + String.valueOf( Math.abs( inIncome ) ) );
        }
        gd.incMoney( inIncome );
        money.setText( String.valueOf( gd.getMoney() ) );
    }

    /**
     * This is used to set error messages for the map activity
     */
    public void setError( String message )
    {
        if( error != null )
        {
            error.setText( message );
        }
    }

    /**
     * Calculates and sets the employment rate to be used in income calculations
     */
    public void setEmployment()
    {
        double employmentRate = 1;

        if( gd.getPopulation() > 0 )
        {
            employmentRate = ( double )( ( int )( Math.min( 1.0, ( double )gd.getNumCommercial() * ( double )gd.getSettings().getShopSize() / ( double )gd.getPopulation() ) * 100.0 ) ) / 100.0;
        }

        employment.setText( String.valueOf( employmentRate ) );
    }

    public void setPopulation()
    {
        population.setText( String.valueOf( gd.getPopulation() ) );
    }

    public void updateStatus()
    {
        setEmployment();
        setPopulation();
    }
}
