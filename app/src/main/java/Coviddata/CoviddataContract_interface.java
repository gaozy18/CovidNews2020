package Coviddata;

import com.github.mikephil.charting.data.LineData;

import Base.Presenter_interface;
import Base.View_interface;

public interface CoviddataContract_interface {

    interface View extends View_interface<Presenter>{
        void setData(LineData totalcure,LineData totaldeath);

        void onError();

        void onSuccess();
    }

    interface Presenter extends Presenter_interface{
        void getData(int tag1, int tag2);
    }
}
