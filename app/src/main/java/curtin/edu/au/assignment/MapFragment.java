package curtin.edu.au.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        GameData gd = GameData.getInstance();

        View view = inflater.inflate( R.layout.fragment_map, ui, false );

        // Obtain the RecyclerView UI element
        RecyclerView rv = ( RecyclerView )view.findViewById( R.id.mapRecyclerView );

        // Specify how it should be laid out
        rv.setLayoutManager( new GridLayoutManager( getActivity(), gd.getMapHeight(), GridLayoutManager.HORIZONTAL, false ) );

        //ArrayList<MapElement> data = gd.getMapList();
        //MapElement[][] data = gd.getMapElements();

        // Create your adapter
        MapAdapter adapter = new MapAdapter();

        // Hook it up
        rv.setAdapter(adapter);

        return view;
    }

    private class MapAdapter extends RecyclerView.Adapter<MapDataViewHolder>
    {
        //This might need to be a List
        //private MapElement[][] data;
        private GameData gd = GameData.getInstance();
        //private ArrayList<MapElement> data = gd.getMapList();
        private MapElement[][] data = gd.getMapElements();

        @Override
        public int getItemCount()
        {
            int count = 0;

            /*
            if( data != null )
            {
                count = data.size();
            } */

            count = gd.getCount();

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

            vh.bind( data[row][col] );
            //vh.bind( data.get( index ) );
        }
    }

    private class MapDataViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private GameData gd = GameData.getInstance();

        public MapDataViewHolder( LayoutInflater li, ViewGroup parent )
        {
            super( li.inflate( R.layout.grid_cell, parent, false ) );
            image = ( ImageView )itemView.findViewById( R.id.image );

            //Add stuff to ensure square images in here (from prac 3)
            int size = parent.getMeasuredHeight() / gd.getMapHeight() + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;
        }

        //This needs to change from Object
        public void bind( MapElement mapElement )
        {
            image.setImageResource( mapElement.getStructure().getImageID() );
        }
    }
}
