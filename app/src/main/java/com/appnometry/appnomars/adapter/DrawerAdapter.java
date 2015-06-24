package com.appnometry.appnomars.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appnometry.appnomars.R;


/**
 * Created by d4ddy-lild4rk on 11/8/14.
 */
public class DrawerAdapter extends ArrayAdapter<String> {

    private Context context;
    private final String[] values;

    public DrawerAdapter(Context context, String[] values) {
        super(context, R.layout.row_adapter, values);
        this.context = context;
        this.values = values;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        View row = convertView;
        LayoutInflater inflater = null;

        if (row == null) {

            holder = new ViewHolder();
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_adapter, null);
            holder.item_name = (TextView) row.findViewById(R.id.item_name);

            row.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }
        holder.item_name.setText(values[position]);
        return convertView;
    }
    static class ViewHolder {

        //public ProgressBar progressBar;
        public TextView item_name;


    }
}
