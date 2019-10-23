package curtin.edu.au.assignment.controller;

import curtin.edu.au.assignment.R;
import curtin.edu.au.assignment.model.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;

/**
 * The map fragment class
 * */
public class MapFragment extends Fragment
{
    //These are here so the fragments can communicate with each other
    private SelectorFragment selector;
    private StatusFragment status;

    private GameData gd = GameData.getInstance();
    private MapElement[][] data = gd.getMapElements();

    //Takes the SelectorFragment and StatusFragment from GameData that we initialised in the SettingsActivity
    public MapFragment()
    {
        selector = gd.getSelector();
        status = gd.getStatus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate( R.layout.fragment_map, ui, false );

        // Obtain the RecyclerView UI element
        RecyclerView rv = ( RecyclerView )view.findViewById( R.id.mapRecyclerView );

        // Specify how it should be laid out
        rv.setLayoutManager( new GridLayoutManager( getActivity(), gd.getMapHeight(), GridLayoutManager.HORIZONTAL, false ) );

        // Create your adapter
        MapAdapter adapter = new MapAdapter();

        // Hook it up
        rv.setAdapter(adapter);

        return view;
    }

    /**
     * My own Adapter class for the map
     */
    private class MapAdapter extends RecyclerView.Adapter<MapDataViewHolder>
    {
        @Override
        public int getItemCount()
        {
            int count = gd.getCount();
            return count;
        }

        @Override
        public MapDataViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
        {
            LayoutInflater li = LayoutInflater.from( getActivity() );
            return new MapDataViewHolder( li, parent );
        }

        @Override
        public void onBindViewHolder( MapDataViewHolder vh, int index )
        {
            //This is how we fins the row and column that we are in
            int row = index % gd.getMapHeight();
            int col = index / gd.getMapHeight();

            //Only set the bitmap if there isn't already an image in place
            //This lets us keep camera taken images rather than just setting it to the drawable
            if( data[row][col].getImage() == null )
            {
                Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
                data[row][col].setImage( bm );
            }

            //Bind everything and save it to the database
            vh.bind( data[row][col] );
            //gd.getStore().updateMapElement( data[row][col], row, col );
        }
    }

    /**
     * My own ViewHolder class for the map
     */
    private class MapDataViewHolder extends RecyclerView.ViewHolder
    {
        private GameData gd = GameData.getInstance();
        private ImageView image;
        //This is the currently selected structure from the structure fragment
        private Structure struc;

