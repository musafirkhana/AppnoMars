package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.database.AppnomarsDBHandler;
import com.appnometry.appnomars.database.KudoroBDModel;
import com.appnometry.appnomars.holder.AllGlobalItemList;
import com.appnometry.appnomars.model.GlobalItemListModel;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.JsonUtility;

import org.json.simple.JSONObject;

public class ShopDetailActivity extends Activity implements View.OnClickListener {
    private int position = 0;
    private KudoroBDModel kudoroBDModel = new KudoroBDModel();
    AppnomarsDBHandler appnomarsDBHandler;
    private Context context;
    private JsonUtility jsonUtility = new JsonUtility();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        context = this;
        appnomarsDBHandler = new AppnomarsDBHandler(context);
        position = getIntent().getIntExtra("position", 0);
        initUI();
    }

    private void initUI() {
        sd_imageview = (ImageView) findViewById(R.id.sd_imageview);
        sd_back = (ImageView) findViewById(R.id.sd_back);
        cd_title = (TextView) findViewById(R.id.cd_title);
        sd_places = (TextView) findViewById(R.id.sd_places);
        sd_prices = (TextView) findViewById(R.id.sd_prices);
        sd_detail = (TextView) findViewById(R.id.sd_detail);
        sd_addto_cart = (Button) findViewById(R.id.sd_addto_cart);

        sd_back.setOnClickListener(this);
        sd_addto_cart.setOnClickListener(this);
        setData();
    }

    private void setData() {
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        sd_detail.setText(query.getDescription());
        sd_prices.setText("$ " + query.getItem_price());
        sd_places.setText(query.getItem_venues());
        cd_title.setText(query.getTitle());
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
        }

    }

}
