package data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.*;

public class API {
    private static int searchCategory = 1000;

    public static List<NewsItem> getSimpleNewsFromURL(long timeBefore) throws JSONException, IOException {
        Log.i("API","into_getsimplenewfromurl");
        List<NewsItem> simpleJsons = new ArrayList<>();
        String jsonString = "";
        String url = "https://covid-dashboard.aminer.cn/api/events/update?tflag=";
        URL realurl = new URL(url +(new Date().getTime() - timeBefore));
        URLConnection connection = realurl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(15000);
        connection.connect();
        Log.i("API","afterconnect");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        jsonString = in.readLine();
        JSONObject allData = new JSONObject(jsonString);
        JSONArray datas = allData.getJSONObject("data").getJSONArray("datas");
        //System.out.println(datas.length());
        Log.i("API",String.valueOf(datas.length()));
        for (int i = 0; i <= datas.length()-1; i++) {
            JSONObject jsonobj = datas.getJSONObject(i);
            simpleJsons.add(new NewsItem(jsonobj));
        }

        return simpleJsons;
    }

    public static List<NewsItem> getSimpleNewsFromURL(String type, int page, int size) throws JSONException, IOException {
        List<NewsItem> simpleJsons = new ArrayList<>();
        String jsonString = "";
        String paramType = "type=" + type;
        String paramPage = "page=" + page;
        String paramSize = "size=" + size;
        String url = "https://covid-dashboard.aminer.cn/api/events/list";
        URL realUrl = new URL(url+"?"+paramType+"&"+paramPage+"&"+paramSize);
        URLConnection connection = realUrl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        jsonString = in.readLine();
        JSONObject allData = new JSONObject(jsonString);
        JSONArray datas = allData.getJSONArray("data");
        for (int i = 0; i <= datas.length()-1; i++) {
            JSONObject jsonobj = datas.getJSONObject(i);
//            if (jsonobj.optString("lang").equals(new String("zh")))
            simpleJsons.add(new NewsItem(jsonobj));
        }
//        System.out.println(simpleJsons.size());
        return simpleJsons;
    }

    public static List<NewsItem> searchSimpleNewsFromURL(String keyword, String type, int size) throws JSONException, IOException {
        List<NewsItem> recentNews = getSimpleNewsFromURL(type, 1, searchCategory);
        List<NewsItem> searchResult = new ArrayList<>();
        for (NewsItem item : recentNews) {
            String segs = item.title;
            if (segs.contains(keyword)) {
                searchResult.add(item);
            }
        }
        return searchResult;
    }

    public static NewsContent getDetailedNewsById(String id) throws JSONException, IOException {
        String url = "https://covid-dashboard.aminer.cn/api/event/" + id;
        String jsonString = "";
        URL realurl = new URL(url);
        URLConnection connection = realurl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        jsonString = in.readLine();
        JSONObject allData = new JSONObject(jsonString);
        JSONObject data = allData.getJSONObject("data");
        return new NewsContent(data);
    }

    public static CoronaData getCoronaData(String country, String state) throws IOException, JSONException {
        String entry = "";
        entry = (state.isEmpty() ? country : country+"|"+state);
        String url = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
        String jsonString = "";
        URL realurl = new URL(url);
        URLConnection connection = realurl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        jsonString = in.readLine();
        JSONObject allData = new JSONObject(jsonString);
        JSONObject data = allData.getJSONObject(entry);
        CoronaData coronaData = new CoronaData(data.optString("begin"));
        JSONArray statistics = data.getJSONArray("data");
        for (int i = 0; i <= statistics.length()-1; i++) {
            coronaData.addDaily(statistics.getJSONArray(i));
        }
        return coronaData;
    }

    public static List<CoronaEntity> getCoronaEntity(String keyword) throws IOException, JSONException {
        List<CoronaEntity> result = new ArrayList<>();
        String url = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=";
        URL realurl = new URL(url + keyword);
        String jsonString = "";
        URLConnection connection = realurl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        jsonString = in.readLine();
        JSONObject allData = new JSONObject(jsonString);
        JSONArray data = allData.getJSONArray("data");
        for (int i=0; i<=data.length()-1; i++) {
            JSONObject obj = data.getJSONObject(i);
            result.add(new CoronaEntity(obj));
        }
        System.out.println("JSONLeng: "+result.size());
        return result;
    }

    public static Bitmap getPicFromURL(String url) throws IOException {
        URL realurl = new URL(url);
        URLConnection connection = realurl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
        connection.connect();
        return BitmapFactory.decodeStream(connection.getInputStream());
    }

    public static List<Scholar> getScholarList(int size) throws IOException, JSONException {
        List<Scholar> scholarList = new ArrayList<>();
        URL realurl = new URL("https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2");
        String jsonString = "";
        URLConnection connection = realurl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        jsonString = in.readLine();
        JSONObject allData = new JSONObject(jsonString);
        JSONArray datas = allData.getJSONArray("data");
        for (int i = 0; i <= datas.length()-1; i++) {
            JSONObject scholarJson = datas.getJSONObject(i);
            scholarList.add(new Scholar(scholarJson));
        }
        return scholarList;
    }

    public static List<NewsItem> getCluster(int category, int size) throws IOException, JSONException {
        List<NewsItem> result = new ArrayList<>();
        URL realurl = new URL("https://covid-dashboard.aminer.cn/api/events/list?type=event&size=700");
        String jsonString = "";
        URLConnection connection = realurl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        jsonString = in.readLine();
        JSONObject allData = new JSONObject(jsonString);
        JSONArray datas = allData.getJSONArray("data");
        for (int i = 0; i <= datas.length()-1; i++) {
            if (ClusterRes.clusterRes[i] == category && result.size() <= size) {
                JSONObject jsonobj = datas.getJSONObject(i);
                result.add(new NewsItem(jsonobj));
            }
        }
        return result;
    }

    public static List<NewsItem> searchClusterFromURL(String keyword, int category, int size) throws JSONException, IOException {
        List<NewsItem> recentNews = getCluster(category, searchCategory);
        List<NewsItem> searchResult = new ArrayList<>();
        for (NewsItem item : recentNews) {
            String segs = item.title;
            if (segs.contains(keyword)) {
                searchResult.add(item);
            }
        }
        return searchResult;
    }
}
