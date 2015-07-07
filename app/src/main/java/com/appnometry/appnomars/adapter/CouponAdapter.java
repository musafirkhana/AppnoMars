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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.holder.AllGlobalItemList;
import com.appnometry.appnomars.model.GlobalItemListModel;
import com.appnometry.appnomars.util.CommonUtils;
import com.appnometry.appnomars.util.JsonUtility;
import com.appnometry.appnomars.util.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class CouponAdapter extends ArrayAdapter<GlobalItemListModel> {
    Context context;

    private ImageLoader imageLoader;
    DisplayImageOptions options;

    private JsonUtility jsonUtility = new JsonUtility();
    public static int counter = 0;

    public CouponAdapter(Context context) {
        super(context, R.layout.list_row, AllGlobalItemList.getAllNewsFeedList());
        this.context = context;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.logo)
                .showImageForEmptyUri(R.drawable.logo)
                .showImageOnFail(R.drawable.logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }

    static class ViewHolder {

        ImageView img_mainimage;
        ImageView title_imageview;
        ProgressBar progressBarCircular;
        TextView nf_coupontitle;
        TextView nf_placensme;
        TextView nf_date;
        TextView nf_price;
        TextView short_desc;
        View top_separator;

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
            v = vi.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.img_mainimage = (ImageView) v.findViewById(R.id.img_mainimage);
            holder.title_imageview=(ImageView)v.findViewById(R.id.title_imageview);
            holder.progressBarCircular = (ProgressBar) v.findViewById(R.id.progress);
            holder.short_desc = (TextView) v.findViewById(R.id.short_desc);
            holder.nf_coupontitle = (TextView) v.findViewById(R.id.nf_coupontitle);
            holder.nf_placensme = (TextView) v.findViewById(R.id.nf_placensme);
            holder.nf_date = (TextView) v.findViewById(R.id.nf_date);
            holder.nf_price = (TextView) v.findViewById(R.id.nf_price);
            holder.top_separator = (View) v.findViewById(R.id.top_separator);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (position < AllGlobalItemList.getAllNewsFeedList().size()) {
            final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);

            holder.title_imageview.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.nf_coupontitle.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            holder.short_desc.setText(query.getDescription());
            holder.nf_date.setText(Tools.DateFormator(query.getValid_to().toString(), "yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy"));
            holder.nf_price.setText("$ "+query.getItem_price());
            holder.nf_coupontitle.setText(query.getTitle());
            int numberOfPlaces = CommonUtils.getNumberOfCoupon(query.getVenue_name());
            if (numberOfPlaces == 1) {
                holder.nf_placensme.setText(query.getVenue_name());
            } else {
                holder.nf_placensme.setText("Valid In " + numberOfPlaces + " Places");
            }




          imageLoader.displayImage(query.getImage(), holder.img_mainimage, options, new SimpleImageLoadingListener() {
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
            });


        }


        return v;
    }

}