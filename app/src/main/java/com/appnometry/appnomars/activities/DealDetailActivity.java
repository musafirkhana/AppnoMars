package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.database.AppnomarsDBHandler;
import com.appnometry.appnomars.database.KudoroBDModel;
import com.appnometry.appnomars.holder.AllGlobalItemList;
import com.appnometry.appnomars.model.GlobalItemListModel;
import com.appnometry.appnomars.util.JsonUtility;

public class DealDetailActivity extends Activity implements View.OnClickListener {
    private int position = 0;
    private KudoroBDModel kudoroBDModel = new KudoroBDModel();
    AppnomarsDBHandler appnomarsDBHandler;
    private Context context;
    private JsonUtility jsonUtility = new JsonUtility();
    /**
     * ***************************Declare View********************************
     */
    private ImageView dd_back;
    private TextView dd_title;
    private TextView dd_places;
    private TextView dd_prices;
    private TextView dd_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);
        context = this;
        appnomarsDBHandler = new AppnomarsDBHandler(context);
        position = getIntent().getIntExtra("position", 0);
        initUI();
    }

    private void initUI() {
        dd_back = (ImageView) findViewById(R.id.dd_back);
        dd_title = (TextView) findViewById(R.id.dd_title);
        dd_places = (TextView) findViewById(R.id.dd_places);
        dd_prices = (TextView) findViewById(R.id.dd_prices);
        dd_detail = (TextView) findViewById(R.id.dd_detail);

        dd_back.setOnClickListener(this);
        setData();
    }

    private void setData() {
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        dd_detail.setText(query.getDescription());
        dd_prices.setText("$ " + query.getItem_price());
        dd_places.setText(query.getItem_venues());
        dd_title.setText(query.getTitle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dd_back:
                finish();
                break;

        }

    }

}
