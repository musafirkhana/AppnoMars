package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.JsonUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.simple.JSONObject;

public class ShopDetailActivity extends Activity implements View.OnClickListener {
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
    private ImageView sd_imageview;
    private ImageView sd_back;
    private TextView cd_title;
    private TextView sd_places;
    private TextView sd_prices;
    private TextView sd_detail;
    private Button sd_addto_cart;
    private ListView sd_moreplace;
    private RelativeLayout placemore_relative;


    String[] placesArray=null;
    String[] placesID=null;
    private PlacesAdapter placesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        context = this;
        initImageLoader();
        appnomarsDBHandler = new AppnomarsDBHandler(context);
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
        sd_imageview = (ImageView) findViewById(R.id.sd_imageview);
        sd_back = (ImageView) findViewById(R.id.sd_back);
        cd_title = (TextView) findViewById(R.id.cd_title);
        sd_places = (TextView) findViewById(R.id.sd_places);
        sd_prices = (TextView) findViewById(R.id.sd_prices);
        sd_detail = (TextView) findViewById(R.id.sd_detail);
        sd_addto_cart = (Button) findViewById(R.id.sd_addto_cart);
        sd_moreplace=(ListView)findViewById(R.id.sd_moreplace);
        placemore_relative=(RelativeLayout)findViewById(R.id.placemore_relative);
        sd_back.setOnClickListener(this);
        sd_addto_cart.setOnClickListener(this);
        placemore_relative.setOnClickListener(this);
        setData();
    }

    private void setData() {
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        sd_detail.setText(query.getDescription());
        sd_prices.setText("$ " + query.getItem_price());
       // sd_places.setText(query.getItem_venues());
        cd_title.setText(query.getTitle());
        placesArray = query.getVenue_name().split(",");
        placesID=query.getItem_venues().split(",");
        loadImage(query.getImage());
        if(placesArray.length==1){
            sd_places.setText(query.getVenue_name());
        }else {
            sd_places.setText("Valid In " + placesArray.length+" Places");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sd_back:
                finish();
                break;
            case R.id.sd_addto_cart:
                final GlobalItemListModel queryGlobalItemListModel = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("ID", "" + queryGlobalItemListModel.getId());
                jsonObj.put("TITLE", queryGlobalItemListModel.getTitle());
                jsonObj.put("PRICE", queryGlobalItemListModel.getItem_price());
                jsonObj.put("QUANTITY", "1");
                jsonObj.put("DETAIL", queryGlobalItemListModel.getDescription());
                jsonObj.put("UNIT_PRICE", queryGlobalItemListModel.getItem_price());
                AppConstant.ADDTOCART = jsonObj;
                jsonUtility.createJsonFile(jsonObj, context);
                finish();
                break;
            case R.id.placemore_relative:
                if(placesArray.length==1) {
                    sd_moreplace.setVisibility(View.GONE);
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }else {
                    if(sd_moreplace.getVisibility()==View.VISIBLE){
                        sd_moreplace.setVisibility(View.GONE);
                    }else {
                        sd_moreplace.setVisibility(View.VISIBLE);
                        placesAdapter=new PlacesAdapter(context,placesArray,placesID);
                        sd_moreplace.setAdapter(placesAdapter);
                        setListView(sd_moreplace);
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
        imageLoader.displayImage(imageURL, sd_imageview, options, new SimpleImageLoadingListener() {
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
