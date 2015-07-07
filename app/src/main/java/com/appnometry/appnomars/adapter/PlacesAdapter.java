package com.appnometry.appnomars.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appnometry.appnomars.R;

/**
 * Created by Ali PC on 7/7/2015.
 */
public class PlacesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final String[] venueID;

    public PlacesAdapter(Context context, String[] values,String[] venueID) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.venueID = venueID;
    }

    static class ViewHolder {
        TextView places_txt;
        TextView places_id;
        LinearLayout places_mainlinear;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View v = convertView;

        if (v == null) {
            final LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_places, null);
            holder = new ViewHolder();
            holder.places_txt = (TextView) v.findViewById(R.id.places_txt);
            holder.places_id=(TextView)v.findViewById(R.id.places_id);
            holder.places_mainlinear=(LinearLayout)v.findViewById(R.id.places_mainlinear);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.places_txt.setText(values[position]);
        holder.places_id.setText(venueID[position]);
        holder.places_mainlinear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context,  holder.places_id.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }

}
