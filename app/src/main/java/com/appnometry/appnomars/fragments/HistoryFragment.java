package com.appnometry.appnomars.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appnometry.appnomars.R;


/**
 * Created by Daniel on 09.11.2014.
 */
public class HistoryFragment extends Fragment {


    Fragment fragment = null;
    /**
     * ***************Initiate Bottom Tab************************
     */
    private LinearLayout linear_shop;
    private LinearLayout linear_mycart;
    private LinearLayout linear_history;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        initBottomTAb(rootView);
        return rootView;
    }
    private void initiateFragmentView() {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        }
    }

    private void initBottomTAb(View view) {
        linear_shop = (LinearLayout) view.findViewById(R.id.linear_shop);
        linear_mycart = (LinearLayout) view.findViewById(R.id.linear_mycart);
        linear_history = (LinearLayout) view.findViewById(R.id.linear_history);
        linear_history.setBackgroundColor(getResources().getColor(R.color.bottom_select));
        linear_shop.setOnClickListener(tabonclick);
        linear_mycart.setOnClickListener(tabonclick);
        linear_history.setOnClickListener(tabonclick);
    }

    public View.OnClickListener tabonclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_shop:
                    fragment = new ShopFragment();
                    initiateFragmentView();
                    break;
                case R.id.linear_mycart:
                    fragment = new MyCartFragment();
                    initiateFragmentView();
                    break;
                case R.id.linear_history:

                    break;
            }
        }
    };
}
