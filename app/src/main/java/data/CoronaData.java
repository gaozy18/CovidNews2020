package data;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CoronaData {
    public static CoronaData NULL = new CoronaData();
    public List<Integer> confirmed;
    public List<Integer> suspected;
    public List<Integer> cured;
    public List<Integer> dead;

    public String dayBegin;
    CoronaData(String begin) {
        dayBegin = begin;
        confirmed = new ArrayList<>();
        suspected = new ArrayList<>();
        cured = new ArrayList<>();
        dead = new ArrayList<>();
    }
    CoronaData() {
        dayBegin = "2000-06-10";
        confirmed = new ArrayList<>();
        suspected = new ArrayList<>();
        cured = new ArrayList<>();
        dead = new ArrayList<>();
    }
    public void addDaily(JSONArray arr) {
        if (arr.length() < 4) return;
        int conf = arr.optInt(0);
        int susp = arr.optInt(1);
        int cur = arr.optInt(2);
        int de = arr.optInt(3);
        confirmed.add(conf);
        suspected.add(susp);
        cured.add(cur);
        dead.add(de);
    }
}
