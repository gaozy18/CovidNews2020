package data;

import org.json.JSONObject;

public class Scholar {
    public final String avatar;       // avatar picture
    public final String name;         // name_en
    public final String name_zh;
    public final double activity;     // academic activity
    public final int citations;    // num of citations
    public final double diversity;
    public final double gindex;
    public final double hindex;
    public final String affiliation;  // belonging to
    public final String bio;          // biography
    public final String edu;
    public final boolean isPassedAway;
    public final String position;
    public final String work;
    public final String homepage;     // web url

    public Scholar(JSONObject scholar) {
        avatar = scholar.optString("avatar");
        name = scholar.optString("name");
        name_zh = scholar.optString("name_zh");
        isPassedAway = scholar.optBoolean("is_passedaway");
        JSONObject indices = scholar.optJSONObject("indices");
        if (indices != null) {
            activity = indices.optDouble("activity");
            citations = indices.optInt("citations");
            diversity = indices.optDouble("diversity");
            gindex = indices.optDouble("gindex");
            hindex = indices.optDouble("hindex");
        } else {
            activity = 0;
            citations = 0;
            diversity = 0;
            gindex = 0;
            hindex = 0;
        }
        JSONObject profile = scholar.optJSONObject("profile");
        if (profile != null) {
            affiliation = profile.optString("affiliation");
            bio = profile.optString("bio").replace("<br>", "\n");
            edu = profile.optString("edu");
            homepage = profile.optString("homepage");
            position = profile.optString("position");
            work = profile.optString("work");
        } else {
            affiliation = "";
            bio = "";
            edu = "";
            homepage = "";
            position = "";
            work = "";
        }
    }
}
