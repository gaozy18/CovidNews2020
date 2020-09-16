package Entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.dac.R;

import java.util.List;

import data.CoronaEntity;

public class EntityFragment extends Fragment implements EntityContract_interface.View {

    private EntityContract_interface.Presenter entityPresenter;
    private EditText searchEditText;
    private String keyword;
    private Button searchButton;
    private RecyclerView entityrecylerview;
    private EntityAdapter myentityadapter;
    private LinearLayoutManager linearLayoutManager;
    private int lastOpen = 0;

    public EntityFragment(){}

    public static EntityFragment newInstance() {
        EntityFragment fragment = new EntityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.entityPresenter = new EntityPresenter(this);

        this.myentityadapter = new EntityAdapter(this.context());
        this.myentityadapter.setOnItemClickListener(new EntityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != lastOpen){
                    myentityadapter.setVisibleNum(lastOpen,position);
                    lastOpen = position;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_entity,container,false);

        this.searchButton = (Button) view.findViewById(R.id.entity_searchButton);
        this.searchButton.setEnabled(true);
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = searchEditText.getText().toString().trim();
                if (info.isEmpty()){
                    Toast.makeText(context(),"请输入有效内容！",Toast.LENGTH_SHORT).show();
                } else {
                    entityPresenter.getData(info);
                }
            }
        });
        this.searchEditText = (EditText) view.findViewById(R.id.entity_searchedittext);
        this.entityrecylerview = (RecyclerView) view.findViewById(R.id.entity_recycle_view);

        this.linearLayoutManager = new LinearLayoutManager(this.context());
        this.entityrecylerview.setLayoutManager(this.linearLayoutManager);
        this.entityrecylerview.setItemAnimator(new DefaultItemAnimator());
        this.entityrecylerview.setHasFixedSize(true);
        this.entityrecylerview.setAdapter(this.myentityadapter);

        return view;
    }


    @Override
    public void setPresenter(EntityContract_interface.Presenter presenter) {
        this.entityPresenter = presenter;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void startAc(Intent intent, Bundle bundle) {
        startActivity(intent,bundle);
    }

    @Override
    public void setData(List<CoronaEntity> list) {
        this.myentityadapter.setEntitiesData(list);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {
        Toast.makeText(context(),"无法获取信息！",Toast.LENGTH_SHORT).show();
    }
}