        /**
         * The ViewHolder constructor
         * This one is a little long as this contains the logic for when a grid cell image is clicked
         */
        public MapDataViewHolder( LayoutInflater li, ViewGroup parent )
        {
            super( li.inflate( R.layout.grid_cell, parent, false ) );
            image = ( ImageView )itemView.findViewById( R.id.image );

            //This ensures that the grid cells are actually squared (same solution as worksheet 3)
            int size = parent.getMeasuredHeight() / gd.getMapHeight() + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            /**
             * Clicking the image can do several things depending on what you have selected
            */
            image.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Clears the error message on each click (if an error does occur, it will be re-presented
                    status.setError( null );

                    //Again finding the row and col positons
                    int row = getAdapterPosition() % gd.getMapHeight();
                    int col = getAdapterPosition() / gd.getMapHeight();

                    //Get the currently selected structure
                    struc = selector.getSelection();

                    /**
                    * The following block of IF statements represent each individual senario or possibility
                    * for the different things that could happen when a map element's image is clicked-
                    *
                    * One of three actions can occur-
                    *
                    * 1. Building- (If land is selected (ie. no structure in that cell), you have enough money and you have selected a structure from the selection fragment
                    * 2. Destruction- (If destroy option is selected from the selection fragment)
                    * 3. Open the details page of the given structure- (If you click an already built structure and you have not selected "Destruction" option
                    */

                    //Null on land (no structure is selected)
                    if( struc == null && data[row][col].getStructure().getType().equals( "LAND" ) )
                    {
                        status.setError( "No structure/option selected!" );
                    }
                    //Null on building (no structure is selected, but you click a non-land cell (this means there is a structure there)
                    //So this will open the details activity
                    else if( struc == null )
                    {
                        openDetails();
                    }
                    //Not null- Now we move onto all possibilities of options where an option has been selected
                    //Everything is inside this "else" so I don't get any NullPointerExceptions or anything like it anywhere
                    else
                    {
                        //Attempt to delete a road
                        if( struc.getType().equals( "DESTROY" ) && data[row][col].getStructure().getType().equals( "ROAD" ) )
                        {
                            //Check if that road is okay to delete
                            destroyRoad();
                        }
                        //Attempt to delete anything else
                        else if( struc.getType().equals( "DESTROY" ) )
                        {
                            //Other than land coz why would you want to delete land?!?!
                            if( !data[row][col].getStructure().getType().equals( "LAND" )  )
                            {
                                //Just create a new map element and replace the item at the selected location
                                data[row][col] = new MapElement();

                                //Create the bitmap image and set it in the map element
                                Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
                                data[row][col].setImage( bm );

                                //Bind it and save it to the database!
                                bind( data[row][col] );
                                gd.getStore().updateMapElement( data[row][col], row, col );
                            }
                            //This is what happens when you try to destroy land
                            else
                            {
                                status.setError( "Cannot destroy land!" );
                            }
                        }
                        //Attempt to build
                        else
                        {
                            //Make sure that you're not building on another structure (no structure means the structure is LAND)
                            if( data[row][col].getStructure().getType().equals( "LAND" ) )
                            {
                                //Tryna build a road
                                if( struc.getType().equals( "ROAD" ) )
                                {
                                    //Only let it happen if there's enough money
                                    if( gd.getMoney() >= struc.getCost() )
                                    {
                                        data[row][col].setStructure(struc);

                                        //Negative adding is subtracting! :)
                                        status.addMoney( -struc.getCost() );

                                        //Create bitmap images and set them
                                        Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
                                        data[row][col].setImage( bm );

                                        //Bind and save
                                        bind( data[row][col] );
                                        gd.getStore().updateMapElement( data[row][col], row, col );
                                    }
                                    //Not enough money
                                    else
                                    {
                                        status.setError( "Not enough money to build!" );
                                        startActivity( new Intent( getActivity(), GameOverActivity.class ) );
                                    }
                                }
                                //Not trying to build a road
                                else
                                {
                                    //You have enough money
                                    if( gd.getMoney() >= struc.getCost() && isBuildable( data[row][col].getStructure() ) )
                                    {
                                        data[row][col].setStructure(struc);

                                        //Take the money away from current money
                                        status.addMoney( -struc.getCost() );

                                        //Set the bitmap image
                                        Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
                                        data[row][col].setImage( bm );

                                        //Bind and save
                                        bind( data[row][col] );
                                        gd.getStore().updateMapElement( data[row][col], row, col );
                                    }
                                    //You don't have enough money or it isn't buildable upon
                                    else
                                    {
                                        //You don't have enough money so now ur bankrupt and game is over
                                        if( !( gd.getMoney() >= struc.getCost() ) )
                                        {
                                            status.setError( "Not enough money to build!" );
                                            startActivity( new Intent( getActivity(), GameOverActivity.class ) );
                                        }
                                        //That location isn't buildable
                                        else
                                        {
                                            status.setError( "Cannot build there" );
                                        }
                                    }
                                }
                            }
                            //This means you have selected something other than land
                            //And considering you haven't selected the Destruction option you will open the details activity
                            else
                            {
                                openDetails();
                            }
                        }
                    }
                }
            });
        }

        /**
        * This will get the intents ready with all required data and then start the DetailsActivity
        */
        public void openDetails()
        {
            //Get the row and column numbers
            int row = getAdapterPosition() % gd.getMapHeight();
            int col = getAdapterPosition() / gd.getMapHeight();

            //Create the intent and put all data required by the details activity inside
            Intent intent = new Intent( getActivity(), DetailsActivity.class );
            intent.putExtra( "owner", data[row][col].getOwnerName() );
            intent.putExtra( "type", data[row][col].getStructure().toString() );
            intent.putExtra( "row", row );
            intent.putExtra( "col", col );
            intent.putExtra( "type", data[row][col].getStructure().toString() );

            //Get the bitmap ready to display at the details activity
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            Bitmap bm = data[row][col].getImage();
            bm.compress( Bitmap.CompressFormat.PNG, 100, str );
            byte[] arr = str.toByteArray();

            intent.putExtra( "image", arr );

            //Start the details activity
            startActivity( intent );
        }

        /**
         * All bind will do is set the image to the "bitmap" inside the corresponding map element
         */
        public void bind( MapElement mapElement )
        {
            image.setImageBitmap( mapElement.getImage() );
        }

        /**
         * This is a method to safely destroy roads without isolating any buildings
         */
        public void destroyRoad()
        {
            int row = getAdapterPosition() % gd.getMapHeight();
            int col = getAdapterPosition() / gd.getMapHeight();

            //This will determine if the road can safely be destroyed
            boolean destroyable = true;

            //Destroy the road and see if its okay
            MapElement element = data[row][col];
            data[row][col] = new MapElement();

            for( int ii = -1; ii < 2; ii++ )
            {
                for( int jj = -1; jj < 2; jj++ )
                {
                    if( !data[row + ii][col + jj].getStructure().getType().equals( "LAND" ) )
                    {
                        if( !isBuildable( row + ii, col + jj ) )
                        {
                            destroyable = false;
                        }
                    }
                }
            }

            if( destroyable == false )
            {
                data[row][col] = element;
                status.setError( "Cannot destroy that road, there are buildings relying on that road!" );
            }

            Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
            data[row][col].setImage( bm );

            bind( data[row][col] );
            gd.getStore().updateMapElement( data[row][col], row, col );
        }

        /**
         * Checks if the selected cell is okay to build a Residential or Commercial building
         * ie. makes sure there is a road adjacent to it
         * My application allows a building to be diagonally adjacent to roads as well,
         * ie. not necessarily just above/below/left/right
         *
         * This one is used by the destroyRoad() method to ensure that all remaining buildings are still on
         * buildable blocks, this means I need to be able to check buildability for cells that are not the
         * current adapter position, but can use the existing structure at the cells
         */
        public boolean isBuildable( int row, int col )
        {
            boolean buildable = false;

            //Loops through each element adjacent to the selected cell
            //Including diagonally
            for( int ii = -1; ii < 2; ii++ )
            {
                for( int jj = -1; jj < 2; jj++ )
                {
                    //Make sure it doesn't try to go out of bounds
                    if( row + ii >= 0 && col + jj >= 0 && row + ii < data.length && col + jj < data[0].length )
                    {
                        //If there is a road near it, then you can build!
                        if( data[row + ii][col + jj].getStructure().getType().equals( "ROAD" ) )
                        {
                            buildable = true;
                        }
                    }
                }
            }
            return buildable;
        }

        /**
         * Checks if the selected cell is okay to build a Residential or Commercial building
         * ie. makes sure there is a road adjacent to it
         * My application allows a building to be diagonally adjacent to roads as well,
         * ie. not necessarily just above/below/left/right
         *
         * This one is used when building and uses the current adapter position and the seleceted structure
         */
        public boolean isBuildable( Structure structure )
        {
            boolean buildable = false;
            int row = getAdapterPosition() % gd.getMapHeight();
            int col = getAdapterPosition() / gd.getMapHeight();

            if( structure.getType().equals( "ROAD" ) )
            {
                buildable = true;
            }
            else
            {
                for( int ii = -1; ii < 2; ii++ )
                {
                    for( int jj = -1; jj < 2; jj++ )
                    {
                        if( row + ii >= 0 && col + jj >= 0 && row + ii < data.length && col + jj < data[0].length )
                        {
                            if( data[row + ii][col + jj].getStructure().getType().equals( "ROAD" ) )
                            {
                                buildable = true;
                            }
                        }
                    }
                }
            }

            if( !data[row][col].getStructure().getType().equals( "LAND" ) )
            {
                buildable = false;
            }

            if( structure.getType().equals( "DESTROY" ) )
            {
                buildable = true;
            }

            return buildable;
        }

    }


    /**
     * This method was taken from Stack overflow:
     * Original URL- https://stackoverflow.com/questions/33696488/getting-bitmap-from-vector-drawable
     *
     * This just turns a vector drawable into a bitmap, this was needed so I can store the bitmap in the MapElement object
     */
    public Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
