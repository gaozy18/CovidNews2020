package Coviddata;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.Constant;
import data.CoronaData;
import data.Manager;
import data.NewsItem;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class CoviddataPresenter implements CoviddataContract_interface.Presenter {

    private CoviddataContract_interface.View Coviddataview;

    CoviddataPresenter(CoviddataContract_interface.View view){
        this.Coviddataview = view;
        view.setPresenter(this);
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubsribe() {

    }

    @Override
    public void getData(int tag1, int tag2) {
        Log.i("CoviddataPresenter", String.valueOf(tag1) + " " + String.valueOf(tag2));
        ArrayList<Entry> confirmedentry = new ArrayList<Entry>();
        ArrayList<Entry> suspectedentry = new ArrayList<Entry>();
        ArrayList<Entry> curedentry = new ArrayList<Entry>();
        ArrayList<Entry> deathentry = new ArrayList<Entry>();
        String s1 = "",s2 = "";
        if (tag1 == 0){
            s1 = "中华人民共和国";
            s2 = Constant.PROVINCES[tag2];
        }else if (tag1 == 1){
            s1 = Constant.COUNTRIES[tag2];
        }
        s1 = Manager.M.getSitemap().countryMap.get(s1);
        if (!s2.isEmpty()) {
            s2 = Manager.M.getSitemap().stateMap.get(s2);
        }
        System.out.println(s1 + " " + s2);
        Single<CoronaData> single = Manager.M.getRegionCoronaData(s1, s2);
        single.subscribe(new Consumer<CoronaData>() {
            @Override
            public void accept(CoronaData coronaData) throws Exception {
                System.out.println(coronaData.confirmed);
                System.out.println(coronaData.suspected);
                if (coronaData.confirmed.isEmpty()) {
                    Coviddataview.onError();
                }
                else {
                    for (int i = 0;i < coronaData.confirmed.size();i++)
                    {
                        confirmedentry.add(new Entry(i+1,coronaData.confirmed.get(i)));
                        suspectedentry.add(new Entry(i+1,coronaData.suspected.get(i)));
                        curedentry.add(new Entry(i+1,coronaData.cured.get(i)));
                        deathentry.add(new Entry(i+1,coronaData.dead.get(i)));
                    }
                    LineDataSet confirmedset = new LineDataSet(confirmedentry,"累计确诊");
                    confirmedset.setColor(Color.RED);
                    confirmedset.setCircleColor(Color.RED);
                    confirmedset.setCircleRadius(1f);
                    LineDataSet curedset = new LineDataSet(curedentry,"累计治愈");
                    curedset.setColor(Color.GREEN);
                    curedset.setCircleColor(Color.GREEN);
                    curedset.setCircleRadius(1f);
                    LineDataSet deathset = new LineDataSet(deathentry,"累计死亡");
                    deathset.setColor(Color.BLACK);
                    deathset.setCircleColor(Color.BLACK);
                    deathset.setCircleRadius(1f);
                    LineDataSet suspectedset = new LineDataSet(suspectedentry,"现有疑似");
                    suspectedset.setColor(Color.YELLOW);
                    suspectedset.setCircleColor(Color.YELLOW);
                    suspectedset.setCircleRadius(1f);
                    LineData curedata = new LineData(confirmedset,curedset);
                    LineData deathdata = new LineData(deathset,suspectedset);
                    Coviddataview.setData(curedata,deathdata);
                    Coviddataview.onSuccess();
                }
            }
        });
    }
}
