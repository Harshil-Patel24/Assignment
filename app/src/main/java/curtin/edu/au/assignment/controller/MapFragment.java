package curtin.edu.au.assignment.controller;

import curtin.edu.au.assignment.R;
import curtin.edu.au.assignment.model.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.app.NotificationCompatSideChannelService;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;


public class MapFragment extends Fragment
{
    private SelectorFragment selector;
    private StatusFragment status;
    private GameData gd = GameData.getInstance();
    private MapElement[][] data = gd.getMapElements();
    //private MapAdapter adapter;

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
            int row = index % gd.getMapHeight();
            int col = index / gd.getMapHeight();

            if( data[row][col].getImage() == null )
            {
                Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
                data[row][col].setImage( bm );
            }

            vh.bind( data[row][col] );
            gd.getStore().updateMapElement( data[row][col], row, col );
        }
    }

    private class MapDataViewHolder extends RecyclerView.ViewHolder
    {
        private GameData gd = GameData.getInstance();
        private ImageView image;
        private Structure struc;

        public MapDataViewHolder( LayoutInflater li, ViewGroup parent )
        {
            super( li.inflate( R.layout.grid_cell, parent, false ) );
            image = ( ImageView )itemView.findViewById( R.id.image );


            //Add stuff to ensure square images in here (from prac 3)
            int size = parent.getMeasuredHeight() / gd.getMapHeight() + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            image.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    int row = getAdapterPosition() % gd.getMapHeight();
                    int col = getAdapterPosition() / gd.getMapHeight();

                    struc = selector.getSelection();
                    //MapElement element = data[row][col];

                    //Null on land
                    if( struc == null && data[row][col].getStructure().getType().equals( "LAND" ) )
                    {
                        status.setError( "No structure/option selected!" );
                    }
                    //Null on building
                    else if( struc == null )
                    {
                        openDetails();
                    }
                    //Not null
                    else
                    {
                        //Attempt to delete a road
                        if( struc.getType().equals( "DESTROY" ) && data[row][col].getStructure().getType().equals( "ROAD" ) )
                        {
                            destroyRoad();
                        }
                        //Attempt to delete anything else
                        else if( struc.getType().equals( "DESTROY" ) )
                        {
                            //other than land coz why would you want to delete land?!?!
                            if( !data[row][col].getStructure().getType().equals( "LAND" )  )
                            {
                                data[row][col] = new MapElement();
                                Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
                                data[row][col].setImage( bm );

                                bind( data[row][col] );
                                gd.getStore().updateMapElement( data[row][col], row, col );
                            }
                            else
                            {
                                status.setError( "Cannot destroy land!" );
                            }
                        }
                        //Attempt to build
                        else
                        {
                            //Make sure that you're not building on another structure
                            if( data[row][col].getStructure().getType().equals( "LAND" ) )
                            {
                                //You have enough money
                                if( gd.getMoney() >= struc.getCost() )
                                {
                                    status.setError(null);
                                    data[row][col].setStructure(struc);

                                    status.addMoney( -struc.getCost() );

                                    Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
                                    data[row][col].setImage( bm );


                                    bind( data[row][col] );
                                    gd.getStore().updateMapElement( data[row][col], row, col );
                                }
                                //You don't have enough money
                                else
                                {
                                    status.setError( "Not enough money to build!" );
                                }
                            }
                            else
                            {
                                openDetails();
                            }
                        }
                    }
                }
            });
        }

        public void openDetails()
        {
            int row = getAdapterPosition() % gd.getMapHeight();
            int col = getAdapterPosition() / gd.getMapHeight();

            //Start details activity
            Intent intent = new Intent( getActivity(), DetailsActivity.class );
            intent.putExtra( "owner", data[row][col].getOwnerName() );
            intent.putExtra( "type", data[row][col].getStructure().toString() );
            intent.putExtra( "row", row );
            intent.putExtra( "col", col );

            //Make the getImage() actually return a bitmap of the image

            ByteArrayOutputStream str = new ByteArrayOutputStream();
            Bitmap bm = data[row][col].getImage();
            bm.compress( Bitmap.CompressFormat.PNG, 100, str );
            byte[] arr = str.toByteArray();

            intent.putExtra( "image", arr );

            startActivity( intent );
        }

        public void bind( MapElement mapElement )
        {
            image.setImageBitmap( mapElement.getImage() );
        }

        public void destroyRoad()
        {/*
            int row = getAdapterPosition() % gd.getMapHeight();
            int col = getAdapterPosition() / gd.getMapHeight();

            boolean destroyable = true;

            //This will be the road element chosen to delete
            MapElement road = data[row][col];

            data[row][col] = new MapElement();

            for( int ii = -1; ii < 2; ii++ )
            {
                for( int jj = -1; jj < 2; jj++ )
                {
                    if( ii != 0 && jj != 0 )
                    {
                        if( !data[row + ii][col + jj].getStructure().getType().equals( "LAND" ) )
                        {
                            if( !isBuildable( data[row + ii][col + jj].getStructure() ) )
                            {
                                destroyable = false;
                            }
                        }
                    }
                }
            }

            if( !destroyable )
            {
                data[row][col] = road;
            }
            else
            {
                status.setError( "Cannot destroy that road!" );
            }

            Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
            data[row][col].setImage( bm );
            bind( data[row][col] );
            gd.getStore().updateMapElement( data[row][col], row, col );

            /*
            MapElement element = data[row][col];

            if( element.getStructure().getType().equals( "ROAD" ) )
            {
                data[row][col] = new MapElement();
                for( int ii = -1; ii < 2; ii++ )
                {
                    for( int jj = -1; jj < 2; jj++ )
                    {

                        if( row + ii >= 0 && col + jj >= 0 && row + ii < data.length && col + jj < data[0].length )
                        {
                            if( !data[row + ii][col + jj].getStructure().getType().equals( "LAND" ) )
                            {
                                if( !isBuildable( data[row + ii][col + jj].getStructure() ) )
                                {
                                    destroyable = false;
                                }
                            }
                        }

                    }
                }

                if( !destroyable )
                {
                    data[row][col] = element;
                }
                else
                {
                    status.setError( "Cannot destroy that road!" );
                }

                Bitmap bm = getBitmapFromVectorDrawable( getContext(), data[row][col].getStructure().getImageID() );
                data[row][col].setImage( bm );
                bind( data[row][col] );
                gd.getStore().updateMapElement( data[row][col], row, col );
            }*/

        }

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


    //This method was taken from Stack overflow:
    //Original URL- https://stackoverflow.com/questions/33696488/getting-bitmap-from-vector-drawable
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
