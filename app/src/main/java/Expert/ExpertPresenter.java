package Expert;

import java.util.List;

import data.Manager;
import data.Scholar;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class ExpertPresenter implements ExpertContract_interface.Presenter {

    private ExpertContract_interface.View expertview;

    ExpertPresenter(ExpertContract_interface.View view){
        this.expertview = view;
    }

    @Override
    public void getData() {
        Single<List<Scholar>> single = Manager.M.getScholarInfo(100);
        single.subscribe(new Consumer<List<Scholar>>() {
            @Override
            public void accept(List<Scholar> list) throws Exception {
                if (list.isEmpty()){
                    expertview.onError();
                } else {
                    expertview.onSuccess();
                    expertview.setData(list);
                }
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubsribe() {

    }
}
