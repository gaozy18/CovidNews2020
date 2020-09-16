package com.java.dac;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import Coviddata.CoviddataFragment;
import Entity.EntityFragment;
import Expert.ExpertFragment;
import news.newsFragment;
import settings.SettingFragment;
import About.AboutFragment;


public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,MainContract_interface.View{

    //private Context context;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private MainContract_interface.Presenter mainPresenter;
    private Fragment newsfragment,settingfragment,coviddatafragment,entityfragment,expertfragment,aboutfragment;
    private MenuItem searchitem;
    private SearchView searchView;
    private String keyword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = new MainPresenter(this);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.subscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_search,menu);

        this.searchitem = menu.findItem(R.id.action_search);
        this.searchitem.setVisible(R.id.covid_news == this.mainPresenter.getCurrentNavigationid());

        this.searchView = (SearchView)this.searchitem.getActionView();
        this.searchView.setOnCloseListener(() -> {
            if (!this.keyword.isEmpty())
            {
                this.keyword = "";
                ( (newsFragment) this.newsfragment ).setSearchKeyWord("");
            }
            return false;
        });

        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query.trim();
                Log.i("MainActivity","searching" + query);
                if (mainPresenter.getCurrentNavigationid() == R.id.covid_news){
                    ( (newsFragment) newsfragment ).setSearchKeyWord(query);
                }
                searchView.clearFocus();
                //searchView.setQuery("",false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    public void changeInfo(int id,String title)
    {
        this.mToolbar.setTitle(title);
        this.mNavigationView.setCheckedItem(id);
    }


    @Override
    public void switchToNews() {
        Log.i("MainActivity", "新闻");
        this.changeInfo(R.id.covid_news,"新闻");
        if (newsfragment == null) newsfragment = newsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,newsfragment).commit();
        if (searchView != null) searchView.setVisibility(View.VISIBLE);
    }

    @Override
    public void switchToCovidData() {
        Log.i("MainActivity", "新冠数据");
        this.changeInfo(R.id.covid_coviddata,"新冠数据");
        if (coviddatafragment == null) coviddatafragment = CoviddataFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,coviddatafragment).commit();
        if (searchView != null) searchView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void switchToSettings() {
        Log.i("MainActivity", "设置");
        this.changeInfo(R.id.covid_settings,"设置");
        if (settingfragment == null) this.settingfragment = SettingFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,settingfragment).commit();
        if (searchView != null) searchView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void switchToAbout() {
        Log.i("MainActivity", "关于");
        this.changeInfo(R.id.covid_about,"关于");
        if (aboutfragment == null) this.aboutfragment = new AboutFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,aboutfragment).commit();
        if (searchView != null) searchView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void switchToEntity() {
        Log.i("MainActivity","疫情图谱");
        this.changeInfo(R.id.covid_entity,"疫情图谱");
        if (entityfragment == null) this.entityfragment = EntityFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,entityfragment).commit();
        if (searchView != null) searchView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void switchToExpert() {
        Log.i("MainActivity","知疫学者");
        this.changeInfo(R.id.covid_master,"知疫学者");
        if (expertfragment == null) this.expertfragment = ExpertFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,expertfragment).commit();
        if (searchView != null) searchView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(MainContract_interface.Presenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void startAc(Intent intent, Bundle bundle) {
        startActivity(intent,bundle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mainPresenter.switchNavigation(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
