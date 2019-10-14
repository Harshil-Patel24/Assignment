package curtin.edu.au.assignment.controller;

import curtin.edu.au.assignment.R;
import curtin.edu.au.assignment.model.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectorFragment extends Fragment
{
    //private StructureData sd = StructureData.getInstance();
    //private ArrayList<Structure> data = ( ArrayList<Structure> )sd.getStructures();
    private Structure selection;

    public void setSelection( Structure selected )
    {
        selection = selected;
    }

    public Structure getSelection()
    {
        return selection;
    }

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
        private StructureData sd = StructureData.getInstance();
        private ArrayList<Structure> data = ( ArrayList<Structure> )sd.getStructures();

        @Override
        public SelectorDataViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
        {
            LayoutInflater li = LayoutInflater.from( getActivity() );
            SelectorDataViewHolder vh = new SelectorDataViewHolder( li, parent );



            return vh;
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
        private Structure struc = null;
        private ImageView image;
        private TextView description;

        public SelectorDataViewHolder( LayoutInflater li, ViewGroup parent )
        {
            super( li.inflate( R.layout.list_selection, parent, false ) );

            image = itemView.findViewById( R.id.itemImage );
            description = itemView.findViewById( R.id.itemDesc );

            image.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    selection = struc;
                }
            });
        }

        public void bind( Structure structure )
        {
            struc = structure;
            image.setImageResource( structure.getImageID() );
            //Prolly have to FIX THIS UP
            description.setText( structure.toString() );
        }
    }
}
