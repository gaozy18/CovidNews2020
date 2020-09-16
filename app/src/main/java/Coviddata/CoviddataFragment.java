package Coviddata;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.dac.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import data.Constant;

public class CoviddataFragment extends Fragment implements CoviddataContract_interface.View {

    private CoviddataContract_interface.Presenter coviddatapresenter;
    private Spinner domesticspinner,provincespinner;
    private ArrayAdapter<String> DomesticarrayAdapter,ProvincearryAdapter;
    private myOnItemSelectedListener myonItemSelectedListener;
    private LineChart totalcaseandcured,totaldeathandsuspected;
    private SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar;

    public CoviddataFragment() {};

    public static CoviddataFragment newInstance()
    {
        CoviddataFragment fragment = new CoviddataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.coviddatapresenter = new CoviddataPresenter(this);
        this.myonItemSelectedListener = new myOnItemSelectedListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.coviddatapresenter.subscribe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_covid_data,container,false);

        this.domesticspinner = (Spinner) view.findViewById(R.id.is_Domestic);
        this.provincespinner = (Spinner) view.findViewById(R.id.is_Area);
        this.DomesticarrayAdapter = new ArrayAdapter<String>(context(),R.layout.support_simple_spinner_dropdown_item,
                Constant.DOMESTIC);
        this.domesticspinner.setAdapter(DomesticarrayAdapter);
        this.ProvincearryAdapter = new ArrayAdapter<String>(context(),R.layout.support_simple_spinner_dropdown_item,
                Constant.PROVINCES);
        this.provincespinner.setAdapter(ProvincearryAdapter);
        this.domesticspinner.setOnItemSelectedListener(this.myonItemSelectedListener);
        this.provincespinner.setOnItemSelectedListener(this.myonItemSelectedListener);

        this.totalcaseandcured = (LineChart) view.findViewById(R.id.covid_data_totalcases);
        this.totaldeathandsuspected = (LineChart) view.findViewById(R.id.covid_data_totlaldeath);

        /*calendar.set(Calendar.YEAR,2020);
        calendar.set(Calendar.MONTH,8);
        calendar.set(Calendar.DATE,20);
        Log.i("CoviddataFragment",sdf.format(calendar.getTime()));
        calendar.add(Calendar.DATE,10);
        Log.i("CoviddataFragment",sdf.format(calendar.getTime()));*/

        initlineChat();

        /*ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(1, 1));
        values.add(new Entry(2, 22));
        values.add(new Entry(3, 33));
        values.add(new Entry(4, 66));
        values.add(new Entry(5, 88));
        values.add(new Entry(6, 145));
        values.add(new Entry(7, 312));
        values.add(new Entry(8, 666));
        values.add(new Entry(9, 777));
        values.add(new Entry(10, 887));

        LineDataSet dataSet1 = new LineDataSet(values,"1");
        LineData data = new LineData(dataSet1);
        this.totalcaseandcured.setData(data);*/

        //int tag1 = (int)this.domesticspinner.getSelectedItemId();
        //int tag2 = (int)this.provincespinner.getSelectedItemId();

        //this.coviddatapresenter.getData(tag1,tag2);

        return view;
    }

    public void initlineChat()
    {
        Description d = new Description();
        d.setText("数据来源于网络");
        this.totalcaseandcured.setDescription(d);
        XAxis xAxis1 = this.totalcaseandcured.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis1.setLabelCount(3,true);
        xAxis1.setDrawGridLines(false);

        xAxis1.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE,(int)(value-axis.getAxisMaximum()));
                return sdf.format(calendar.getTime());
            }
        });

        YAxis leftAxis1 = this.totalcaseandcured.getAxisLeft();
        YAxis rightAxis1 = this.totalcaseandcured.getAxisRight();
        leftAxis1.setDrawZeroLine(true);
        rightAxis1.setDrawZeroLine(true);
        leftAxis1.setLabelCount(5);
        rightAxis1.setLabelCount(5);
        this.totalcaseandcured.animateXY(1000,1000);

        this.totaldeathandsuspected.setDescription(d);
        XAxis xAxis2 = this.totaldeathandsuspected.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setLabelCount(3,true);
        xAxis2.setDrawGridLines(false);

        xAxis2.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE,(int)(value-axis.getAxisMaximum()));
                return sdf.format(calendar.getTime());
            }
        });

        YAxis leftAxis2 = this.totaldeathandsuspected.getAxisLeft();
        YAxis rightAxis2 = this.totaldeathandsuspected.getAxisRight();
        leftAxis2.setDrawZeroLine(true);
        rightAxis2.setDrawZeroLine(true);
        leftAxis2.setLabelCount(5);
        rightAxis2.setLabelCount(5);
        this.totaldeathandsuspected.animateXY(1000,1000);
    }


    @Override
    public void setPresenter(CoviddataContract_interface.Presenter presenter) {
        this.coviddatapresenter = presenter;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void startAc(Intent intent, Bundle bundle) {
        startActivity(intent,bundle);
    }

    @Override
    public void setData(LineData totalcure, LineData totaldeath) {
        this.totalcaseandcured.setData(totalcure);
        this.totaldeathandsuspected.setData(totaldeath);
        this.totaldeathandsuspected.notifyDataSetChanged();
        this.totalcaseandcured.notifyDataSetChanged();
        this.totalcaseandcured.invalidate();
        this.totaldeathandsuspected.invalidate();
    }

    @Override
    public void onError() {
        Toast.makeText(context(),"获取数据失败！请稍后重试",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {

    }

    private class myOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        boolean atFirst = true;

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (adapterView.getId())
            {
                case R.id.is_Domestic:
                    String value = (String) adapterView.getItemAtPosition(i);
                    Log.i("CoviddataFragment",value);
                    if (value == Constant.DOMESTIC[0])
                        ProvincearryAdapter = new ArrayAdapter<String>(context(),R.layout.support_simple_spinner_dropdown_item,
                            Constant.PROVINCES);
                    else
                        ProvincearryAdapter = new ArrayAdapter<String>(context(),R.layout.support_simple_spinner_dropdown_item,
                            Constant.COUNTRIES);
                    provincespinner.setAdapter(ProvincearryAdapter);
                    atFirst = true;
                    break;
                case R.id.is_Area:
                    //TODO display data
                    if (atFirst){
                        atFirst = false;
                        break;
                    }
                    int tag1 = (int)domesticspinner.getSelectedItemId();
                    int tag2 = (int)provincespinner.getSelectedItemId();
                    coviddatapresenter.getData(tag1,tag2);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
