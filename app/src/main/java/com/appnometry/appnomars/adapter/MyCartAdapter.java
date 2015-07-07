package com.appnometry.appnomars.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.fragments.MyCartFragment;
import com.appnometry.appnomars.holder.AllShoppingCartList;
import com.appnometry.appnomars.model.ShoppingCartListModel;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.JsonUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.simple.JSONObject;


public class MyCartAdapter extends ArrayAdapter<ShoppingCartListModel> {
    Context context;

    public String response;
    public boolean asyncCheck = false;
    public String ContentCode;
    public String mobileNo;
    private int loader;
    private ImageLoader imageLoader;
    DisplayImageOptions options;

    private JsonUtility jsonUtility = new JsonUtility();
    int itemPriceUnit=0;
    OnDataChangeListener mOnDataChangeListener;

    public MyCartAdapter(Activity context) {
        super(context, R.layout.mycart_row, AllShoppingCartList.getAllShoppingList());
        this.context = context;

    }

    static class ViewHolder {


        ImageView cart_leftarrow;
        ImageView cart_rightarrow;
        ImageView cart_delete;

        TextView cart_title;
        TextView cart_description;
        TextView cart_price;
        TextView cart_itemnumber;


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
            v = vi.inflate(R.layout.mycart_row, null);
            holder = new ViewHolder();
            holder.cart_leftarrow = (ImageView) v.findViewById(R.id.cart_leftarrow);
            holder.cart_rightarrow = (ImageView) v.findViewById(R.id.cart_rightarrow);
            holder.cart_delete = (ImageView) v.findViewById(R.id.cart_delete);
            holder.cart_title = (TextView) v.findViewById(R.id.cart_title);
            holder.cart_description = (TextView) v.findViewById(R.id.cart_description);
            holder.cart_price = (TextView) v.findViewById(R.id.cart_price);
            holder.cart_itemnumber = (TextView) v.findViewById(R.id.cart_itemnumber);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (position < AllShoppingCartList.getAllShoppingList().size()) {
            final ShoppingCartListModel query = AllShoppingCartList.getAllShoppingList().elementAt(
                    position);
            holder.cart_price.setText(query.getItemPrice());
            holder.cart_description.setText(query.getItemDetail());
            holder.cart_title.setText(query.getItemName());
            holder.cart_itemnumber.setText(query.getItemQuantity());

            itemPriceUnit=Integer.valueOf(query.getItemUnitPrice());

        }

        holder.cart_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                jsonUtility.removeAddtocartList(position);
                AllShoppingCartList.getAllShoppingList().remove(position);
                //notifyDataSetChanged();
                MyCartFragment.updateSum(AppConstant.SUBTOTAL-Integer.valueOf(holder.cart_price.getText().toString()));

            }
        });
        holder.cart_rightarrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemCount = Integer.valueOf(holder.cart_itemnumber.getText().toString());
                int itemPrice = Integer.valueOf(holder.cart_price.getText().toString());

                holder.cart_itemnumber.setText("" + (itemCount + 1));
                holder.cart_price.setText("" + (itemPrice + itemPriceUnit));
                AppConstant.SUBTOTAL = AppConstant.SUBTOTAL+itemPriceUnit;
                addItem(position, holder.cart_itemnumber.getText().toString(), holder.cart_price.getText().toString());

                jsonUtility.removeAddtocartList(position);
                AppConstant.elements.add(addItem(position, holder.cart_itemnumber.getText().toString(), holder.cart_price.getText().toString()));
                MyCartFragment.updateSum(AppConstant.SUBTOTAL);
            }
        });
        holder.cart_leftarrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int itemCount = Integer.valueOf(holder.cart_itemnumber.getText().toString());
                int itemPrice = Integer.valueOf(holder.cart_price.getText().toString());

                holder.cart_price.setText("" + (itemPrice - itemPriceUnit));
                if (itemCount < 0) {
                    AlertDialogHelper.showAlert(context, "Please Select least one");
                }else {
                    holder.cart_itemnumber.setText("" + (itemCount - 1));
                    addItem(position, holder.cart_itemnumber.getText().toString(), holder.cart_price.getText().toString());

                    jsonUtility.removeAddtocartList(position);
                    AppConstant.elements.add(addItem(position, holder.cart_itemnumber.getText().toString(), holder.cart_price.getText().toString()));
                    AppConstant.SUBTOTAL = AppConstant.SUBTOTAL-itemPriceUnit;
                }


            }
        });

        return v;
    }


    private String addItem(int position, String itemCountString,String itemPrice) {
        final ShoppingCartListModel query = AllShoppingCartList.getAllShoppingList().elementAt(
                position);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("ID", query.getItemID());
        jsonObj.put("TITLE", query.getItemName());
        jsonObj.put("PRICE", query.getItemPrice());
        jsonObj.put("QUANTITY", itemCountString);
        jsonObj.put("DETAIL", query.getItemDetail());
        jsonObj.put("UNIT_PRICE", query.getItemPrice());
        AppConstant.ADDTOCART = jsonObj;
        jsonUtility.UpdateJson("QUANTITY", itemCountString);
        jsonUtility.UpdateJson("PRICE", itemPrice);
        return jsonUtility.UpdateJson("QUANTITY", itemCountString);
    }
    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }
    public void doButtonOneClickActions(int rowNumber) {
        if(mOnDataChangeListener != null){
            mOnDataChangeListener.onDataChanged(AppConstant.SUBTOTAL);
        }
    }
}