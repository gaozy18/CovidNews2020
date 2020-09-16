package Entity;

import java.util.List;

import Base.Presenter_interface;
import Base.View_interface;
import data.CoronaEntity;

public interface EntityContract_interface {

    interface View extends View_interface<Presenter>{
        void setData(List<CoronaEntity> list);

        void onSuccess();

        void onError();
    }

    interface Presenter extends Presenter_interface{
        void getData(String info);
    }

}
