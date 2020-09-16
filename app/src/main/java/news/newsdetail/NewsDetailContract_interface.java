package news.newsdetail;

import android.app.Activity;

import Base.View_interface;
import Base.Presenter_interface;
import data.NewsContent;

public interface NewsDetailContract_interface {

    interface View extends View_interface<Presenter>{

        /*
        设置新闻
        @param newsContent 新闻
         */
        void setNewsContent(NewsContent newsContent);

        /*
         弹窗
         @param title 标题
         */
        void onShowToast(String title);

        /*
         开始加载
         */
        void onStartLoading();

        /*
         获取新闻详情失败
         */
        void onError();

        /*
         设置图片可见性
         @param visible 图片是否可见
         */
        void setImageVisible(boolean visible);

    }

    interface Presenter extends Presenter_interface{
        //TODO 分享
        /*
        分享
        @param activity调用者
        @param newscontent 新闻
         */
        void shareNews(Activity activity, NewsContent newsContent);
    }


}
