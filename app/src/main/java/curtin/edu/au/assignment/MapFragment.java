package curtin.edu.au.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MapFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        GameData data = GameData.getInstance();

        View view = inflater.inflate( R.layout.fragment_map, ui, false );

        // Obtain the RecyclerView UI element
        RecyclerView rv = ( RecyclerView )view.findViewById( R.id.mapRecyclerView );

        // Specify how it should be laid out
        rv.setLayoutManager( new GridLayoutManager( getActivity(), data.getMapHeight(), GridLayoutManager.HORIZONTAL, false ) );

        // Have your data ready
        //Probably should make this a List somehow
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

        private GameData data = GameData.getInstance();

        @Override
        public int getItemCount()
        {
            int count = 0;

            if( data != null )
            {
                count = data.getCount();
            }

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
            //vh.bind( data.get( index ) );
        }
    }

    private class MapDataViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;

        public MapDataViewHolder( LayoutInflater li, ViewGroup parent )
        {
            super( li.inflate( R.layout.grid_cell, parent, false ) );
            image = ( ImageView )itemView.findViewById( R.id.image );
        }

        //This needs to change from Object
        public void bind( MapElement mapElement )
        {
            //Bind the stuff here I guess
        }
    }

}
