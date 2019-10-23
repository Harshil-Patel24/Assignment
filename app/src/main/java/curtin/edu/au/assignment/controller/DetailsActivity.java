package curtin.edu.au.assignment.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import curtin.edu.au.assignment.R;
import curtin.edu.au.assignment.model.GameData;

/**
 * The details activity
 */
public class DetailsActivity extends AppCompatActivity
{
    private TextView ownerName;
    private Button back;
    private Button photo;
    private Button setOwner;
    private ImageView image;
    private TextView type;
    private EditText newOwner;
    private TextView error;
    private TextView coord;
    private int row;
    private int col;

    private static final int REQUEST_CODE_PLAY = 1;
    private Intent cameraIntent;

    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_details );

        //Initialise the views
        ownerName = ( TextView )findViewById( R.id.owner );
        back = ( Button )findViewById( R.id.back );
        photo = ( Button )findViewById( R.id.photo );
        setOwner = ( Button )findViewById( R.id.setOwner );
        image = ( ImageView )findViewById( R.id.image );
        type = ( TextView )findViewById( R.id.type );
        newOwner = ( EditText )findViewById( R.id.newOwner );
        error = ( TextView )findViewById( R.id.error );
        coord = ( TextView )findViewById( R.id.coordinates );

        //This is final so it can be accessed in the anonymous class
        final Intent intent = getIntent();

        //Convert a bitmap to a byte array so it can be passed back to the map activity
        byte[] ba = intent.getByteArrayExtra( "image" );
        Bitmap bm = BitmapFactory.decodeByteArray( ba, 0, ba.length );
        Bitmap scaled = Bitmap.createScaledBitmap( bm, 200, 200, true );
        image.setImageBitmap(scaled);

        ownerName.setText( intent.getStringExtra( "owner" ) );

        row = intent.getIntExtra( "row", 0 );
        col = intent.getIntExtra( "col", 0 );

        //Build the string for displaying the coordinates
        String coordStr = "(";
        coordStr += row;
        coordStr += ", ";
        coordStr += col;
        coordStr += ")";

        coord.setText( coordStr );

        //Type is the type of structure
        String typeStr = intent.getStringExtra( "type" );

        type.setText( typeStr );

        /**
         * This is how we go back to the map activity
         **/
        back.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                //Go back to the MapActivity
                startActivity( new Intent( DetailsActivity.this, MapActivity.class ) );
                ownerName.setText( intent.getStringExtra( "owner" ) );
            }
        });

        setOwner.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                String newName = newOwner.getText().toString();
                //Doesn't allow the name to be empty or null
                if( newName != null && !newName.equals( "" ) )
                {
                    error.setText( null );
                    GameData.getInstance().getMapElements()[row][col].setOwnerName( newName );
                    ownerName.setText( newName );
                }
                else
                {
                    error.setText( "Please enter a valid owner name" );
                }
            }
        });

        //This one takes us to the camera app to retrieve a photo
        photo.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( cameraIntent, REQUEST_CODE_PLAY );
            }
        });
    }

    /**
     * This came from the lecture slides (lecture 2 I think?)
     */
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent resultIntent )
    {
        super.onActivityResult( requestCode, resultCode, resultIntent );

        if( resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PLAY)
        {
            // The return intent from the camera contains the
            // small ("thumbnail") photo inside an extra called
            // "data".
            Bitmap img = (Bitmap) resultIntent.getExtras().get("data");

            GameData.getInstance().getMapElements()[row][col].setImage( img );

            image.setImageBitmap( img ); // Display photo
        }

    }
}
