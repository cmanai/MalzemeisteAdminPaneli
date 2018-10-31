package com.bilgiyazan.malzemeiste.adminpaneli.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.bilgiyazan.malzemeiste.adminpaneli.Fragment.BoyalarFragment;
import com.bilgiyazan.malzemeiste.adminpaneli.Fragment.KampanyalarFragment;
import com.bilgiyazan.malzemeiste.adminpaneli.Fragment.MakinalarFragment;
import com.bilgiyazan.malzemeiste.adminpaneli.Fragment.MalzemeFragment;
import com.bilgiyazan.malzemeiste.adminpaneli.Fragment.YazilimFragment;
import com.bilgiyazan.malzemeiste.adminpaneli.Fragment.YedekParcaFragment;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.Utils.ConnectionDetector;
import com.bilgiyazan.malzemeiste.adminpaneli.Utils.HttpHandler;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;


public class ContainerActivity extends AppCompatActivity {
    public static Drawer result;
    Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    Toast showToastMessage;
    DialogPlus SortDialog;
    CheckBox sort_by_code;
    FloatingSearchView mSearchView;
    AppBarLayout appBarLayout;
    RelativeLayout circularProgressBar;
    ConnectionDetector connectionDetector;
    Boolean isInternetPresent;
    SharedPreferences prefs;
    MaterialDialog Logout_Dialog;
    MaterialDialog Logout_Dialog_spinn;
    View view;
    ViewPager viewPager;
    private FirebaseAuth mAuth;
    private AccountHeader headerResult = null;
    private DatabaseReference mDatabase;

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();


        prefs = this.getSharedPreferences("malzemeiste_admin_panel", Context.MODE_PRIVATE);

        showToastMessage = Toast.makeText(this, "Çıkmak için tekrar basın", Toast.LENGTH_SHORT);

        SortDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.sort_dialog_layout))
                .setGravity(Gravity.BOTTOM)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)  // or any custom width ie: 300
                .setContentHeight(ViewGroup.LayoutParams.MATCH_PARENT)

                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                    }
                })
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                        SortDialog.dismiss();
                    }
                })

                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                    }
                })
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {


                        if (view.getId() == R.id.close_sort) {
                            SortDialog.dismiss();
                        }


                    }
                })

                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)

                .create();


        view = SortDialog.getHolderView();

        sort_by_code = (CheckBox) view.findViewById(R.id.sort_by_code);


        if (prefs.getString("SortByKod", "false").equals("true")) {

            sort_by_code.setChecked(true);


        }

        sort_by_code.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("SortByKod", "true");


                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("SortByKod", "false");


                    editor.apply();

                }
            }
        });


        setContentView(R.layout.container_layout);
        circularProgressBar = (RelativeLayout) findViewById(R.id.circularProgressBar);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_container);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(false);

        }
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        // ViewCompat.setElevation(mSearchView, 5);
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {


            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (!currentQuery.equals("")) {
                    Intent intent = new Intent(ContainerActivity.this, ResultSearchActivity.class);
                    intent.putExtra("search", currentQuery);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, 911);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            mSearchView.clearQuery();
                        }
                    }, 1000);
                }
            }
        });

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_sort) {


                    SortDialog.show();
                }

            }
        });


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("BASKI MAKİNALARI", MakinalarFragment.class)
                .add("Boyalar", BoyalarFragment.class)
                .add("Yazılımlar", YazilimFragment.class)
                .add("Yedek Parça", YedekParcaFragment.class)
                .add("Malzeme", MalzemeFragment.class)
                .add("KAMPANYALAR", KampanyalarFragment.class)

                .create());

        viewPager = (ViewPager) findViewById(R.id.viewpager_container);

        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.tabs);

        viewPagerTab.setViewPager(viewPager);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category");
        mDatabase.keepSynced(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appBarLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                circularProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

     /*   headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                .withTextColor(getColor(ContainerActivity.this, R.color.md_black_1000))
                .withSelectionListEnabledForSingleProfile(false)
                .withCloseDrawerOnProfileListClick(true)
                .withSelectionListEnabled(false)
                .withProfileImagesClickable(false)

                .withHeaderBackground(R.drawable.background_splash_procolor)
                .addProfiles(

                        // profile

                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)


                )

                .withSavedInstance(savedInstanceState)
                .build();


        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Track Your Order").withTextColor(Color.parseColor("#777777")).withSelectedTextColor(Color.parseColor("#ec1a5b"));
        final PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Refer & Earn").withTextColor(Color.parseColor("#777777")).withSelectedTextColor(Color.parseColor("#ec1a5b"));
        final PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Help Center").withTextColor(Color.parseColor("#777777")).withSelectedTextColor(Color.parseColor("#ec1a5b"));
        final PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("Rate Üçge").withTextColor(Color.parseColor("#777777")).withSelectedTextColor(Color.parseColor("#ec1a5b"));
        final PrimaryDrawerItem item5 = new PrimaryDrawerItem().withName("Terms & Conditions").withTextColor(Color.parseColor("#777777")).withSelectedTextColor(Color.parseColor("#ec1a5b"));
        final PrimaryDrawerItem item6 = new PrimaryDrawerItem().withName("Follow us on Facebook").withTextColor(Color.parseColor("#777777")).withSelectedTextColor(Color.parseColor("#ec1a5b"));
        final PrimaryDrawerItem item7 = new PrimaryDrawerItem().withName("Follow us on Twitter").withTextColor(Color.parseColor("#777777")).withSelectedTextColor(Color.parseColor("#ec1a5b"));


        result = new DrawerBuilder()
                .withSelectedItem(0)
                .withToolbar(toolbar)
                .withActivity(ContainerActivity.this)

                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        item1,

                        item2, item3, item4, item5, item6, item7)
                .withHasStableIds(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        if (item1.isSelected()) {


                        }
                        if (item2.isSelected()) {


                        }
                        if (item3.isSelected()) {


                        }
                        if (item4.isSelected()) {


                        }
                        if (item5.isSelected()) {


                        }
                        if (item6.isSelected()) {


                        }
                        if (item7.isSelected()) {

                       *//*     toolbar.setTitle("Agile Scrum");
                            toolbar.setTitleTextC       olor(Color.parseColor("#ffffff"));*//*

*//*
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_container, new AgileScrumFragment());

                            fragmentTransaction.commit();*//*
                        }


                        return false;
                    }

                })
                .withSavedInstance(savedInstanceState)

                .build();*/
     /*   mSearchView.attachNavigationDrawerToMenuButton(result.getDrawerLayout());*/


     /*   BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentContainer, new HomeFragment());

                    fragmentTransaction.commit();
                }
                if (tabId == R.id.tab_category) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentContainer, new CategoriesFragment());

                    fragmentTransaction.commit();
                }

                if (tabId == R.id.tab_account) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentContainer, new AccountFragment());

                    fragmentTransaction.commit();
                }
            }
        });*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.container_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_log_out:

                Logout_Dialog = new MaterialDialog.Builder(ContainerActivity.this)
                        .title(getResources().getString(R.string.log_out_title))
                        .icon(getApplicationContext().getResources().getDrawable(R.drawable.icon_malzemeiste))
                        .limitIconToDefaultSize()
                        .content(getApplicationContext().getResources().getString(R.string.logout_message))
                        .backgroundColor(getColor(getApplicationContext(), R.color.md_white_1000))
                        .titleColor(getColor(getApplicationContext(), R.color.md_black_1000))
                        .contentColor(Color.parseColor("#80000000"))

                        .positiveText(getResources().getString(R.string.log_out))
                        .negativeText(getResources().getString(R.string.cancel))

                        .positiveColor(getColor(getApplicationContext(), R.color.primary))
                        .negativeColor(getColor(getApplicationContext(), R.color.primary))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Logout_Dialog.dismiss();
                                Logout_Dialog_spinn = new MaterialDialog.Builder(ContainerActivity.this)

                                        .content(getResources().getString(R.string.loging_out))
                                        .cancelable(false)
                                        .progress(true, 0).build();
                                Logout_Dialog_spinn.show();
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {

                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("Logged", "false");
                                        editor.putString("logged_out", "true");

                                        editor.apply();
                                        Intent refresh = new Intent(ContainerActivity.this, SplashScreenActivity.class);

                                        startActivity(refresh);
                                        Logout_Dialog_spinn.dismiss();

                                        finish();
                                        mAuth.signOut();

                                    }
                                }, 1000);
                            }
                        })

                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                            }
                        })

                        .build();

                Logout_Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                    }
                });
                Logout_Dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            showToastMessage.cancel();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showToastMessage.show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        view = SortDialog.getHolderView();

        sort_by_code = (CheckBox) view.findViewById(R.id.sort_by_code);

        if (requestCode == 911) {
            if (resultCode == Activity.RESULT_OK) {
                if (prefs.getString("SortByKod", "false").equals("true")) {

                    sort_by_code.setChecked(true);


                } else {

                    sort_by_code.setChecked(false);

                }
            }

        }


    }

    private class GetEuroTate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall("http://free.currencyconverterapi.com/api/v3/convert?q=EUR_TRY");

            Log.e("", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONObject Resultobj = jsonObj.getJSONObject("results");
                    JSONObject Rateobj = Resultobj.getJSONObject("EUR_TRY");
                    String rateString = String.valueOf(Rateobj.get("val"));
                    Log.e("resuult euro", rateString + "");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Euro_Rate", rateString);
                    editor.apply();

                } catch (final JSONException e) {
                    Log.e("", "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e("", "Couldn't get json from server.");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }

    private class GetDollarRate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://free.currencyconverterapi.com/api/v3/convert?q=USD_TRY");

            Log.e("", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject Resultobj = jsonObj.getJSONObject("results");
                    JSONObject Rateobj = Resultobj.getJSONObject("USD_TRY");
                    String rateString = String.valueOf(Rateobj.get("val"));
                    Log.e("resuult dollar", rateString + "");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Dollar_Rate", rateString);
                    editor.apply();

                } catch (final JSONException e) {
                    Log.e("", "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e("", "Couldn't get json from server.");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }
}
