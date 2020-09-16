package Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.dac.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import data.CoronaEntity;

public class EntitySecondAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CoronaEntity.Fellow> myfellows;
    private LinkedHashMap<String, String> myproperties;
    private String entity_parent;
    private Context context;

    public EntitySecondAdpater( Context context ) { this.context = context; }

    public void setData(String ep,List<CoronaEntity.Fellow> f,LinkedHashMap<String, String> p){
        this.entity_parent = ep;
        this.myfellows = f;
        this.myproperties = p;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entity_relations_and_attributes,parent,false);
        return new SecondItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SecondItemViewHolder){
            final SecondItemViewHolder item = (SecondItemViewHolder) holder;
            if (position < myfellows.size()){
                CoronaEntity.Fellow fellow = myfellows.get(position);
                String temp = fellow.label + ":";
                if (fellow.forward) temp = temp + fellow.label + "从属于" + entity_parent;
                else temp = temp + entity_parent + "从属于" + fellow.label;
                temp = temp + ";" + "关系:" + fellow.relation;
                item.textView_title_and_info.setText(temp);
            } else {
                ArrayList<Map.Entry<String, String>> temp = new ArrayList<Map.Entry<String, String>>(myproperties.entrySet());
                item.textView_title_and_info.setText(temp.get(position - myfellows.size()).getKey() + ":" + temp.get(position - myfellows.size()).getValue());
            }
        }
    }

    @Override
    public int getItemCount() {
        return myfellows.size() + myproperties.size();
    }

    private class SecondItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View myview;
        private TextView textView_title_and_info;


        public SecondItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.myview = itemView;
            this.textView_title_and_info = this.myview.findViewById(R.id.entity_title_and_info);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
