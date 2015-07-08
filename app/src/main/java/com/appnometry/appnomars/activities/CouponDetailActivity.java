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

public class CouponDetailActivity extends Activity implements View.OnClickListener {
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
    private ImageView cd_back;
    private ImageView cd_imageview;
    private TextView cd_title;
    private TextView cd_places;
    private TextView cd_prices;
    private TextView cd_detail;
    private TextView cd_time;

    /*****************************Load More Places********************/
    private RelativeLayout cd_moreplace_relative;
    private ListView cd_moreplace;
    private ImageView cd_arrow_image;
    String[] placesArray=null;
    String[] placesID=null;
    private PlacesAdapter placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);
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
        cd_back = (ImageView) findViewById(R.id.cd_back);
        cd_title = (TextView) findViewById(R.id.cd_title);
        cd_places = (TextView) findViewById(R.id.cd_places);
        cd_prices = (TextView) findViewById(R.id.cd_prices);
        cd_detail = (TextView) findViewById(R.id.cd_detail);
        cd_imageview=(ImageView)findViewById(R.id.cd_imageview);
        cd_time=(TextView)findViewById(R.id.cd_time);
        cd_back.setOnClickListener(this);
        setData();
    }

    private void setData() {
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        cd_detail.setText(query.getDescription());
        cd_prices.setText("$ " + query.getItem_price());
        cd_title.setText(query.getTitle());
        cd_time.setText("Valid Till : " + Tools.DateFormator(query.getValid_to().toString(), "yyyy-MM-dd HH:mm:ss", "MMMM dd, yyyy"));
        loadImage(query.getImage());
        implementMorePlaces();
    }
    private void loadImage(String imageURL){
        Log.i("Image Url ", imageURL);
        imageLoader.displayImage(imageURL,cd_imageview, options, new SimpleImageLoadingListener() {
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
        cd_moreplace_relative=(RelativeLayout)findViewById(R.id.cd_moreplace_relative);
        cd_moreplace=(ListView)findViewById(R.id.cd_moreplace);
        cd_arrow_image=(ImageView)findViewById(R.id.cd_arrow_image);
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        placesArray = query.getVenue_name().split(",");
        placesID=query.getItem_venues().split(",");
        if(placesArray.length==1){
            cd_places.setText(query.getVenue_name());
        }else {
            cd_places.setText("Valid In " + placesArray.length+" Places");
        }
        cd_moreplace_relative.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cd_back:
                finish();
                break;
            case R.id.cd_moreplace_relative:
                if(placesArray.length==1) {
                    cd_moreplace.setVisibility(View.GONE);
                    cd_arrow_image.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }else {
                    if(cd_moreplace.getVisibility()==View.VISIBLE){
                        cd_moreplace.setVisibility(View.GONE);
                    }else {
                        cd_moreplace.setVisibility(View.VISIBLE);
                        placesAdapter=new PlacesAdapter(context,placesArray,placesID);
                        cd_moreplace.setAdapter(placesAdapter);
                        setListView(cd_moreplace);
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
