package settings;

import java.util.List;

import Base.Presenter_interface;
import Base.View_interface;
import data.Configuration;

public interface SettingContract_interface {

    interface View extends View_interface<Presenter>{
        /*
        添加新闻标签
        @param category 分类标签
         */
        void onAddCategory(Configuration.Category category);
        /*
        删除新闻标签
        @param category 分类标签
        */
        void onRemoveCategory(Configuration.Category category, int position);
        /*
        弹窗
        @param title 标题
         */
        void onShowToast(String title);
        /*
        弹窗
        @param title 标题
        @param message 消息
         */
        void onShowAlertDialog(String title, String message);
    }


    interface Presenter extends Presenter_interface{

        /*
        清空缓存
         */
        void cleanCache();
        /*
        检查更新
         */
        void checkUpdate();
        /*
        添加新闻标签
        @param category 分类标签
         */
        void addCategory(Configuration.Category category);
        /*
        删除新闻标签
        @param category 分类标签
         */
        void removeCategory(Configuration.Category category, int position);
        /*
        获得新闻标签
        @return 关键词列表
         */
        List<Configuration.Category> getCategory();
    }


}
