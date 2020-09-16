package Expert;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.dac.R;

import java.util.List;

import data.Scholar;


public class ExpertFragment extends Fragment implements ExpertContract_interface.View {

    ExpertContract_interface.Presenter expertpresenter;
    private RecyclerView expertrecyclerView;
    private LinearLayoutManager expertlinearlayoutmanager;
    private ExpertAdapter myexpertadapter;

    public ExpertFragment(){};

    public static ExpertFragment newInstance(){
        ExpertFragment fragment = new ExpertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.myexpertadapter = new ExpertAdapter(context());
        this.expertpresenter = new ExpertPresenter(this);
        this.myexpertadapter.setOnItemClickListener(new ExpertAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Scholar s = myexpertadapter.getScholarById(position);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context(),ExpertActivity.class);
                bundle.putString("avater",s.avatar);
                bundle.putString("name",s.name);
                bundle.putString("name_zh",s.name_zh);
                bundle.putDouble("activity",s.activity);
                bundle.putInt("citations",s.citations);
                bundle.putDouble("diversity",s.diversity);
                bundle.putDouble("gindex",s.gindex);
                bundle.putDouble("hindex",s.hindex);
                bundle.putString("affiliation",s.affiliation);
                bundle.putString("bio",s.bio);
                bundle.putString("edu",s.edu);
                bundle.putBoolean("isPassedAway",s.isPassedAway);
                bundle.putString("position",s.position);
                bundle.putString("work",s.work);
                bundle.putString("homepage",s.homepage);
                intent.putExtras(bundle);
                startAc(intent,bundle);
            }
        });
        this.expertpresenter.subscribe();
        this.expertpresenter.getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert,container,false);

        this.expertrecyclerView = (RecyclerView) view.findViewById(R.id.expert_recycle_view);
        this.expertlinearlayoutmanager = new LinearLayoutManager(context());
        this.expertrecyclerView.setLayoutManager(this.expertlinearlayoutmanager);
        this.expertrecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.expertrecyclerView.setHasFixedSize(true);
        //TODO set adapter for recyclerview and add onscolllistner for it

        this.expertrecyclerView.setAdapter(this.myexpertadapter);

        return view;
    }

    @Override
    public void setData(List<Scholar> list) {
        this.myexpertadapter.setExpertData(list);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {
        Toast.makeText(context(),"获取信息失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ExpertContract_interface.Presenter presenter){
        this.expertpresenter = presenter;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void startAc(Intent intent, Bundle bundle) {
        startActivity(intent,bundle);
    }
}
