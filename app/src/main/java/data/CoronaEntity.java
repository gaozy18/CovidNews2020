package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class CoronaEntity {
    public static CoronaEntity NULL = new CoronaEntity();
    public double hot;
    public String label;
    public String description;
    public List<Fellow> fellows;
    public String imgUrl;
    public LinkedHashMap<String, String> properties;

    public static class Fellow {
        public String relation;
        public String label;
        public Boolean forward;
        public Fellow(String re, String la, Boolean fo) {
            relation = re;
            label = la;
            forward = fo;
        }
    }

    CoronaEntity(JSONObject entity) {
        hot = entity.optDouble("hot");
        label = entity.optString("label");
        imgUrl = entity.optString("img");
        description = "";
        properties = new LinkedHashMap<>();
        fellows = new ArrayList<>();
        JSONObject abstractInfo = entity.optJSONObject("abstractInfo");
        if (abstractInfo != null) {
            String zhWiki = abstractInfo.optString("zhwiki");
            String baiDu = abstractInfo.optString("baidu");
            String enWiki = abstractInfo.optString("enwiki");
            if (!baiDu.isEmpty()) {
                description = baiDu;
            } else if (!zhWiki.isEmpty()) {
                description = zhWiki;
            } else {
                description = enWiki;
            }

            JSONObject CovidProperty = Objects.requireNonNull(abstractInfo.optJSONObject("COVID")).optJSONObject("properties");
            if (CovidProperty != null) {
                Iterator<String> pros = CovidProperty.keys();
                while (pros.hasNext()) {
                    String tmpKey = pros.next();
                    properties.put(tmpKey, CovidProperty.optString(tmpKey));
                }
            }
            JSONArray relations = Objects.requireNonNull(abstractInfo.optJSONObject("COVID")).optJSONArray("relations");
            if (relations != null) {
                for (int i = 0; i <= relations.length() - 1; i++) {
                    JSONObject fellowObj = relations.optJSONObject(i);
                    String re = "";
                    String la = "";
                    boolean fo = false;
                    if (fellowObj != null) {
                        re = fellowObj.optString("relation");
                        la = fellowObj.optString("label");
                        fo = fellowObj.optBoolean("forward");
                    }
                    fellows.add(new Fellow(re, la, fo));
                }
            }
        }
    }

    CoronaEntity() {
        hot = 0;
        label = "";
        description = "";
        fellows = new ArrayList<>();
        properties = new LinkedHashMap<>();
        imgUrl = "";
    }
}
