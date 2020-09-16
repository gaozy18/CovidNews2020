package data;

import android.content.Context;
import android.graphics.Bitmap;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Manager {
    public static Manager M = null;
    private SiteMap sitemap;
    private DBManager dbManager;
    private Configuration configuration;

    private Manager(Context c) {
        this.configuration = new Configuration(c);
        this.sitemap = new SiteMap();
        try {
            dbManager = new DBManager(c);
        } catch (IOException ignored) {
        }
    }

    public List<Configuration.Category> getavailableCategories(){
        return this.configuration.availableCategoreis();
    }

    public List<Configuration.Category> getunavailableCategories(){
        return this.configuration.unavailableCategories();
    }

    public Configuration getConfiguration(){
        return this.configuration;
    }

    public SiteMap getSitemap(){
        return this.sitemap;
    }

    public static synchronized void CreateSingleton(Context c) {
        M = new Manager(c);
    }

    public Single<List<NewsItem>> getNewsItems(String type, int page, int size) {
        return Flowable.fromCallable(new Callable<List<NewsItem>>() {
            @Override
            public List<NewsItem> call() throws Exception {
                if (type.equals(new String("history"))) {
                    return dbManager.getHistSimpleList(page,size);
                }
                try {
                    return API.getSimpleNewsFromURL(type, page, size);
                } catch(Exception e) {
                    System.out.println(e);
                    return new ArrayList<NewsItem>();
                }
            }
        }).flatMap(new Function<List<NewsItem>, Publisher<NewsItem>>() {
            @Override
            public Publisher<NewsItem> apply(@NonNull List<NewsItem> simpleNewses) throws Exception {
                return Flowable.fromIterable(simpleNewses);
            }
        }).map(new Function<NewsItem, NewsItem>() {
            @Override
            public NewsItem apply(@NonNull NewsItem simpleNews) throws Exception {
                if (dbManager.searchSimpleIdHistory(simpleNews.id)) {
                    simpleNews.setHas_read();
                }
                return simpleNews;
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<NewsContent> getNewsContent(final String id) {
        return Flowable.fromCallable(new Callable<NewsContent>() {
            @Override
            public NewsContent call() {
                if (dbManager.searchDetailedIdHistory(id)) {
                    System.out.println("Searched from db!");
                    return dbManager.getDetailedById(id);
                }
                try {
                    return API.getDetailedNewsById(id);
                } catch(Exception e) {
                    System.out.println("Can't fetch from Internet!");
                    return new NewsContent();
                }
            }
        }).map(new Function<NewsContent, NewsContent>() {
            @Override
            public NewsContent apply(@NonNull NewsContent newsContent) throws Exception {
                if (!newsContent.isNull()) {
                    dbManager.insertDetailedHist(newsContent);
                }
                return newsContent;
            }
        }).firstOrError().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<NewsItem>> searchNewsItems(String keyword, String type, int page, int size) {
        return Flowable.fromCallable(new Callable<List<NewsItem>>() {
            @Override
            public List<NewsItem> call() throws Exception {
                if (type.equals(new String("history"))) {
                    return dbManager.searchHistSimpleList(keyword,size);
                }
                try {
                    return API.searchSimpleNewsFromURL(keyword, type, size);
                } catch(Exception e) {
                    System.out.println(e);
                    return new ArrayList<NewsItem>();
                }
            }
        }).flatMap(new Function<List<NewsItem>, Publisher<NewsItem>>() {
            @Override
            public Publisher<NewsItem> apply(@NonNull List<NewsItem> simpleNewses) throws Exception {
                return Flowable.fromIterable(simpleNewses);
            }
        }).map(new Function<NewsItem, NewsItem>() {
            @Override
            public NewsItem apply(@NonNull NewsItem simpleNews) throws Exception {
                if (dbManager.searchSimpleIdHistory(simpleNews.id)) {
                    simpleNews.setHas_read();
                }
                return simpleNews;
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<CoronaData> getRegionCoronaData(String country, String state) {
        return Flowable.fromCallable(new Callable<CoronaData>() {
            @Override
            public CoronaData call() throws Exception {
                try {
                    return API.getCoronaData(country, state);
                } catch (Exception e) {
                    return CoronaData.NULL;
                }
            }
        }).firstOrError().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<CoronaEntity>> getCoronaEntity(String keyword) {
        return Flowable.fromCallable(new Callable<List<CoronaEntity>>() {
            @Override
            public List<CoronaEntity> call() throws Exception {
                try {
                    return API.getCoronaEntity(keyword);
                } catch (Exception e) {
                    System.out.println("Exception in get corona entity!");
                    return new ArrayList<CoronaEntity>();
                }
            }
        }).flatMap(new Function<List<CoronaEntity>, Publisher<CoronaEntity>>() {
            @Override
            public Publisher<CoronaEntity> apply(@NonNull List<CoronaEntity> coronaEntities) throws Exception {
                return Flowable.fromIterable(coronaEntities);
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Bitmap> getPicFromURL(String url) {
        return Flowable.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                try {
                    return API.getPicFromURL(url);
                } catch (Exception e) {
                    return null;
                }
            }
        }).firstOrError().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Bitmap loadPic(String url) {
        Single<Bitmap> single = getPicFromURL(url);
        final Bitmap[] pic = {null};
        single.subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                pic[0] = bitmap;
            }
        });
        return pic[0];
    }

    public Single<List<Scholar>> getScholarInfo(int size) {
        return Flowable.fromCallable(new Callable<List<Scholar>>() {
            @Override
            public List<Scholar> call() throws Exception {
                try {
                    return API.getScholarList(size);
                } catch (Exception e) {
                    return new ArrayList<Scholar>();
                }
            }
        }).flatMap(new Function<List<Scholar>, Publisher<Scholar>>() {
            @Override
            public Publisher<Scholar> apply(@NonNull List<Scholar> scholars) throws Exception {
                return Flowable.fromIterable(scholars);
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<NewsItem>> getClusterItem(int category, int size) {
        return Flowable.fromCallable(new Callable<List<NewsItem>>() {
            @Override
            public List<NewsItem> call() throws Exception {
                try {
                    return API.getCluster(category, size);
                } catch (Exception e) {
                    return new ArrayList<NewsItem>();
                }
            }
        }).flatMap(new Function<List<NewsItem>, Publisher<NewsItem>>() {
            @Override
            public Publisher<NewsItem> apply(@NonNull List<NewsItem> newsItems) throws Exception {
                return Flowable.fromIterable(newsItems);
            }
        }).map(new Function<NewsItem, NewsItem>() {
            @Override
            public NewsItem apply(@NonNull NewsItem simpleNews) throws Exception {
                if (dbManager.searchSimpleIdHistory(simpleNews.id)) {
                    simpleNews.setHas_read();
                }
                return simpleNews;
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<NewsItem>> searchClusterItem(String keyword, int category, int size) {
        return Flowable.fromCallable(new Callable<List<NewsItem>>() {
            @Override
            public List<NewsItem> call() throws Exception {
                try {
                    return API.searchClusterFromURL(keyword, category, size);
                } catch (Exception e) {
                    return new ArrayList<NewsItem>();
                }
            }
        }).flatMap(new Function<List<NewsItem>, Publisher<NewsItem>>() {
            @Override
            public Publisher<NewsItem> apply(@NonNull List<NewsItem> newsItems) throws Exception {
                return Flowable.fromIterable(newsItems);
            }
        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void storeSimpleNews(NewsItem newsItem) {
        dbManager.insertSimpleHist(newsItem);
    }

    public void storeDetailedNews(NewsContent newsContent) {
        dbManager.insertDetailedHist(newsContent);
    }

    public void cleanDataBase() {
        dbManager.dropTables();
    }
}
