package news.newslist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import data.Constant;
import data.Manager;
import data.NewsItem;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import news.newsdetail.NewsDetailActivity;

public class NewsListPresenter implements NewsListContract_interface.Presenter {

    private final int PAGE_SIZE = 20;

    private NewsListContract_interface.View view;
    private int category_id;
    private String mykeyword;
    private int PageNum = 1;
    private boolean isLoading = false;
    //private long mLastFetchStart;

    public NewsListPresenter(NewsListContract_interface.View view, int category_id, String keyword)
    {
        this.view = view;
        this.category_id = category_id;
        this.mykeyword = keyword;
        this.mykeyword.trim();
        view.setPresenter(this);
    }

    @Override
    public boolean isLoading() { return this.isLoading; }

    @Override
    public void requireMoreNews() {
        Log.i("NewlistPresenter","requiremorenews" + PageNum);
        this.PageNum += 1;
        obtainNews();
    }



    @Override
    public void refreshNews() {
        Log.i("NewslistPresenter","refreshnews:" + PageNum);
        this.PageNum = 1;
        obtainNews();
    }

    @Override
    //打开某一新闻详细页面
    public void openNewsDetail(NewsItem news, Bundle options) {
        //TODO Detailnews
        Intent intent = new Intent(this.view.context(), NewsDetailActivity.class);
        //intent.putExtra(Constant.News_ID,news.id);
        //intent.putExtra(Constant.News_TITLE,news.title);
        //intent.putExtra(Constant.News_URLofPICTURE,news.news_Pictures);
        options.putString(Constant.News_ID,news.id);
        options.putString(Constant.News_TITLE,news.title);
        options.putString(Constant.News_URLofPICTURE,news.news_Pictures);
        intent.putExtras(options);
        Log.i("NewListPresenter",news.id);
        Log.i("NewsListPresenter",news.title);
        this.view.startAc(intent,options);
    }

    @Override
    public void fetchNewsRead(int posision, NewsItem news) {

    }

    @Override
    public void setKeyWord(String keyWord) {
        this.mykeyword = keyWord;
        this.refreshNews();
    }

    @Override
    public void subscribe() {
        refreshNews();
    }

    @Override
    public void unsubsribe() {
        //donothing
    }
    //从数据库中获取新闻
    private void obtainNews(){
        //TODO getNewsFromDatabase
        if (!mykeyword.isEmpty()) Log.i("NewsListPresenter","searching:"+ mykeyword);
        String type = "all";
        switch (category_id) {
            case 0:
                type = "news";
                break;
            case 1:
                type = "paper";
                break;
            case 2:
                type = "history";
                break;
            default:
                type = "cluster";
        }
        System.out.println(type);
        //Single<List<NewsItem>> single = Manager.M.getNewsItems(type, 1, PageNum * 25);
        Single<List<NewsItem>> single = null;
        if (mykeyword.isEmpty()) {
            if (type.equals(new String("cluster"))) {
                single = Manager.M.getClusterItem(category_id-3, PageNum*25);
            } else {
                single = Manager.M.getNewsItems(type, 1, PageNum * 25);
            }
        } else {
            if (type.equals(new String("cluster"))) {
                single = Manager.M.searchClusterItem(mykeyword, category_id-3, PageNum*25);
            } else {
                single = Manager.M.searchNewsItems(mykeyword, type, 1, PageNum * 25);
            }
        }
        single.subscribe(new Consumer<List<NewsItem>>() {
            @Override
            public void accept(List<NewsItem> list) throws Exception {
                if (list.isEmpty()) {
                    System.out.println("Is Empty");
                    view.setNewsList(list);
                    view.onError();
                } else {
                    view.setNewsList(list);
                    view.OnSuccess(false);
                }
            }
        });
    }
}
