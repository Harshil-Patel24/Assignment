package curtin.edu.au.assignment.controller;

import curtin.edu.au.assignment.R;
import  curtin.edu.au.assignment.model.*;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class StatusFragment extends Fragment
{
    private TextView time;
    private Button timeIncrement;
    private TextView population;
    private TextView money;
    private TextView employment;
    //private Button save;
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
        //save = ( Button )view.findViewById( R.id.save );
        error = ( TextView )view.findViewById( R.id.error );
        money = ( TextView )view.findViewById( R.id.moneyVal );
        income = ( TextView )view.findViewById( R.id.income );

        time.setText( String.valueOf( gd.getGameTime() ) );
        money.setText( String.valueOf( gd.getMoney() ) );
        population.setText( String.valueOf( gd.getPopulation() ) );
        setEmployment();

        timeIncrement.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                double salary = gd.getSettings().getSalary();
                double tax = gd.getSettings().getTaxRate();
                double serviceCost = gd.getSettings().getServiceCost();
                setEmployment();
                double employmentRate = Double.parseDouble( employment.getText().toString() );
                double populationNum = gd.getPopulation(); //Integer.parseInt( population.getText().toString() );
                int incomeVal = ( int )( populationNum * ( employmentRate * salary * tax - serviceCost ) );
                gd.incTime();
                time.setText( String.valueOf( gd.getGameTime() ) );
                addMoney( incomeVal );
                gd.getStore().addSettings( gd.getSettings() );
                gd.getStore().updateStatus();
                gd.getStore().update();
                //error.setText( "Population: " + String.valueOf( populationNum ) + "Employment: " + String.valueOf( employmentRate ) + " Income: " + String.valueOf( incomeVal ) + " Service: " + String.valueOf( serviceCost ) + " Salary: " + String.valueOf( salary ) );
            }
        });

        return view;
    }

    public void addMoney( int inIncome )
    {
        if( inIncome > 0 )
        {
            income.setTextColor( Color.parseColor( "#008000" ) );
            income.setText( "+" + inIncome );
        }
        else
        {
            income.setTextColor( Color.parseColor( "#FF0000" ) );
            income.setText( String.valueOf( inIncome ) );
        }
        gd.incMoney( inIncome );
        money.setText( String.valueOf( gd.getMoney() ) );
    }

    public void setError( String message )
    {
        if( error != null )
        {
            error.setText( message );
        }
    }

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
