package com.appnometry.appnomars.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.dialog.MultiDialog;
import com.appnometry.appnomars.dialog.RadioDialog;
import com.appnometry.appnomars.dialog.SingleDialog;
import com.appnometry.appnomars.dialog.Standard_Dialog;
import com.appnometry.appnomars.fragments.CouponFragment;
import com.appnometry.appnomars.fragments.DealsFragment;
import com.appnometry.appnomars.fragments.EventsFragment;
import com.appnometry.appnomars.fragments.LocationFragment;
import com.appnometry.appnomars.fragments.MyprofileFragment;
import com.appnometry.appnomars.fragments.NewsFeedFragment;
import com.appnometry.appnomars.fragments.SearchFragment;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.ui.Items;
import com.appnometry.appnomars.ui.MultiSwipeRefreshLayout;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.HttpRequest;
import com.appnometry.appnomars.util.PersistUser;
import com.appnometry.appnomars.util.SharedPreferencesHelper;
import com.appnometry.appnomars.util.Tools;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private String[] mDrawerTitles;
    private String[] mFooterTitles;
    private TypedArray mDrawerIcons;
    private ArrayList<Items> drawerItems;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    Fragment fragment = null;
    private static FragmentManager mManager;
    private Context context;
    SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    ApiImplementation apiImplementation = new ApiImplementation();
    private ProgressDialog progressDialog;
    private PersistUser persistUser = new PersistUser();
    String[] existUSER = null;
    private MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private String results;

    /**
     * **************************
     * Declare UI Element
     * **********************
     */
    private TextView tv_logout;
    private TextView main_username;
    private TextView main_usertype;
    private TextView main_userexpire;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    /**
     * *******************************For Search**********************************
     */
    private ImageView action_search;
    private CardView card_view2;
    private Button catagory_imageview;
    private Button places_imageview;
    private Button btn_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new CustomProgressDialog(this, "Loading...", true);
        context = this;
        existUSER = persistUser.loadArray("USEREXIST", context);
        initUI();


    }

    private void initUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);
        card_view2 = (CardView) findViewById(R.id.card_viewmain);
        mManager = getSupportFragmentManager();
        mDrawerTitles = getResources().getStringArray(R.array.drawer_titles);
        mFooterTitles = getResources().getStringArray(R.array.footer_titles);
        mDrawerIcons = getResources().obtainTypedArray(R.array.drawer_icons);
        drawerItems = new ArrayList<Items>();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        main_username = (TextView) findViewById(R.id.main_username);
        main_usertype = (TextView) findViewById(R.id.main_usertype);
        main_userexpire = (TextView) findViewById(R.id.main_userexpire);
        main_username.setText(existUSER[2]);
        main_usertype.setText(existUSER[2]);
        main_userexpire.setText("Expires in " + Tools.dateDifference("" + existUSER[3]) + " Days");
        tv_logout.setOnClickListener(this);
        initActionBerMenu();
        fragment = new NewsFeedFragment();

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();
        }

        /***********************************************Drawer Adapter************************************************/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_adapter, R.id.item_name, mDrawerTitles);
        mDrawerList.setAdapter(adapter);
        setListView(mDrawerList);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close  /* "close drawer" description */
        ) {

            /******************* Called when a drawer has settled in a completely closed state. *********************/
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            /******************** Called when a drawer has settled in a completely open state. ************************/
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                updateView(5, 99, true);
            }
        };

        /******************** Set the drawer toggle as the DrawerListener************************/
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        /********************Drawer On item Clicklistner************************/
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        action_search.setVisibility(View.GONE);
                        fragment = new NewsFeedFragment();
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 1:
                        action_search.setVisibility(View.VISIBLE);
                        fragment = new CouponFragment();
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 2:
                        action_search.setVisibility(View.GONE);
                        fragment = new DealsFragment();
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 3:
                        action_search.setVisibility(View.GONE);
                        fragment = new EventsFragment();
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 4:
                        action_search.setVisibility(View.GONE);
                        fragment = new LocationFragment();
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 5:
                        action_search.setVisibility(View.GONE);
                        Toast.makeText(context, "MY Cart", Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 6:
                        action_search.setVisibility(View.GONE);
                        Toast.makeText(context, "Shop Clicked", Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 7:
                        action_search.setVisibility(View.GONE);
                        fragment = new MyprofileFragment();
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;


                }


                if (fragment != null) {
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_content, fragment)
                            .commit();
                }
                if (position != -1) {
                    setTitle(mDrawerTitles[position]);

                }

            }
        });

    }

    /**
     * ***************For Search Menu Initilization*********************
     */
    private void initActionBerMenu() {

        action_search = (ImageView) findViewById(R.id.action_search);
        action_search.setVisibility(View.GONE);
        catagory_imageview = (Button) findViewById(R.id.catagory_imageview);
        places_imageview = (Button) findViewById(R.id.places_imageview);
        btn_search = (Button) findViewById(R.id.btn_search);

        action_search.setOnClickListener(this);
        catagory_imageview.setOnClickListener(this);
        places_imageview.setOnClickListener(this);
        btn_search.setOnClickListener(this);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Called whenever we call invalidateOptionsMenu() *//*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }*/


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    public static void showMyDialog(String title, String message, String negativeButton, String positiveButton, Standard_Dialog.MyDialogListener myDialogListener) {
        Standard_Dialog newDialog = Standard_Dialog.newInstance(title, message, negativeButton, positiveButton, myDialogListener);
        newDialog.show(mManager, "dialog");
    }

    public static void showMySingleDialog(String title, ArrayList<String> dialogItems, String negativeButton, String positiveButton, SingleDialog.MyDialogListener myDialogListener) {
        SingleDialog newDialog = SingleDialog.newInstance(title, dialogItems, negativeButton, positiveButton, myDialogListener);
        newDialog.show(mManager, "dialog");
    }


    public static void showMyRadioDialog(String title, ArrayList<String> dialogItems, String negativeButton, String positiveButton) {
        RadioDialog newDialog = RadioDialog.newInstance(title, dialogItems, negativeButton, positiveButton);
        newDialog.show(mManager, "dialog");
    }


    public static void showMyMultiDialog(String title, ArrayList<String> dialogItems, String negativeButton, String positiveButton) {
        MultiDialog newDialog = MultiDialog.newInstance(title, dialogItems, negativeButton, positiveButton);
        newDialog.show(mManager, "dialog");
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

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.tv_logout:
                String url = apiImplementation.GenarateFullURLforLogout(sharedPreferencesHelper.getRegId(context).toString()
                        , "Android", persistUser.getPushId(context), sharedPreferencesHelper.getSessionId(context).toString());
                new LogoutAsync().execute(url);
                break;
            case R.id.action_search:
                Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_down);
                if (card_view2.getVisibility() == View.VISIBLE) {
                    card_view2.setVisibility(View.GONE);

                } else {
                    card_view2.setVisibility(View.VISIBLE);
                    card_view2.bringToFront();
                    card_view2.startAnimation(slide_down);

                }
                break;
            case R.id.catagory_imageview:
                Toast.makeText(context, "Category Selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.places_imageview:

                Toast.makeText(context, "Places Selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_search:
                fragment = new CouponFragment();
                initiateFragmentView();
                Toast.makeText(context, "Search Selected", Toast.LENGTH_LONG).show();
                AppConstant.isSearched = true;
                card_view2.setVisibility(View.GONE);
                //fragment = new SearchFragment();
                break;


        }
    }

    private void initiateFragmentView() {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        }
    }

    private class LogoutAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                results = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(params[0]));
                Log.i("result", results);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(results);
                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                    progressDialog.dismiss();
                    sharedPreferencesHelper.setRegId(context, "");
                    sharedPreferencesHelper.setSessionId(context, "");
                    String[] existErray = {"", "", "", ""};
                    persistUser.saveArray(existErray, "USEREXIST", context);
                    finish();
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.dismiss();
                    AlertDialogHelper.showAlert(context, jsonObject.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void updateView(int position, int counter, boolean visible) {

        View v = mDrawerList.getChildAt(position);
        TextView someText = (TextView) v.findViewById(R.id.item_new);
        Resources res = getResources();
        String articlesFound = "";

        switch (position) {
            /*case 1:
                someText.setVisibility(View.GONE);
                someText.setBackgroundResource(R.drawable.shape_notification);
                break;
            case 2:
                someText.setVisibility(View.GONE);
                someText.setBackgroundResource(R.drawable.shape_notification);
                break;
            case 3:

                someText.setBackgroundResource(R.drawable.shape_notification);
                break;
            case 4:
                someText.setVisibility(View.GONE);
                someText.setBackgroundResource(R.drawable.shape_notification);
                break;*/
            case 5:
                someText.setText("" + counter);
                if (visible) someText.setVisibility(View.VISIBLE);
                someText.setBackgroundResource(R.drawable.shape_notification);
                break;
        }


    }


}
