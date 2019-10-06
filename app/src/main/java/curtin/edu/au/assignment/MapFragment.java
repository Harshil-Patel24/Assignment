package curtin.edu.au.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // Have your data ready
        List<MapElement> data; //Initialise this

        // Create your adapter (see next slides)
        //MapAdapter adapter = new MapAdapter(data);

        // Hook it up
        //rv.setAdapter(adapter);

        return view;
    }

    private class MapAdapter extends RecyclerView.Adapter<MapDataViewHolder>
    {
        private List<MapElement> data;

        @Override
        public int getItemCount()
        {
            return data.size();
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
            vh.bind( data.get( index ) );
        }
    }

    private class MapDataViewHolder extends RecyclerView.ViewHolder
    {
        public MapDataViewHolder( LayoutInflater li, ViewGroup parent )
        {
            super( li.inflate( R.id.gridCell, parent, false ) );
        }

        //This needs to change from Object
        public void bind( MapElement mapElement )
        {
            //Bind the stuff here I guess
        }
    }

}
