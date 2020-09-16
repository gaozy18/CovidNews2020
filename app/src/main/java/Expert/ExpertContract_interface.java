package Expert;

import java.util.List;

import Base.Presenter_interface;
import Base.View_interface;
import data.Scholar;

public interface ExpertContract_interface {

    interface View extends View_interface<Presenter> {
        void setData(List<Scholar> list);

        void onSuccess();

        void onError();
    }

    interface Presenter extends Presenter_interface{
        void getData();
    }
}
