package data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private SQLiteDatabase db;
    private String homePath;
    private final String HISTORY_SIMPLE_TB = "Historical_Simple_Table";
    private final String HISTORY_DETAILED_TB = "Historical_Detailed_Table";
    private final String ID = "news_ID";
    private final String SIMPLE_DATA = "Simple_JSON_String";
    private final String DETAILED_DATA = "Detailed_JSON_String";

    DBManager(Context c) throws IOException {
        homePath = c.getFilesDir().getPath()+"/DataBase.db";
        db = SQLiteDatabase.openOrCreateDatabase(homePath,null);
        createTables();
    }

    void createTables() {
        String createHistTBCmdS = String.format("create table if not exists %s(%s string primary key, %s string)"
                , HISTORY_SIMPLE_TB, ID, SIMPLE_DATA);
        db.execSQL(createHistTBCmdS);
        String createHistTBCmdD = String.format("create table if not exists %s(%s string primary key, %s string)"
                , HISTORY_DETAILED_TB, ID, DETAILED_DATA);
        db.execSQL(createHistTBCmdD);
    }

    void dropTables() {
        String dropHistCmdS = String.format("delete from %s", HISTORY_SIMPLE_TB);
        String dropHistCmdD = String.format("delete from %s", HISTORY_DETAILED_TB);
        db.execSQL(dropHistCmdD);
        db.execSQL(dropHistCmdS);
    }

    void insertSimpleHist(NewsItem newsItem) {
        String insertCmd = String.format("insert or replace into %s(%s, %s) values(%s, %s)"
                , HISTORY_SIMPLE_TB, ID, SIMPLE_DATA
                , DatabaseUtils.sqlEscapeString(newsItem.id)
                , DatabaseUtils.sqlEscapeString(newsItem.originJSON));
        db.execSQL(insertCmd);
    }

    void insertDetailedHist(NewsContent newsContent) {
        String insertCmd = String.format("insert or replace into %s(%s, %s) values(%s, %s)"
                , HISTORY_DETAILED_TB, ID, DETAILED_DATA
                , DatabaseUtils.sqlEscapeString(newsContent.id)
                , DatabaseUtils.sqlEscapeString(newsContent.originJSON));
        db.execSQL(insertCmd);
    }

    List<NewsItem> getHistSimpleList(int page, int size) {
        List<NewsItem> res = new ArrayList<>();
        String getCmd = String.format("select * from %s limit %s", HISTORY_SIMPLE_TB, String.valueOf(size));
        Cursor cursor = db.rawQuery(getCmd, null);
        while (cursor.moveToNext()) {
            try {
                res.add(0, new NewsItem(new JSONObject(cursor.getString(cursor.getColumnIndex(SIMPLE_DATA)))));
            } catch (JSONException ignored) {
            }
        }
        cursor.close();
        return res;
    }

    List<NewsItem> searchHistSimpleList(String keyword, int size) {
        List<NewsItem> res = new ArrayList<>();
        String searchCmd = String.format("select * from %s", HISTORY_SIMPLE_TB);
        Cursor cursor = db.rawQuery(searchCmd, null);
        while (cursor.moveToNext()) {
            try {
                NewsItem item = new NewsItem(new JSONObject(cursor.getString(cursor.getColumnIndex(SIMPLE_DATA))));
                String segs = item.title;
                if (segs.contains(keyword)) {
                    res.add(item);
                }
            } catch (JSONException ignored) {
            }
        }
        cursor.close();
        return res;
    }

    boolean searchSimpleIdHistory(final String id) {
        String searchIdCmd = String.format("select * from %s where %s = %s"
                , HISTORY_SIMPLE_TB, ID, DatabaseUtils.sqlEscapeString(id));
        Cursor cursor = db.rawQuery(searchIdCmd, null);
        boolean res = cursor.moveToFirst();
        cursor.close();
        return res;
    }

    boolean searchDetailedIdHistory(final String id) {
        String searchIdCmd = String.format("select * from %s where %s = %s"
                , HISTORY_DETAILED_TB, ID, DatabaseUtils.sqlEscapeString(id));
        Cursor cursor = db.rawQuery(searchIdCmd, null);
        boolean res = cursor.moveToFirst();
        cursor.close();
        return res;
    }

    NewsContent getDetailedById(String id) {
        NewsContent newsContent = new NewsContent();
        String searchIdCmd = String.format("select * from %s where %s = %s"
                , HISTORY_DETAILED_TB, ID, DatabaseUtils.sqlEscapeString(id));
        Cursor cursor = db.rawQuery(searchIdCmd, null);
        boolean fetched = cursor.moveToFirst();
        if (fetched) {
            try {
                newsContent = new NewsContent(new JSONObject(cursor.getString(cursor.getColumnIndex(DETAILED_DATA))));
            } catch (JSONException ignored) {
            }
        }
        cursor.close();
        return newsContent;
    }
}
