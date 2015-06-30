package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.holder.AllGlobalItemList;
import com.appnometry.appnomars.model.GlobalItemListModel;

public class CouponDetailActivity extends Activity {
    private int position = 0;
    private TextView cd_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);
        position = getIntent().getIntExtra("position", 0);
        initUI();
    }

    private void initUI() {
        cd_title = (TextView) findViewById(R.id.cd_title);
        setData();
    }

    private void setData() {
        final GlobalItemListModel query = AllGlobalItemList.getAllNewsFeedList().elementAt(position);
        cd_title.setText(query.getTitle());
    }
}
