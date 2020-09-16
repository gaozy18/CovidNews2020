package news.newsdetail;

import android.app.Activity;
import android.util.Log;

import data.Manager;
import data.NewsContent;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class NewsDetailPresenter implements NewsDetailContract_interface.Presenter{

    private String Detailnews_id;
    private NewsDetailContract_interface.View view;

    public NewsDetailPresenter(NewsDetailContract_interface.View view, String news_id)
    {
        this.Detailnews_id = news_id;
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void shareNews(Activity activity, NewsContent newsContent) {
        //TODO shareActivity
    }

    @Override
    public void subscribe() {
        //TODO fetchdetialnews
        Single<NewsContent> single = Manager.M.getNewsContent(Detailnews_id);
        single.subscribe(new Consumer<NewsContent>() {
            @Override
            public void accept(NewsContent newsContent) throws Exception {
                view.setNewsContent(newsContent);
            }
        });
        Log.i("NewsDetailPresenter","insubscribe():news_id = " + Detailnews_id);
    }

    @Override
    public void unsubsribe() {
        //donothingforthisfunction
    }
}
