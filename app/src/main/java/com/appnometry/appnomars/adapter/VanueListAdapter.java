package com.appnometry.appnomars.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.holder.AllVanueList;
import com.appnometry.appnomars.model.VanueListModel;
import com.appnometry.appnomars.util.AppConstant;

import java.util.List;


public class VanueListAdapter extends ArrayAdapter<VanueListModel> {
    Context context;


    public static int counter = 0;
    private final List<VanueListModel> list;

    public VanueListAdapter(Context context) {
        super(context, R.layout.vanuelist_row, AllVanueList.getAllVanueList());
        this.context = context;
        this.list = AllVanueList.getAllVanueList();
    }

    static class ViewHolder {

        protected TextView text;
        protected CheckBox checkbox;

    }

    Bitmap Shrinkmethod(String file, int width, int height) {
        BitmapFactory.Options bitopt = new BitmapFactory.Options();
        bitopt.inJustDecodeBounds = true;
        Bitmap bit = BitmapFactory.decodeFile(file, bitopt);

        int h = (int) Math.ceil(bitopt.outHeight / (float) height);
        int w = (int) Math.ceil(bitopt.outWidth / (float) width);

        if (h > 1 || w > 1) {
            if (h > w) {
                bitopt.inSampleSize = h;

            } else {
                bitopt.inSampleSize = w;
            }
        }
        bitopt.inJustDecodeBounds = false;
        bit = BitmapFactory.decodeFile(file, bitopt);

        return bit;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View v = convertView;

        if (v == null) {
            final LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.vanuelist_row, null);
            holder = new ViewHolder();
            holder.text = (TextView) v.findViewById(R.id.label);
            holder.checkbox = (CheckBox) v.findViewById(R.id.check);
            holder.checkbox .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    VanueListModel element = (VanueListModel) holder.checkbox.getTag();
                    element.setSelected(buttonView.isChecked());
                    AppConstant.vanueName =AppConstant.vanueName +","+element.getVenue_name();
                    AppConstant.vanueID=AppConstant.vanueID+","+element.getId();
                    System.out.println("Checked : " + element.getVenue_name());
                }
            });


            v.setTag(holder);
            holder.checkbox.setTag(list.get(position));
        } else {
            holder = (ViewHolder) v.getTag();
            ((ViewHolder) v.getTag()).checkbox.setTag(list.get(position));
        }

        ViewHolder viewHolder = (ViewHolder) v.getTag();
        viewHolder.text.setText(list.get(position).getVenue_name());
        viewHolder.checkbox.setChecked(list.get(position).isSelected());




        return v;
    }


}