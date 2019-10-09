package curtin.edu.au.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectorFragment extends Fragment
{
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup ui, Bundle bundle )
    {
        View view = inflater.inflate( R.layout.fragment_map, ui, false );

        // Obtain the RecyclerView UI element
        RecyclerView rv = ( RecyclerView )view.findViewById( R.id.mapRecyclerView );

        // Specify how it should be laid out
        rv.setLayoutManager( new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false ) );

        // Create your adapter
        SelectorAdapter adapter = new SelectorAdapter();

        // Hook it up
        rv.setAdapter(adapter);

        return view;
    }

    public class SelectorAdapter extends RecyclerView.Adapter<SelectorDataViewHolder>
    {
        StructureData sd = StructureData.getInstance();
        private ArrayList<Structure> data = ( ArrayList<Structure> )sd.getStructures();

        @Override
        public SelectorDataViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
        {
            LayoutInflater li = LayoutInflater.from( getActivity() );
            return new SelectorDataViewHolder( li, parent );
        }

        @Override
        public void onBindViewHolder( SelectorDataViewHolder vh, int index )
        {
            vh.bind( data.get( index ) );
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }
    }

    public class SelectorDataViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private TextView description;

        public SelectorDataViewHolder( LayoutInflater li, ViewGroup parent )
        {
            super( li.inflate( R.layout.list_selection, parent, false ) );

            image = itemView.findViewById( R.id.itemImage );
            description = itemView.findViewById( R.id.itemDesc );
        }

        public void bind( Structure structure )
        {
            image.setImageResource( structure.getImageID() );
            //Prolly have to FIX THIS UP
            description.setText( structure.toString() );
        }
    }
}
