package settings;

import java.util.List;

import data.Configuration;
import data.Manager;

public class SettingPresenter implements SettingContract_interface.Presenter{

    private SettingContract_interface.View view;
    //private Configuration configuration;

    SettingPresenter(SettingContract_interface.View view){
        this.view = view;
        //config getfrom Manager(TODO)
        view.setPresenter(this);
    }


    @Override
    public void cleanCache() {
        Manager.M.cleanDataBase();
    }

    @Override
    public void checkUpdate() {
        this.view.onShowAlertDialog("您已经是最新版本！","当前版本：V1.0.0");
    }

    @Override
    public void addCategory(Configuration.Category category) {
        if (Manager.M.getConfiguration().addCategory(category)) view.onAddCategory(category);
    }

    @Override
    public void removeCategory(Configuration.Category category ,int position) {
        if (Manager.M.getConfiguration().removeCategory(category)) view.onRemoveCategory(category,position);
    }

    @Override
    public List<Configuration.Category> getCategory() {
        return Manager.M.getavailableCategories();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubsribe() {

    }
}
