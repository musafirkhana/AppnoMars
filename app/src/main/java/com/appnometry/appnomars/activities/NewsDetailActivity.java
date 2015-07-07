package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.PlacesAdapter;
import com.appnometry.appnomars.holder.AllGlobalItemList;
import com.appnometry.appnomars.model.GlobalItemListModel;
import com.appnometry.appnomars.util.JsonUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class NewsDetailActivity extends Activity implements View.OnClickListener {
    private int position = 0;
    private Context context;
    private JsonUtility jsonUtility = new JsonUtility();
    private ImageLoader imageLoader;
    DisplayImageOptions options;
    /**
     * ***************************Declare View********************************
     */
    private ImageView nd_imageview;
    private ImageView nd_back;
    private TextView nd_title;
    private TextView nd_places;
    private TextView nd_detail;
    private TextView nd_categorytype;
    private TextView nd_valid_date;

    private LinearLayout nd_places_linear;
    private ListView nd_moreplace;
    String[] placesArray=null;
    String[] placesID=null;
    private PlacesAdapter placesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        context = this;
        initImageLoader();
        position = getIntent().getIntExtra("position", 0);
        initUI();
    }
    private void initImageLoader(){


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
    private void initUI() {
        nd_imageview = (ImageView) findViewById(R.id.nd_imageview);
        nd_back = (ImageView) findViewById(R.id.nd_back);
        nd_title = (TextView) findViewById(R.id.nd_title);
        nd_places = (TextView) findViewById(R.id.nd_places);
        nd_detail = (TextView) findViewById(R.id.nd_detail);
        nd_categorytype=(TextView)findViewById(R.id.nd_categorytype);
        nd_valid_date=(TextView)findViewById(R.id.nd_valid_date);
        nd_places_linear=(LinearLayout)findViewById(R.id.nd_places_linear);
        nd_moreplace=(ListView)findViewById(R.id.nd_moreplace);

        nd_back.setOnClickListener(this);
        setData();
    }

    private void setData() {
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        nd_detail.setText(query.getDescription());
       // nd_places.setText(query.getItem_venues());
        nd_title.setText(query.getTitle());

        placesArray = query.getVenue_name().split(",");
        placesID=query.getItem_venues().split(",");
        if(placesArray.length==1){
            nd_places.setText(query.getItem_venues());
        }else {
            nd_places.setText("Valid In " + placesArray.length+" Places");
        }
        nd_places_linear.setOnClickListener(this);
        loadImage(query.getImage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nd_back:
                finish();
                break;
            case R.id.nd_places_linear:
                nd_moreplace.setVisibility(View.VISIBLE);
                if(placesArray.length==1) {
                    nd_moreplace.setVisibility(View.GONE);
                   // dd_arrow_image.setVisibility(View.GONE);
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }else {
                    if(nd_moreplace.getVisibility()==View.VISIBLE){
                        nd_moreplace.setVisibility(View.GONE);
                    }else {
                        nd_moreplace.setVisibility(View.VISIBLE);
                        placesAdapter=new PlacesAdapter(context,placesArray,placesID);
                        nd_moreplace.setAdapter(placesAdapter);
                        setListView(nd_moreplace);
                        placesAdapter.notifyDataSetChanged();
                    }

                }
                break;

            }
        }


    /**
     * Populate Dynamic Listview
     *
     * @param listView
     */
    public void setListView(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
    private void loadImage(String imageURL){
        Log.i("Image Url ", imageURL);
        imageLoader.displayImage(imageURL, nd_imageview, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                // holder.progressBarCircular.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                //holder.progressBarCircular.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                //holder.progressBarCircular.setVisibility(View.GONE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                //holder.progressBarCircular.setVisibility(View.VISIBLE);
            }
        });
    }
}
