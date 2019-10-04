package curtin.edu.au.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SettingsFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate( R.layout.fragment_settings, ui, false );

        //Get the RecyclerView UI element
        RecyclerView rv = ( RecyclerView )view.findViewById( R.id.settingsRecyclerView );

        rv.setLayoutManager( new LinearLayoutManager( getActivity() ));

        return view;
    }

    private class SettingsViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemDesc;
        EditText itemVal;

        public SettingsViewHolder( LayoutInflater li, ViewGroup parent )
        {
            super( li.inflate( R.layout.setting_item, parent, false ) );

            itemDesc = ( TextView )itemView.findViewById( R.id.itemDesc );
            itemVal = ( EditText )itemView.findViewById( R.id.itemVal );
        }

        //Change string to your own data type
        public void bind( String data )
        {
            //set the stuff using the stuff
        }
    }

    private class SettingsAdapter extends RecyclerView.Adapter<SettingsViewHolder>
    {
        //Probably change from string to your data type

        private List<String> data;

        public SettingsAdapter( List<String> inData )
        {
            data = inData;
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        @Override
        public SettingsViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
        {
            LayoutInflater li = LayoutInflater.from( getActivity() );
            return new SettingsViewHolder( li, parent );
        }
        @Override
        public void onBindViewHolder( SettingsViewHolder vh, int index )
        {
            vh.bind( data.get( index ) );
        }

    }
}
