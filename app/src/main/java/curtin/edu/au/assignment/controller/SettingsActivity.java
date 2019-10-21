package curtin.edu.au.assignment.controller;

import curtin.edu.au.assignment.R;
import curtin.edu.au.assignment.model.*;
import curtin.edu.au.assignment.database.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
{
    private GameData gd = GameData.getInstance();

    private Button start;
    private TextView error;

    private EditText mapHeight;
    private EditText mapWidth;
    private EditText initialMoney;
    private EditText familySize;
    private EditText shopSize;
    private EditText salary;
    private EditText taxRate;
    private EditText serviceCost;
    private EditText houseBuildingCost;
    private EditText commBuildingCost;
    private EditText roadBuildingCost;

    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        final Settings settings = gd.getSettings();
        final GameDataStore store = gd.getStore();

        start = ( Button )findViewById( R.id.start );
        error = ( TextView )findViewById( R.id.error );

        mapHeight = ( EditText )findViewById( R.id.mapHeight );
        mapWidth = ( EditText )findViewById( R.id.mapWidth );
        initialMoney = ( EditText )findViewById( R.id.initialMoney );
        familySize = ( EditText )findViewById( R.id.familySize );
        shopSize = ( EditText )findViewById( R.id.shopSize );
        salary = ( EditText )findViewById( R.id.salary );
        taxRate = ( EditText )findViewById( R.id.taxRate );
        serviceCost = ( EditText )findViewById( R.id.serviceCost );
        houseBuildingCost = ( EditText )findViewById( R.id.houseBuildingCost );
        commBuildingCost = ( EditText )findViewById( R.id.commBuildingCost );
        roadBuildingCost = ( EditText )findViewById( R.id.roadBuildingCost );

        //Display the current defaults to the player
        mapHeight.setText( String.valueOf( settings.getMapHeight() ) );
        mapWidth.setText( String.valueOf( settings.getMapWidth() ) );
        initialMoney.setText( String.valueOf( settings.getInitialMoney() ) );
        familySize.setText( String.valueOf( settings.getFamilySize() ) );
        shopSize.setText( String.valueOf( settings.getShopSize() ) );
        salary.setText( String.valueOf( settings.getSalary() ) );
        taxRate.setText( String.valueOf( settings.getTaxRate() ) );
        serviceCost.setText( String.valueOf( settings.getServiceCost() ) );
        houseBuildingCost.setText( String.valueOf( settings.getHouseBuildingCost() ) );
        commBuildingCost.setText( String.valueOf( settings.getCommBuildingCost() ) );
        roadBuildingCost.setText( String.valueOf( settings.getRoadBuildingCost() ) );

        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String errorMessage = null;
                try
                {
                    settings.setMapWidth( ( Integer.parseInt( mapWidth.getText().toString() ) ) );
                    settings.setMapHeight( Integer.parseInt( mapHeight.getText().toString() ) );
                    settings.setInitialMoney( Integer.parseInt( initialMoney.getText().toString() ) );
                    settings.setFamilySize( Integer.parseInt( familySize.getText().toString() ) );
                    settings.setShopSize( Integer.parseInt( shopSize.getText().toString() ) );
                    settings.setSalary( Integer.parseInt( salary.getText().toString() ) );
                    settings.setTaxRate( Double.parseDouble( taxRate.getText().toString() ) );
                    settings.setServiceCost( Integer.parseInt( serviceCost.getText().toString() ) );
                    settings.setHouseBuildingCost( Integer.parseInt( houseBuildingCost.getText().toString() ) );
                    settings.setCommBuildingCost( Integer.parseInt( commBuildingCost.getText().toString() ) );
                    settings.setRoadBuildingCost( Integer.parseInt( roadBuildingCost.getText().toString() ) );

                    gd.setMap();
                    gd.setMoney( gd.getSettings().getInitialMoney() );
                    //gd.setMoney( Integer.parseInt( initialMoney.getText().toString() ) );
                    store.addSettings( settings );

                    //Start the map activity after settings are decided
                    startActivity( new Intent( SettingsActivity.this, MapActivity.class ) );
                }
                catch( IllegalArgumentException e )
                {
                    errorMessage = e.getMessage();
                    error.setText( errorMessage );
                }
            }
        });
    }
}
