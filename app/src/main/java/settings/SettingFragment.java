package settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import com.java.dac.R;

import java.util.List;

import data.Configuration;
import data.Constant;
import data.Manager;

public class SettingFragment extends Fragment implements SettingContract_interface.View {

    private SettingContract_interface.Presenter settingpresenter;
    private Button AddCategoryButton;
    private RecyclerView CategoryRecyclerView;
    private SettingChipAdapter chipAdapter;

    public SettingFragment(){

    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.settingpresenter = new SettingPresenter(this);
        this.chipAdapter = new SettingChipAdapter(this.settingpresenter.getCategory(),2);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.settingpresenter.subscribe();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        view.findViewById(R.id.button_clear).setOnClickListener((View v) -> this.settingpresenter.cleanCache());
        view.findViewById(R.id.button_update).setOnClickListener((View v) -> this.settingpresenter.checkUpdate());

        this.AddCategoryButton = (Button)view.findViewById(R.id.button_add_category);
        this.AddCategoryButton.setEnabled(this.settingpresenter.getCategory().size() < Constant.CATEGERIES_COUNT);

        this.AddCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Configuration.Category> list = Manager.M.getunavailableCategories();
                if (list.isEmpty()) return;
                String[] titlearray = new String[list.size()];
                for (int i = 0;i < list.size();i++) titlearray[i] = list.get(i).title;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("请选择要添加的新闻标签").setNegativeButton("取消",null);
                builder.setItems(titlearray,(DialogInterface dialog, int which) -> settingpresenter.addCategory(list.get(which)));
                builder.create().show();
            }
        });
        this.chipAdapter.setOnCountofChipsChangeListener(new SettingChipAdapter.OnCountofChipsChangeListener() {
            @Override
            public void onChipsCountChange(int count) {
                AddCategoryButton.setEnabled(count < Constant.CATEGERIES_COUNT);
            }
        });

        this.chipAdapter.setOnRemoveChipListener(new SettingChipAdapter.OnRemoveChipListener() {
            @Override
            public void onRemoveChip(View view, int position) {
                Configuration.Category c = chipAdapter.getChip(position);
                settingpresenter.removeCategory(c,position);
                Log.i("SettingChipAdapter","you will delete tag:" + c.title);
            }
        });

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(getContext())
                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER)
                .withLastRow(true)
                .build();
        this.CategoryRecyclerView = (RecyclerView) view.findViewById(R.id.category_view);
        this.CategoryRecyclerView.setLayoutManager(chipsLayoutManager);
        this.CategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.CategoryRecyclerView.setAdapter(this.chipAdapter);
        return view;
    }

    @Override
    public void onAddCategory(Configuration.Category category) {
        this.chipAdapter.addChip(category);
    }

    @Override
    public void onRemoveCategory(Configuration.Category category, int position) {
        this.chipAdapter.removeChip(position);
    }

    @Override
    public void onShowToast(String title) {
        Toast.makeText(getContext(),title,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowAlertDialog(String title, String message) {
        AlertDialog d = new AlertDialog.Builder(getContext()).setTitle(title).setMessage(message).setPositiveButton("确定",null).create();
        d.show();
    }

    @Override
    public void setPresenter(SettingContract_interface.Presenter presenter) {
        this.settingpresenter = presenter;
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
