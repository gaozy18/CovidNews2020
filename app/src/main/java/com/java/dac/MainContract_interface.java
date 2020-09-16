package com.java.dac;

import Base.Presenter_interface;
import Base.View_interface;


public interface MainContract_interface {

    interface View extends View_interface<Presenter>
    {

        void switchToNews();

        void switchToCovidData();

        void switchToSettings();

        void switchToAbout();

        void switchToEntity();

        void switchToExpert();

    }


    interface Presenter extends Presenter_interface
    {

        void switchNavigation(int fragment_id);

        int getCurrentNavigationid();

    }


}
