package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.PlacesAdapter;
import com.appnometry.appnomars.database.AppnomarsDBHandler;
import com.appnometry.appnomars.database.KudoroBDModel;
import com.appnometry.appnomars.holder.AllGlobalItemList;
import com.appnometry.appnomars.model.GlobalItemListModel;
import com.appnometry.appnomars.util.JsonUtility;
import com.appnometry.appnomars.util.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class EventDetailActivity extends Activity implements View.OnClickListener {
    private int position = 0;
    private KudoroBDModel kudoroBDModel = new KudoroBDModel();
    AppnomarsDBHandler appnomarsDBHandler;
    private Context context;
    private JsonUtility jsonUtility = new JsonUtility();
    private ImageLoader imageLoader;
    DisplayImageOptions options;
    /**
     * ***************************Declare View********************************
     */
    private ImageView ed_back;
    private ImageView ed_imageview;
    private TextView ed_title;
    private TextView ed_places;
    private TextView ed_prices;
    private TextView ed_detail;
    private TextView ed_time;

    /*****************************Load More Places********************/
    private RelativeLayout ed_moreplace_relative;
    private ListView ed_moreplace;
    private ImageView ed_arrow_image;
    String[] placesArray=null;
    String[] placesID=null;
    private PlacesAdapter placesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        context = this;


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


        appnomarsDBHandler = new AppnomarsDBHandler(context);
        position = getIntent().getIntExtra("position", 0);
        initUI();

    }


    private void initUI() {
        ed_back = (ImageView) findViewById(R.id.ed_back);
        ed_title = (TextView) findViewById(R.id.ed_title);
        ed_places = (TextView) findViewById(R.id.ed_places);
        ed_prices = (TextView) findViewById(R.id.ed_prices);
        ed_detail = (TextView) findViewById(R.id.ed_detail);
        ed_imageview=(ImageView)findViewById(R.id.ed_imageview);
        ed_time=(TextView)findViewById(R.id.ed_time);
        ed_back.setOnClickListener(this);
        setData();
    }

    private void setData() {
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        ed_detail.setText(query.getDescription());
        ed_prices.setText("$ " + query.getItem_price());
        ed_title.setText(query.getTitle());
        ed_time.setText("Valid Till : " + Tools.DateFormator(query.getValid_to().toString(), "yyyy-MM-dd HH:mm:ss", "MMMM dd, yyyy"));
        loadImage(query.getImage());
        implementMorePlaces();
    }
    private void loadImage(String imageURL){
        Log.i("Image Url ", imageURL);
        imageLoader.displayImage(imageURL, ed_imageview, options, new SimpleImageLoadingListener() {
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
    private void implementMorePlaces(){
        ed_moreplace_relative=(RelativeLayout)findViewById(R.id.ed_moreplace_relative);
        ed_moreplace=(ListView)findViewById(R.id.ed_moreplace);
        ed_arrow_image=(ImageView)findViewById(R.id.ed_arrow_image);
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        placesArray = query.getVenue_name().split(",");
        placesID=query.getItem_venues().split(",");
        if(placesArray.length==1){
            ed_places.setText(query.getItem_venues());
        }else {
            ed_places.setText("Valid In " + placesArray.length+" Places");
        }
        ed_moreplace_relative.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_back:
                finish();
                break;
            case R.id.ed_moreplace_relative:
                if(placesArray.length==1) {
                    ed_moreplace.setVisibility(View.GONE);
                    ed_arrow_image.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }else {
                    if(ed_moreplace.getVisibility()==View.VISIBLE){
                        ed_moreplace.setVisibility(View.GONE);
                    }else {
                        ed_moreplace.setVisibility(View.VISIBLE);
                        placesAdapter=new PlacesAdapter(context,placesArray,placesID);
                        ed_moreplace.setAdapter(placesAdapter);
                        setListView(ed_moreplace);
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

}
