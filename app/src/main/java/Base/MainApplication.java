package Base;

import android.app.Application;

import data.ImageLoader;
import data.Manager;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(this);
        Manager.CreateSingleton(this);
    }
}
