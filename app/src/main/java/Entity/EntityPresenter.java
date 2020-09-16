package Entity;

import java.util.List;

import data.CoronaEntity;
import data.Manager;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class EntityPresenter implements EntityContract_interface.Presenter {

    private EntityContract_interface.View entityview;

    EntityPresenter(EntityContract_interface.View view){
        this.entityview = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubsribe() {

    }

    @Override
    public void getData(String info) {
        System.out.println(info);
        Single<List<CoronaEntity>> single = Manager.M.getCoronaEntity(info);
        single.subscribe(new Consumer<List<CoronaEntity>>() {
            @Override
            public void accept(List<CoronaEntity> coronaEntities) throws Exception {
                if (coronaEntities.isEmpty()){
                    entityview.onError();
                    entityview.setData(coronaEntities);
                } else {
                    entityview.onSuccess();
                    entityview.setData(coronaEntities);
                }
            }
        });
    }
}
