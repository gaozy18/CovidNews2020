package com.java.dac;

public class MainPresenter implements MainContract_interface.Presenter {

    private MainContract_interface.View MainView;
    private int CurrentNavigation = R.id.covid_news;

    public MainPresenter(MainContract_interface.View view){
        this.MainView = view;
        view.setPresenter(this);
    }


    public void switchNavigation(int fragment_id){
        this.CurrentNavigation = fragment_id;
        switch (fragment_id)
        {
            case R.id.covid_news:
                this.MainView.switchToNews();
                break;
            case R.id.covid_coviddata:
                this.MainView.switchToCovidData();
                break;
            case R.id.covid_settings:
                this.MainView.switchToSettings();
                break;
            case R.id.covid_about:
                this.MainView.switchToAbout();
                break;
            case R.id.covid_entity:
                this.MainView.switchToEntity();
                break;
            case R.id.covid_master:
                this.MainView.switchToExpert();
                break;
            default:
                break;
        }
    }

    @Override
    public int getCurrentNavigationid() {
        return this.CurrentNavigation;
    }


    @Override
    public void subscribe() {
        switchNavigation(this.CurrentNavigation);
    }

    @Override
    public void unsubsribe() {

    }
}
