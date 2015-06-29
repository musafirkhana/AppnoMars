package com.appnometry.appnomars.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.holder.AllGlobalItemList;
import com.appnometry.appnomars.model.GlobalItemListModel;
import com.appnometry.appnomars.util.JsonUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class ShopListAdapter extends ArrayAdapter<GlobalItemListModel> {
    Context context;

    private ImageLoader imageLoader;
    DisplayImageOptions options;

    private JsonUtility jsonUtility = new JsonUtility();
    public static int counter = 0;

    public ShopListAdapter(Context context) {
        super(context, R.layout.list_row_shop, AllGlobalItemList.getAllNewsFeedList());
        this.context = context;
        /*imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.logo)
                .showImageForEmptyUri(R.drawable.logo)
                .showImageOnFail(R.drawable.logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();*/

    }

    static class ViewHolder {

        ImageView shop_mainimage;
        ProgressBar progressBarCircular;
        TextView shop_coupontitle;
        TextView shop_price;
        TextView shop_type;
        TextView shop_addtocart;


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
            v = vi.inflate(R.layout.list_row_shop, null);
            holder = new ViewHolder();
            holder.shop_mainimage = (ImageView) v.findViewById(R.id.shop_mainimage);
            holder.progressBarCircular = (ProgressBar) v.findViewById(R.id.progress);
            holder.shop_coupontitle = (TextView) v.findViewById(R.id.shop_coupontitle);
            holder.shop_type = (TextView) v.findViewById(R.id.shop_type);
            holder.shop_addtocart = (TextView) v.findViewById(R.id.shop_addtocart);
            holder.shop_price = (TextView) v.findViewById(R.id.shop_price);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (position < AllGlobalItemList.getAllNewsFeedList().size()) {
            final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);



            holder.shop_coupontitle.setText(query.getTitle());
            holder.shop_type.setText(query.getItem_type());
            holder.shop_price.setText("$ "+query.getItem_price());





          /* imageLoader.displayImage(query.getImage_url().replaceAll("http://localhost","http://192.168.1.7/"), holder.img_mainimage, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    holder.progressBarCircular.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    holder.progressBarCircular.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.progressBarCircular.setVisibility(View.GONE);
                }
            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    holder.progressBarCircular.setVisibility(View.VISIBLE);
                }
            });*/


        }


        return v;
    }

}