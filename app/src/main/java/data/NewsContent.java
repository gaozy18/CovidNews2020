package data;

import org.json.JSONObject;

public class NewsContent {
    public String originJSON;
    public String id;
    public String title;
    public String content;
    public String date;
    public String source;
    public String author;
    /*
    * wait for extensions
    **/
    public NewsContent(JSONObject jsonNewsDetail) {
        originJSON = jsonNewsDetail.toString();
        id = jsonNewsDetail.optString("_id");
        title = jsonNewsDetail.optString("title");
        content = arrangeParagraph(jsonNewsDetail.optString("content"));
        if (content.isEmpty()) content = jsonNewsDetail.optString("title");
        date = jsonNewsDetail.optString("date");
        source = jsonNewsDetail.optString("source");
    }

    public NewsContent() {
        title = "Not Found";
        content = "";
        date = "";
        source = "";
        originJSON = "";
    }
    private String arrangeParagraph(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 1; i <= s.length()-2; i++) {
            if (s.charAt(i) == ',' && s.charAt(i+1) != ' ' && !Character.isDigit(s.charAt(i-1))) {
                sb.replace(i,i+1,"\n");
            }
        }
        return sb.toString();
    }

    public boolean isNull() {
        if (title.equals(new String("Not Found"))) {
            return true;
        }
        return false;
    }
}
